package com.wyki.idsauth.services.dao;

import com.wyki.idsauth.controllers.wrappers.ResponseWrapper;
import com.wyki.idsauth.controllers.wrappers.RoleWrapper;
import com.wyki.idsauth.controllers.wrappers.UserStats;
import com.wyki.idsauth.controllers.wrappers.UserWrapper;
import com.wyki.idsauth.db.ActivedirectoryRepo;
import com.wyki.idsauth.db.RolesRepo;
import com.wyki.idsauth.db.UserrolesRepo;
import com.wyki.idsauth.db.UsersRepo;
import com.wyki.idsauth.db.entities.ActiveDirectory;
import com.wyki.idsauth.db.entities.Roles;
import com.wyki.idsauth.db.entities.Userroles;
import com.wyki.idsauth.db.entities.Users;
import com.wyki.idsauth.services.ImposterService;
import com.wyki.idsauth.services.utils.SendEmail;
import com.wyki.idsauth.wrappers.ImposterWrapper;
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
    ImposterService imposterService;
    @Autowired
    ActivedirectoryRepo activedirectoryRepo;
    @Autowired
    SendEmail sendEmail;

    @Value("${otp.expiry.timeinminutes}")
    int otpexpirytime;

    @Value("${otp.email.message}")
    String otpusermessage;
    @Value("${otp.email.subject}")
    String otpusersubject;


//    public UsersDao(UsersRepo usersRepo, BCryptPasswordEncoder passwordEncoder,RolesRepo roleRepo){
//        this.dbusersRepo=usersRepo;
//        this.encoder=passwordEncoder;
//        this.roleRepo=roleRepo;
//    }

    @Transactional
    public Optional<Users> loadUserByusername(String username) {
        List<Users> dbusersList = dbusersRepo.findByEmailOrPhonenumberOrNationalidnumber(username, username,username);
        if (dbusersList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(dbusersList.get(0));
        }
    }

    @Transactional
    public ResponseWrapper registerUser(String identificationnumber) {
        List<Users> dbusersList = dbusersRepo.findByNationalidnumber(identificationnumber);
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
//                    dbUser.setEmail(email);
//                    dbUser.setPhonenumber(phonenumber);
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

                    dbUser = dbusersRepo.save(dbUser);

                    responseWrapper = getActiveDirectoryDetails(staffDetails.getOthernames() + " " + staffDetails.getSurname());
                    if (responseWrapper.getStatus().equals("success")) {
                        ActiveDirectory activedirectoryuser = (ActiveDirectory) responseWrapper.getBody();
                        dbUser.setEmail(activedirectoryuser.getEmail());
                        dbUser.setPhonenumber(activedirectoryuser.getTelephone());

                        //generate OTP
                        int otp = 10000 + new Random().nextInt(9999);
                        dbUser.setOtp(String.valueOf(otp));
                        dbUser.setValidotp(true);
                        Calendar c = Calendar.getInstance();
                        c.setTime(new Date());
                        c.add(Calendar.MINUTE, otpexpirytime);
                        dbUser.setOtpexpirytime(c.getTime());
                        responseWrapper.setStatus("success");
                        responseWrapper.setBody("user created, use otp to set password");
                        try {
                            sendEmail.sendEmail(activedirectoryuser.getEmail(), String.format(otpusermessage, otp), otpusersubject);
                        } catch (Exception ex) {
                            log.error(ex.getMessage(),ex);
                            responseWrapper.setBody("could not send email, use forgot password for new otp");
                        }


//                        dbUser =
                        dbusersRepo.save(dbUser);
                    }
//                    addRoles(roleid, dbUser);

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

    public ResponseWrapper forgotPassword(String nationalid){
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
            try {
                sendEmail.sendEmail(dbUser.getEmail(), String.format(otpusermessage, otp), otpusersubject);
            } catch (Exception ex) {
                log.error(ex.getMessage(),ex);
                responseWrapper.setBody("could not send email, use forgot password for new otp");
            }


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
                    userWrapper.setName(user.getFirstname() + " " + user.getOthernames());
                    userWrapper.setPhonenumber(user.getPhonenumber());
                    userWrapper.setEmployeenumber(user.getEmployeenumber());
                    userWrapper.setRegion(user.getRegion());
                    userWrapper.setDepartment(user.getDepartment());
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


    private ResponseWrapper getActiveDirectoryDetails(String name) {
        name = name.toUpperCase();
        log.info("searching for ad user " + name);
        List<ActiveDirectory> activedirectoryUsers = activedirectoryRepo.findByStaffname(name);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        if (activedirectoryUsers.isEmpty()) {
            responseWrapper.setStatus("failed");
            responseWrapper.setBody("Could not find user from AD");
        } else if (activedirectoryUsers.size() > 1) {
            responseWrapper.setStatus("failed");
            responseWrapper.setBody("Multiple users returned with same name from AD");
        } else {
            responseWrapper.setStatus("success");
            responseWrapper.setBody(activedirectoryUsers.get(0));
        }
        return responseWrapper;

    }
}
