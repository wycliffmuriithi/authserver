package com.wyki.idsauth.controllers;

import com.wyki.idsauth.controllers.wrappers.ResponseWrapper;
import com.wyki.idsauth.controllers.wrappers.UserWrapper;
import com.wyki.idsauth.services.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserManagement {
    @Autowired
    UsersDao usersDao;

    @PostMapping("/register")
    public ResponseWrapper registerUser(@RequestBody UserWrapper userWrapper){
        boolean register = usersDao.registerUser(userWrapper.getName(),"", userWrapper.getEmail(), userWrapper.getPhonenumber(),
                new Date(),"","Kenyan","99999","","ADMIN");
        ResponseWrapper response = new ResponseWrapper();
        if(register){
            usersDao.updateUserPassword(userWrapper.getEmail(), userWrapper.getPhonenumber(), userWrapper.getPassword());

            response.setStatus("success");
            response.setBody("user created successfully");

        }else{
            response.setStatus("failed");
            response.setBody("user creation failed, duplicate details");
        }
        return response;
    }
}
