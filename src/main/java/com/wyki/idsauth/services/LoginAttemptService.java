package com.wyki.idsauth.services;

import com.wyki.idsauth.db.entities.Users;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Class name: LoginAttemptService
 * Creater: wgicheru
 * Date:3/26/2020
 */
@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPT = 4;


    public void loginSucceeded(String accountname) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String accountname) {
        int attempts = 0;
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(Users key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
