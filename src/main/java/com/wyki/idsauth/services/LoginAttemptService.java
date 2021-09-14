package com.wyki.idsauth.services;

import com.wyki.idsauth.services.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class name: LoginAttemptService
 * Creater: wgicheru
 * Date:3/26/2020
 */
@Service
public class LoginAttemptService {
    @Autowired
    UsersDao usersDao;
    private final int MAX_ATTEMPT = 4;


    public void loginSucceeded(String accountname) {
        usersDao.updateAttempts(accountname, 0);
    }

    public void loginFailed(String accountname) {
        int attempts = usersDao.getUserAttempts(accountname);
        attempts++;
        usersDao.updateAttempts(accountname, attempts);
    }

    public boolean isBlocked(String accountname) {
        return usersDao.getUserAttempts(accountname) >= MAX_ATTEMPT;
    }
}
