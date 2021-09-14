package com.wyki.idsauth.services;


import com.wyki.idsauth.db.entities.Userroles;
import com.wyki.idsauth.db.entities.Users;
import com.wyki.idsauth.services.dao.UsersDao;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class name: UserService
 * Creater: wgicheru
 * Date:6/17/2019
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UsersDao dbusersDao;
    @Autowired
    LoginAttemptService loginAttemptService;

    private static final Logger LOGGER = Logger.getLogger(UserService.class);



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Optional<Users> databaseusercontainer = dbusersDao.loadUserByusername(username);


        if (databaseusercontainer.isPresent()) {
            Users databaseuser = databaseusercontainer.get();
            if(loginAttemptService.isBlocked(databaseuser.getPhonenumber())){
                throw new UsernameNotFoundException("Account is disabled, use OTP to activate");
            }

            if (databaseuser.isActive()) {
                User.UserBuilder builder = User.withUsername(username);
                builder.password(databaseuser.getPassword());
                builder.authorities(loadUserRoles(databaseuser));

                return builder.build();
            } else {
                LOGGER.info("user is inactive");
                throw new UsernameNotFoundException("Account is disabled, use OTP to activate");
            }

        } else {
            LOGGER.info("user does not exist");
            throw new UsernameNotFoundException(username);
        }

    }


    private List loadUserRoles(Users user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Userroles userroles : user.getRoles()){
//            LOGGER.info("Roles for user "+user.getEmail()+" count "+ user.getRoles().size()+ " name "+userroles.getRoles().getName());
            grantedAuthorities.add(new SimpleGrantedAuthority(userroles.getRoles().getName()));
        }

//        grantedAuthorities.add(new SimpleGrantedAuthority());
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        return grantedAuthorities;
    }
}
