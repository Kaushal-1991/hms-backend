package com.hms.gateway.filter;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {

    // JWT secret - Base64 encoded
    private static final String SECRET =
            "SSWuHijaMXSWyVtU2NERpKPGXF0hPN2/7a6WPuq3RI65e0kpWAuWhtUhslVdEl/zutvYh6TRanlcJhkJ+GQZ5Q==";

    // Secret used for Gateway -> UserMS communication
    private static final String SECRET_KEY =
            "SSWuHijaMXSWyVtU2NERpKPGXF0hPN2";

    private final SecretKey key;

    public TokenFilter() {

        super(Config.class);

        this.key = Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(SECRET)
        );
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String path = exchange.getRequest()
                    .getPath()
                    .toString();

            HttpMethod method = exchange.getRequest()
                    .getMethod();

            System.out.println(
                    "Gateway Request: " + method + " " + path
            );

            // =====================================================
            // 1. CORS PREFLIGHT REQUEST
            // =====================================================

            /*
             * Browser sends OPTIONS before POST/PUT/etc.
             *
             * Example:
             * OPTIONS /user/register
             *
             * OPTIONS request does NOT contain JWT.
             *
             * Therefore, never validate JWT for OPTIONS.
             */

            if (HttpMethod.OPTIONS.equals(method)) {

                System.out.println(
                        "CORS Preflight Request - Bypassing JWT"
                );

                return chain.filter(exchange);
            }

            // =====================================================
            // 2. PUBLIC APIs
            // =====================================================

            /*
             * These APIs don't require JWT.
             */

            if (path.equals("/user/register")
                    || path.equals("/user/login")) {

                System.out.println(
                        "Public API - Bypassing JWT: " + path
                );

                exchange = exchange.mutate()
                        .request(request -> request.header(
                                "X-Secret-Key",
                                SECRET_KEY
                        ))
                        .build();

                return chain.filter(exchange);
            }

            // =====================================================
            // 3. GET AUTHORIZATION HEADER
            // =====================================================

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            System.out.println(
                    "Authorization Header: " + authHeader
            );

            // =====================================================
            // 4. CHECK AUTHORIZATION HEADER
            // =====================================================

            if (authHeader == null || authHeader.isBlank()) {

                System.out.println(
                        "Authorization header is missing"
                );

                throw new RuntimeException(
                        "Authorization header is missing"
                );
            }

            // =====================================================
            // 5. CHECK BEARER TOKEN
            // =====================================================

            if (!authHeader.startsWith("Bearer ")) {

                System.out.println(
                        "Authorization header is invalid"
                );

                throw new RuntimeException(
                        "Authorization header is invalid"
                );
            }

            // =====================================================
            // 6. EXTRACT JWT
            // =====================================================

            String token = authHeader.substring(7);

            if (token.isBlank()) {

                throw new RuntimeException(
                        "JWT token is missing"
                );
            }

            // =====================================================
            // 7. VALIDATE JWT
            // =====================================================

            try {

                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                System.out.println(
                        "JWT Valid"
                );

                System.out.println(
                        "Claims: " + claims
                );

                // =================================================
                // 8. ADD INTERNAL SECRET HEADER
                // =================================================

                exchange = exchange.mutate()
                        .request(request -> request.header(
                                "X-Secret-Key",
                                SECRET_KEY
                        ))
                        .build();

                // =================================================
                // 9. CONTINUE REQUEST
                // =================================================

                return chain.filter(exchange);

            } catch (Exception e) {

                System.out.println(
                        "JWT validation failed: "
                                + e.getMessage()
                );

                throw new RuntimeException(
                        "Invalid or expired JWT token"
                );
            }
        };
    }
}