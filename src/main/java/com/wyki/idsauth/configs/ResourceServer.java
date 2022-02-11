package com.wyki.idsauth.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServer   extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().authorizeRequests()
//                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/user/**").permitAll();
//                .hasAuthority("ROLE_ADMIN");

    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config
                .resourceId("staffapp");

    }
}
