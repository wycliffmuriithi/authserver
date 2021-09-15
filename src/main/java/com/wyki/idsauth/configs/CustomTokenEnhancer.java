package com.wyki.idsauth.configs;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class name: CustomTokenEnhancer
 * Creater: wgicheru
 * Date:6/17/2019
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        Set<String> resourceids = authentication.getOAuth2Request().getResourceIds();
//        String resourcename = resourceids.toArray(new String[resourceids.size()])[0];
//        additionalInfo.put(resourcename, authentication.);
        additionalInfo.put("status","success");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
                additionalInfo);
        return accessToken;
    }
}
