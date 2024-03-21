package com.exemple.geteway.Util;

import com.exemple.geteway.model.DataFromToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import static com.exemple.geteway.constants.JWTUtil.SECRET;

@Component
public class JwtUtil {


    public void validateToken(final String token)
    {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWT.require(algorithm).build().verify(token);
    }

    public DataFromToken getFromToken(String token)
    {
        DecodedJWT decodedJWT = JWT.decode(token);
        Long idUser = decodedJWT.getClaim("id").asLong();
        String username = decodedJWT.getSubject();
        String email = decodedJWT.getClaim("email").asString();
        Long agenceId = decodedJWT.getClaim("agenceId").asLong();

        return new DataFromToken(idUser, username, email, agenceId);
    }
}
