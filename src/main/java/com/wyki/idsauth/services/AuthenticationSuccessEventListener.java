package com.wyki.idsauth.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * Class name: AuthenticationSuccessEventListener
 * Creater: wgicheru
 * Date:3/26/2020
 */
@Component @Slf4j
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(AuthenticationSuccessEvent e) {


//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) e.getAuthentication().getPrincipal();
//        loginAttemptService.loginSucceeded(user.getUsername());
    }
}
