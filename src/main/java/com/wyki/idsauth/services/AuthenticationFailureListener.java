package com.wyki.idsauth.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * Class name: AuthenticationFailureListener
 * Creater: wgicheru
 * Date:3/26/2020
 */
@Component @Slf4j
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
//        log.info("principal "+e.getAuthentication().getPrincipal());
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        loginAttemptService.loginFailed(auth.getName());
    }
}
