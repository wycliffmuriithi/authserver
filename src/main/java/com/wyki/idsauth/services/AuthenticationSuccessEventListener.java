package com.wyki.idsauth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Class name: AuthenticationSuccessEventListener
 * Creater: wgicheru
 * Date:3/26/2020
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(AuthenticationSuccessEvent e) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        loginAttemptService.loginSucceeded(e.getAuthentication().getName());
    }
}
