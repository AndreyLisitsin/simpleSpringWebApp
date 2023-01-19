package com.lisitsin.simpleRestWithSpring.security.jwt;

import com.lisitsin.simpleRestWithSpring.model.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private Long validityInMilliSeconds;

    @Autowired
    private  UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(UserEntity user){

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", getRoleNames(user.getRoles()));
        claims.put("id", user.getId());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliSeconds);

        return  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        log.info("Returning new UsernamePasswordAuthenticationToken");
        return  new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer_")){
            return bearerToken.substring(7);
        }
        return  null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private List<String> getRoleNames(List<Role> roles){
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }


    public Long getUserId(String bearerToken) {
        String token = bearerToken.substring(7);
        DefaultClaims claims = (DefaultClaims) Jwts.parser().setSigningKey(secret).parse(token).getBody();
        return claims.get("id", Long.class);
    }

    public List<String> getUsersAuthorities(String bearerToken) {
        String token = bearerToken.substring(7);
        DefaultClaims claims = (DefaultClaims) Jwts.parser().setSigningKey(secret).parse(token).getBody();
        return claims.get("roles", List.class);
    }
}
