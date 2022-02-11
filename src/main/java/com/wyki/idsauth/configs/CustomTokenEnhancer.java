package com.wyki.idsauth.configs;

import com.wyki.idsauth.db.entities.Users;
import com.wyki.idsauth.services.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Class name: CustomTokenEnhancer
 * Creater: wgicheru
 * Date:6/17/2019
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Autowired
    UsersDao usersDao;
    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        User user = (User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Users dbuser =usersDao.loadUserByusername(user.getUsername()).get();

//        String resourcename = resourceids.toArray(new String[resourceids.size()])[0];
        additionalInfo.put("name", dbuser.getFirstname());
        additionalInfo.put("employeenumber", dbuser.getEmployeenumber());
        additionalInfo.put("department", dbuser.getDepartment());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
                additionalInfo);
        return accessToken;
    }

}
