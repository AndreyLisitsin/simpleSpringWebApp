package com.lisitsin.simpleRestWithSpring.config;

import com.lisitsin.simpleRestWithSpring.security.jwt.JwtConfigurer;
import com.lisitsin.simpleRestWithSpring.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String USER_ENDPOINT = "/api/v1/users/**";
    private static final String EVENT_ENDPOINT = "/api/v1/events/**";
    private static final String DOWNLOAD_ENDPOINT = "/api/v1/download/**";
    private static final String FILE_ENDPOINT = "/api/v1/files/**";
    private static final String REGISTRATION_ENDPOINT = "/api/v1/registration";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(REGISTRATION_ENDPOINT).not().authenticated()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(DOWNLOAD_ENDPOINT).not().authenticated()
                .antMatchers(USER_ENDPOINT).permitAll()
                .antMatchers(EVENT_ENDPOINT).permitAll()
                .antMatchers(FILE_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
