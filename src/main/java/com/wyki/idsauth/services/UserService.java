package com.wyki.idsauth.services;


import com.wyki.idsauth.db.entities.Roles;
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
import java.util.Arrays;
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
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

//    public UserService(UsersDao dbusersDao){
//        this.dbusersDao=dbusersDao;
//    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username){
        Optional<Users> databaseusercontainer = dbusersDao.loadUserByusername(username);

        User.UserBuilder builder = null;
        if(databaseusercontainer.isPresent()){
            Users databaseuser = databaseusercontainer.get();
            builder = User.withUsername(username);
            builder.password(databaseuser.getPassword());
            builder.authorities(loadUserRoles(databaseuser));
        }else{
            throw new UsernameNotFoundException(username);
        }
        return builder.build();
    }



    private List loadUserRoles(Users user){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        LOGGER.info(user.getRoles().size());
//        for (Roles role : username.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }

//        grantedAuthorities.add(new SimpleGrantedAuthority());
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//        return grantedAuthorities;
    }
}
