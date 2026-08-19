package com.hms.user.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60L;
    public static final String SECRET =
            "SSWuHijaMXSWyVtU2NERpKPGXF0hPN2/7a6WPuq3RI65e0kpWAuWhtUhslVdEl/zutvYh6TRanlcJhkJ+GQZ5Q==";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                io.jsonwebtoken.io.Decoders.BASE64.decode(SECRET)
        );
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        CustomUserDetails user = (CustomUserDetails) userDetails;

        claims.put("id", user.getId());
        claims.put("username", user.getEmail());
        claims.put("role", user.getRole());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("profileId", user.getProfileId());

        return doGenerateToken(claims, user.getEmail());
    }

    public String doGenerateToken(
            Map<String, Object> claims,
            String subject) {

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(now))
                .expiration(new Date(
                        now + JWT_TOKEN_VALIDITY * 1000
                ))
                .signWith(getSigningKey())
                .compact();
    }
}
