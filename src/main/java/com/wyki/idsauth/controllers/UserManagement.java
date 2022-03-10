package com.wyki.idsauth.controllers;

import com.wyki.idsauth.controllers.wrappers.AddUserDTO;
import com.wyki.idsauth.controllers.wrappers.PasswordDTO;
import com.wyki.idsauth.controllers.wrappers.ResponseWrapper;
import com.wyki.idsauth.services.dao.UsersDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserManagement {
    @Autowired
    UsersDao usersDao;

    @PostMapping("/register")
    public ResponseWrapper registerUser(@RequestBody AddUserDTO userWrapper) {

        ResponseWrapper responseWrapper = usersDao.registerUser(userWrapper.getNationalid());

//        if (responseWrapper.getStatus().equals("success")) {
//            usersDao.updateUserPassword(userWrapper.getEmail(), userWrapper.getPhonenumber(), userWrapper.getPassword());
//            response.setStatus("success");
//            response.setBody("user created successfully");

//        }
        return responseWrapper;
    }

    @PostMapping("/updatepassword")
    public ResponseWrapper updatePassword(@RequestBody PasswordDTO passwordDTO){
        return usersDao.updateUserPassword(passwordDTO.getNationalid(), passwordDTO.getOtp(), passwordDTO.getPassword());
    }

    @PostMapping("/forgotpassword")
    public ResponseWrapper forgotPassword(@RequestBody AddUserDTO addUserDTO){
        return usersDao.forgotPassword(addUserDTO.getNationalid());
    }

    @GetMapping("/roles")
    public ResponseWrapper getRoles() {
        ResponseWrapper response = new ResponseWrapper();
        response.setStatus("success");
        response.setBody(usersDao.allRoles());

        return response;
    }

    @GetMapping("/users")
    public ResponseWrapper getUsers(Pageable pageable) {
        ResponseWrapper response = new ResponseWrapper();
        response.setStatus("success");
        response.setBody(usersDao.allUsers(pageable));

        return response;
    }

    @GetMapping("/userstats")
    public ResponseWrapper getUserstats() {
        ResponseWrapper response = new ResponseWrapper();
        response.setStatus("success");
        response.setBody(usersDao.getUserStats());

        return response;
    }


}
