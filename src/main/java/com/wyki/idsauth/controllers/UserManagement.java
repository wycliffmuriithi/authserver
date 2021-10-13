package com.wyki.idsauth.controllers;

import com.wyki.idsauth.controllers.wrappers.ResponseWrapper;
import com.wyki.idsauth.controllers.wrappers.UserWrapper;
import com.wyki.idsauth.services.dao.UsersDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user") @Slf4j
public class UserManagement {
    @Autowired
    UsersDao usersDao;

    @PostMapping("/register")
    public ResponseWrapper registerUser(@RequestBody UserWrapper userWrapper) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String createdby = auth.getName();
//        log.info("created by "+auth.getPrincipal()+" "+createdby);

        boolean register = usersDao.registerUser(userWrapper.getName(), "", userWrapper.getEmail(), userWrapper.getPhonenumber(),
                new Date(), "", "Kenyan", "99999", userWrapper.getRoleid(), createdby);

        ResponseWrapper response = new ResponseWrapper();
        if (register) {
            usersDao.updateUserPassword(userWrapper.getEmail(), userWrapper.getPhonenumber(), userWrapper.getPassword());

            response.setStatus("success");
            response.setBody("user created successfully");

        } else {
            response.setStatus("failed");
            response.setBody("user creation failed, duplicate details");
        }
        return response;
    }

    @GetMapping("/roles")
    public ResponseWrapper getRoles(){
        ResponseWrapper response = new ResponseWrapper();
        response.setStatus("success");
        response.setBody(usersDao.allRoles());

        return response;
    }

    @GetMapping("/users")
    public ResponseWrapper getUsers(Pageable pageable){
        ResponseWrapper response = new ResponseWrapper();
        response.setStatus("success");
        response.setBody(usersDao.allUsers(pageable));

        return response;
    }
}
