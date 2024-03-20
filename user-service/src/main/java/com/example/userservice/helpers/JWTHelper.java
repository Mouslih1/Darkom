package com.example.userservice.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.userservice.constant.JWTUtil.*;

@Component
public class JWTHelper {

    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String generateAccessToken(Long id,String username,Long agenceId , List<String> roles)
    {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_ACCESS_TOKEN))
                .withIssuer(ISSUER)
                .withClaim("roles",roles)
                .withClaim("id", id)
                .withClaim("agenceId", agenceId)
                .sign(algorithm);
    }

    public String generateRefreshToken(String username)
    {
        return JWT
                .create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_REFRESH_TOKEN))
                .withIssuer(ISSUER)
                .sign(algorithm);
    }

    public String extractTokenFromHeaderIfExists(String authorizationHeader)
    {
        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX))
        {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    public Map<String, String> getTokensMap(String jwtAccessToken, String jwtRefreshToken)
    {
        Map<String, String> idTokens = new HashMap<>();
        idTokens.put("accessToken", jwtAccessToken);
        idTokens.put("refreshToken", jwtRefreshToken);

        return idTokens;
    }
}
