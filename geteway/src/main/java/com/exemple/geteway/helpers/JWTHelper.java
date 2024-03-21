package com.exemple.geteway.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.exemple.geteway.constants.JWTUtil.*;

@Component
public class JWTHelper {

    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String extractTokenFromHeaderIfExists(String authorizationHeader)
    {
        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX))
        {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }

}
