package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.controllers.wrappers.ResponseWrapper;
import com.wyki.idsauth.controllers.wrappers.RoleWrapper;
import com.wyki.idsauth.controllers.wrappers.UserStats;
import com.wyki.idsauth.controllers.wrappers.UserWrapper;
import com.wyki.idsauth.db.RolesRepo;
import com.wyki.idsauth.db.UserrolesRepo;
import com.wyki.idsauth.db.UsersRepo;
import com.wyki.idsauth.db.entities.Roles;
import com.wyki.idsauth.db.entities.Userroles;
import com.wyki.idsauth.db.entities.Users;
import com.wyki.idsauth.services.ImposterService;
import com.wyki.idsauth.wrappers.ImposterWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class name: UsersDao
 * Creater: wgicheru
 * Date:6/17/2019
 */
@Service
@Slf4j
public class UsersDao {
    @Autowired
    private UsersRepo dbusersRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    RolesRepo roleRepo;
    @Autowired
    UserrolesRepo userrolesRepo;
    @Autowired
    ImposterService imposterService;

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
    public ResponseWrapper registerUser(String email, String phonenumber,
                                        String identificationnumber) {
        List<Users> dbusersList = dbusersRepo.findByEmailOrPhonenumber(email, phonenumber);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setStatus("failed");
        if (dbusersList.isEmpty()) {
            //check staff
            Map<Boolean, ImposterWrapper> result = imposterService.checkStaff(identificationnumber);
            if (result.containsKey(true)) {
                ImposterWrapper imposterWrapper = result.get(true);
              ImposterWrapper.StaffDetails.Staff staffDetails = imposterWrapper.getStaffdetails().getStaff();

                if (staffDetails.getStatus().equalsIgnoreCase("Active")) {
                    Users dbUser = new Users();
                    dbUser.setFirstname(staffDetails.getSurname());
                    dbUser.setOthernames(staffDetails.getOthernames());
                    dbUser.setEmail(email);
                    dbUser.setPhonenumber(phonenumber);
                    dbUser.setDateofbirth(new Date());
                    dbUser.setGender("BINARY");
                    dbUser.setNationality("KENYAN");
                    dbUser.setNationalidnumber(identificationnumber);
                    dbUser.setRegistrationdate(new Date());
                    dbUser.setCreatedby("");
                    dbUser.setRegion(staffDetails.getRegionname());
                    dbUser.setDepartment(staffDetails.getDeptname());
                    dbUser.setEmployeenumber(staffDetails.getEmpno());
//            dbUser.setResourceid(resourceid);

                    dbusersRepo.save(dbUser);
//                    addRoles(roleid, dbUser);
                    responseWrapper.setStatus("success");
                    responseWrapper.setBody("user created");
                    return responseWrapper;
                } else {
                    //user already registered
                    responseWrapper.setBody("staff in not an active employee");

                    return responseWrapper;
                }


            } else {


                responseWrapper.setBody(result.get(false) == null ? "error creating user" :
                        result.get(false).getStaffdetails().getResult().getMessage());

                return responseWrapper;
            }
        } else {
            //user already registered
            responseWrapper.setBody("user already exists");

            return responseWrapper;
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
            user.setPassword(encoder.encode(password));
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
                    List<String> rolenames = user.getRoles().stream().map(Userroles::getRoles)
                            .map(Roles::getName)
                            .collect(Collectors.toList());

                    userWrapper.setCreatedon(user.getRegistrationdate());
                    return userWrapper;
                }).collect(Collectors.toList());
    }

    public UserStats getUserStats() {
        //registeredusers, activeusers, inactiveusers, newusers
        UserStats userStats = new UserStats();
        userStats.setRegisteredusers(dbusersRepo.count());
        userStats.setActiveusers(dbusersRepo.countByActiveTrue());
        userStats.setInactiveusers(dbusersRepo.countByActiveFalse());
        userStats.setNewusers(0);

        return userStats;
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
