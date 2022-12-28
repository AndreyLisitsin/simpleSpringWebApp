package com.lisitsin.simpleRestWithSpring.security.jwt;

import com.lisitsin.simpleRestWithSpring.model.Role;
import com.lisitsin.simpleRestWithSpring.model.Status;
import com.lisitsin.simpleRestWithSpring.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory(){
    }

    public static JwtUser create(UserEntity user){
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getStatus().equals(Status.ACTIVE), mapToGrantedAuthorities(user.getRoles()), user.getEvents());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles){
        return userRoles.stream().map( r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

}
