package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.controllers.wrappers.*;
import com.wyki.idsauth.db.RolesRepo;
import com.wyki.idsauth.db.UserrolesRepo;
import com.wyki.idsauth.db.UsersRepo;
import com.wyki.idsauth.db.entities.Roles;
import com.wyki.idsauth.db.entities.Userroles;
import com.wyki.idsauth.db.entities.Users;
import com.wyki.idsauth.services.utils.SendEmail;
import com.wyki.idsauth.services.utils.SendSMS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    SendEmail sendEmail;
    @Autowired
    SendSMS sendSMS;

    @Value("${otp.expiry.timeinminutes}")
    int otpexpirytime;

    @Value("${otp.email.message}")
    String otpusermessage;
    @Value("${otp.email.subject}")
    String otpusersubject;


    @Transactional
    public Optional<Users> loadUserByusername(String username) {
        List<Users> dbusersList = dbusersRepo.findByEmailOrPhonenumberOrNationalidnumber(username, username, username);
        if (dbusersList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(dbusersList.get(0));
        }
    }

    @Transactional
    public ResponseWrapper registerUser(AddUserDTO addUserDTO) {
        List<Users> dbusersList = dbusersRepo.findByNationalidnumber(addUserDTO.getNationalid());
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setStatus("failed");
        if (dbusersList.isEmpty()) {
            //check staff

            Users dbUser = new Users();
            dbUser.setName(addUserDTO.getName());
            dbUser.setDateofbirth(new Date());
            dbUser.setGender("BINARY");
            dbUser.setNationality("Rwandese");
            dbUser.setNationalidnumber(addUserDTO.getNationalid());
            dbUser.setRegistrationdate(new Date());
            dbUser.setCreatedby("");


            dbUser.setEmail(addUserDTO.getEmail());
            dbUser.setPhonenumber(addUserDTO.getPhonenumber());

            //generate OTP
            int otp = 10000 + new Random().nextInt(9999);
            dbUser.setOtp(String.valueOf(otp));
            dbUser.setValidotp(true);
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.MINUTE, otpexpirytime);
            dbUser.setOtpexpirytime(c.getTime());

//            dbUser.setResourceid(resourceid);

            dbUser = dbusersRepo.save(dbUser);

//            dbusersRepo.save(dbUser);
            responseWrapper.setStatus("success");
            responseWrapper.setBody("user created, use otp to set password");

            return responseWrapper;


        } else {
            //user already registered
            responseWrapper.setBody("user already exists");

            return responseWrapper;
        }

    }

    public ResponseWrapper forgotPassword(String nationalid) {
        List<Users> dbusersList = dbusersRepo.findByNationalidnumber(nationalid);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setStatus("failed");
        responseWrapper.setBody("could not find user");
        if (!dbusersList.isEmpty()) {
            Users dbUser = dbusersList.get(0);

            //generate OTP
            int otp = 10000 + new Random().nextInt(9999);
            dbUser.setOtp(String.valueOf(otp));
            dbUser.setValidotp(true);
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.MINUTE, otpexpirytime);
            dbUser.setOtpexpirytime(c.getTime());
            dbUser.setActive(false);
            dbusersRepo.save(dbUser);
            responseWrapper.setStatus("success");
            responseWrapper.setBody("use otp to set new password");
        }

        return responseWrapper;
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

    public ResponseWrapper updateUserPassword(String nationalid, String otp, String password) {
        List<Users> users = dbusersRepo.findByNationalidnumber(nationalid);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setStatus("failed");
        if (!users.isEmpty()) {
            Users user = users.get(0);
            //check user inactive
            if (user.isActive() || !user.isValidotp()) {
                //no password change request
                responseWrapper.setBody("No password change request found");
            } else if (user.getOtp().equals(otp)) {
                //check otp expired
                if (user.getOtpexpirytime().getTime() >= new Date().getTime()) {
                    user.setPassword(encoder.encode(password));
                    user.setActive(true);
                    user.setValidotp(false);
                    dbusersRepo.save(user);

                    responseWrapper.setStatus("success");
                    responseWrapper.setBody("password updated successfully");
                } else {
                    //otp expired
                    responseWrapper.setBody("OTP expired");
                }
            } else {
                //invalid otp
                responseWrapper.setBody("incorrect OTP");
            }

        } else {
            responseWrapper.setBody("user not found");
        }
        return responseWrapper;
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
                    userWrapper.setUserid(user.getUserid());
                    userWrapper.setEmail(user.getEmail());
                    userWrapper.setName(user.getName());
                    userWrapper.setPhonenumber(user.getPhonenumber());

                    userWrapper.setActive(user.isActive());


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
