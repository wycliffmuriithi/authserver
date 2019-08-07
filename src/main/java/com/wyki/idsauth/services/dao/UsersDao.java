package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.db.RolesRepo;
import com.wyki.idsauth.db.UsersRepo;
import com.wyki.idsauth.db.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Class name: UsersDao
 * Creater: wgicheru
 * Date:6/17/2019
 */
@Service
public class UsersDao {
    @Autowired
    private UsersRepo dbusersRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    RolesRepo roleRepo;

//    public UsersDao(UsersRepo usersRepo, BCryptPasswordEncoder passwordEncoder,RolesRepo roleRepo){
//        this.dbusersRepo=usersRepo;
//        this.encoder=passwordEncoder;
//        this.roleRepo=roleRepo;
//    }

    @Transactional
    public Optional<Users> loadUserByusername(String username) {
        List<Users> dbusersList = dbusersRepo.findByEmail(username);
        if (dbusersList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(dbusersList.get(0));
        }
    }

    @Transactional
    public boolean registerUser(String username, String password) {
        List<Users> dbusersList = dbusersRepo.findByEmail(username);
        if (dbusersList.isEmpty()) {
            Users dbUser = new Users();
//            dbUser.setUsername(username);
//            dbUser.setPassword(encoder.encode(password));
//            dbUser.setRegistrationdate(new Date());
//            dbUser.setRoles(new HashSet<>(roleRepo.findAll()));
//
//            dbusersRepo.save(dbUser);
            return true;
        } else {
            //user already registered
            return false;
        }

    }

    @Transactional
    public List<Users> allUsers() {
        return dbusersRepo.findAll();
    }

}
