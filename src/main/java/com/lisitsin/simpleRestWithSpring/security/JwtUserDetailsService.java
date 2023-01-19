package com.lisitsin.simpleRestWithSpring.security;

import com.lisitsin.simpleRestWithSpring.security.jwt.JwtUser;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import com.lisitsin.simpleRestWithSpring.security.jwt.JwtUserFactory;
import com.lisitsin.simpleRestWithSpring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userService.findByUsername(username);

        if (user == null){
            log.info("User with this username not found");
            throw new UsernameNotFoundException("User with this username not found");
        }

        JwtUser jwtUser1 = JwtUserFactory.create(user);
        log.info("user with username: {} successfully load", username);
        return jwtUser1;
    }

}
