package com.gopi.job.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class JwtProvider {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication, Long userId) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("email", authentication.getName())
                .claim("userId", userId)
                .claim("authorities", roles)
                .signWith(secretKey)
                .compact();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<String> auths = new LinkedHashSet<>();

        for(GrantedAuthority auth : authorities) {
            auths.add(auth.getAuthority());
        }

        return String.join(",", auths);
    }
}
