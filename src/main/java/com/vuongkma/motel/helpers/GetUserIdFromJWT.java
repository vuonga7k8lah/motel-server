package com.vuongkma.motel.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

public class GetUserIdFromJWT {
    public static Long getUserId(Authentication authentication){
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Map<String, Object> claims = jwtAuth.getTokenAttributes();
            return (Long) claims.get("user_id");
        }
        throw new RuntimeException("Error token");
    }
}
