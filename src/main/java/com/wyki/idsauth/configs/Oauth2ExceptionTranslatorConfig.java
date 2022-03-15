package com.wyki.idsauth.configs;

import com.wyki.idsauth.db.entities.Users;
import com.wyki.idsauth.services.dao.UsersDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import java.util.Optional;

@Configuration @Slf4j
public class Oauth2ExceptionTranslatorConfig{
    @Autowired
    private UsersDao dbusersDao;
    @Bean
    public WebResponseExceptionTranslator oauth2ResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
//                log.error(e.getMessage(),e);
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                OAuth2Exception body = responseEntity.getBody();
                HttpStatus statusCode = responseEntity.getStatusCode();
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                log.info(">>>"+user.getUsername());
                Optional<Users> databaseusercontainer = dbusersDao.loadUserByusername(user.getUsername());
                if(databaseusercontainer.isPresent()) {
                    Users databaseuser= databaseusercontainer.get();

                    if (!databaseuser.isActive()) {
                        log.info("not active");
                    }
                }else {
                    log.info("cannot load user");
                }

//                body.addAdditionalInformation("timestamp",new Date().toString());
//                body.addAdditionalInformation("status", String.valueOf(body.getHttpErrorCode()));
                body.addAdditionalInformation("error_description", body.getMessage());

//                body.addAdditionalInformation("error", body.getOAuth2ErrorCode().toUpperCase());

                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                // do something with header or response
                return new ResponseEntity<>(body, headers, statusCode);
            }
        };
    }

}
