package com.wyki.idsauth.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Class name: SecurityConfig
 * Creater: wgicheru
 * Date:6/17/2019
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//

//    @Autowired
//    JsonToUrlEncodedAuthenticationFilter jsonAuthenticationFilter;
//            () throws Exception {
//        JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
////        System.out.println("jsonAuthenticationFilter");
//        return filter;
//    }

//    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(jsonAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
