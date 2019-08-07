package com.wyki.idsauth.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * Class name: OAuth2ResourceServerConfig
 * Creater: wgicheru
 * Date:6/17/2019
 */
@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.
                anonymous().disable()
                .authorizeRequests()
                .antMatchers ("/oauth/token", "/oauth/check_token"). permitAll ()
                .and().exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }
}
