package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.db.RolesRepo;
import com.wyki.idsauth.db.UsersRepo;
import com.wyki.idsauth.db.entities.Roles;
import com.wyki.idsauth.db.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        List<Users> dbusersList = dbusersRepo.findByEmailOrPhonenumberAndActiveIsTrue(username,username);
        if (dbusersList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(dbusersList.get(0));
        }
    }

    @Transactional
    public boolean registerUser(String firstname, String othernames, String email, String phonenumber,
                                Date dateofbirth, String gender,String nationality,String identificationnumber, String resourceid) {
        List<Users> dbusersList = dbusersRepo.findByEmailOrPhonenumber(email,phonenumber);
        if (dbusersList.isEmpty()) {
            Users dbUser = new Users();
            dbUser.setFirstname(firstname);
            dbUser.setOthernames(othernames);
            dbUser.setEmail(email);
            dbUser.setPhonenumber(phonenumber);
            dbUser.setDateofbirth(dateofbirth);
            dbUser.setGender(gender);
            dbUser.setNationality(nationality);
            dbUser.setNationalidnumber(identificationnumber);
//            dbUser.setPassword(encoder.encode(password));
            dbUser.setRegistrationdate(new Date());
            dbUser.setResourceid(resourceid);

            dbusersRepo.save(dbUser);
            return true;
        } else {
            //user already registered
            return false;
        }

    }

    public void updateUserPassword(String email,String phonenumber,String password){
       List<Users> users = dbusersRepo.findByEmailOrPhonenumber(email, phonenumber);
       if(!users.isEmpty()){
           Users user = users.get(0);
           user.setPassword(password);
           user.setActive(true);
       }
       dbusersRepo.saveAll(users);
    }

    public void deactivateUser(String email,String phonenumber){
        List<Users> users = dbusersRepo.findByEmailOrPhonenumber(email, phonenumber);

        if(!users.isEmpty()){
            Users user = users.get(0);
            user.setActive(false);
        }
        dbusersRepo.saveAll(users);
    }

    @Transactional
    public List<Users> allUsers() {
        return dbusersRepo.findAll();
    }

}
