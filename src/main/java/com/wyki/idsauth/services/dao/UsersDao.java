package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.controllers.wrappers.RoleWrapper;
import com.wyki.idsauth.controllers.wrappers.UserWrapper;
import com.wyki.idsauth.db.RolesRepo;
import com.wyki.idsauth.db.UserrolesRepo;
import com.wyki.idsauth.db.UsersRepo;
import com.wyki.idsauth.db.entities.Roles;
import com.wyki.idsauth.db.entities.Userroles;
import com.wyki.idsauth.db.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class name: UsersDao
 * Creater: wgicheru
 * Date:6/17/2019
 */
@Service
public class UsersDao {
    @Autowired
    private UsersRepo dbusersRepo;
    //    @Autowired
//    private BCryptPasswordEncoder encoder;
    @Autowired
    RolesRepo roleRepo;
    @Autowired
    UserrolesRepo userrolesRepo;

//    public UsersDao(UsersRepo usersRepo, BCryptPasswordEncoder passwordEncoder,RolesRepo roleRepo){
//        this.dbusersRepo=usersRepo;
//        this.encoder=passwordEncoder;
//        this.roleRepo=roleRepo;
//    }

    @Transactional
    public Optional<Users> loadUserByusername(String username) {
        List<Users> dbusersList = dbusersRepo.findByEmailOrPhonenumber(username, username);
        if (dbusersList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(dbusersList.get(0));
        }
    }

    @Transactional
    public boolean registerUser(String firstname, String othernames, String email, String phonenumber,
                                Date dateofbirth, String gender, String nationality, String identificationnumber,
                                Long roleid, String createdby) {
        List<Users> dbusersList = dbusersRepo.findByEmailOrPhonenumber(email, phonenumber);
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
            dbUser.setRegistrationdate(new Date());
            dbUser.setCreatedby(createdby);
//            dbUser.setResourceid(resourceid);

            dbusersRepo.save(dbUser);
            addRoles(roleid, dbUser);
            return true;
        } else {
            //user already registered
            return false;
        }

    }

    private void addRoles(Long roleid, Users dbUser) {
        Optional<Roles> rolesList = roleRepo.findById(roleid);
        Optional<Userroles> userrolescontainer = userrolesRepo.findbyUserRole(dbUser.getUserid(), roleid);
        if (rolesList.isPresent() && !userrolescontainer.isPresent()) {
            Userroles userroles = new Userroles();
            userroles.setRoles(rolesList.get());
            userroles.setUsers(dbUser);

            userrolesRepo.save(userroles);
        }
    }

    public void updatePhoneandEmail(long userid, String newphone, String newemail) {
        Optional<Users> users = dbusersRepo.findById(userid);
        if (users.isPresent()) {
            Users dbuser = users.get();
            dbuser.setPhonenumber(newphone);
            dbuser.setEmail(newemail);
            dbusersRepo.save(dbuser);
        }
        ;

    }

    public void updateUserPassword(String email, String phonenumber, String password) {
        List<Users> users = dbusersRepo.findByEmailOrPhonenumber(email, phonenumber);
        if (!users.isEmpty()) {
            Users user = users.get(0);
            user.setPassword(password);
            user.setActive(true);
        }
        dbusersRepo.saveAll(users);
    }

    public void deactivateUser(String email, String phonenumber) {
        List<Users> users = dbusersRepo.findByEmailOrPhonenumber(email, phonenumber);

        if (!users.isEmpty()) {
            Users user = users.get(0);
            user.setActive(false);
        }
        dbusersRepo.saveAll(users);
    }

    public int getUserAttempts(String username) {
        Users user = dbusersRepo.findByEmailOrPhonenumber(username, username).get(0);
        return user.getAttempts();
    }

    public void updateAttempts(String username, int attempts) {
        Users user = dbusersRepo.findByEmailOrPhonenumber(username, username).get(0);
        user.setAttempts(attempts);
        dbusersRepo.save(user);
    }

    @Transactional
    public List<UserWrapper> allUsers(Pageable pageable) {

        return dbusersRepo.findAll(pageable).stream()
                .map(user -> {
                    UserWrapper userWrapper = new UserWrapper();
                    userWrapper.setEmail(user.getEmail());
                    userWrapper.setName(user.getFirstname() + " " + user.getOthernames());
                    userWrapper.setPhonenumber(user.getPhonenumber());
                    userWrapper.setActive(user.isActive());
                    userWrapper.setCreatedby(user.getCreatedby());
                    List<String> rolenames = user.getRoles().stream().map(Userroles::getRoles)
                            .map(Roles::getName)
                            .collect(Collectors.toList());
                    userWrapper.setRolename(rolenames);
                    userWrapper.setCreationdate(user.getRegistrationdate());
                    return userWrapper;
                }).collect(Collectors.toList());
    }

    public List<RoleWrapper> allRoles() {
        return roleRepo.findAll().stream().map(roles -> {
            RoleWrapper roleWrapper = new RoleWrapper();
            roleWrapper.setRoleid(roles.getRoleid());
            roleWrapper.setRolename(roles.getName());
            return roleWrapper;
        }).collect(Collectors.toList());
    }


}
