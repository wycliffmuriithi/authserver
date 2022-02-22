package com.wyki.idsauth.controllers;

import com.wyki.idsauth.controllers.wrappers.ResponseWrapper;
import com.wyki.idsauth.controllers.wrappers.UserWrapper;
import com.wyki.idsauth.db.entities.Users;
import com.wyki.idsauth.services.dao.UsersDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
@Slf4j
public class UserController {
    @Autowired
    UsersDao usersDao;


    @GetMapping("/details")
    public ResponseWrapper getStaffDetails() {
        User loggedinuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatus("success");
        Users user = usersDao.loadUserByusername(loggedinuser.getUsername()).get();

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
        response.setBody(userWrapper);

        return response;
    }
}
