package com.wyki.idsauth.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

public class ServerEndpointsConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    JsonToUrlEncodedAuthenticationFilter jsonFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jsonFilter, ChannelProcessingFilter.class);
//                .csrf().and().httpBasic().disable()
//                .authorizeRequests()
//                .antMatchers("/test").permitAll()
//                .antMatchers("/secured").authenticated();
    }
}
