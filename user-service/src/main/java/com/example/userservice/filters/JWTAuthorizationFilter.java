package com.example.userservice.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice.entities.User;
import com.example.userservice.entities.enums.Role;
import com.example.userservice.helpers.JWTHelper;
import com.example.userservice.securities.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.example.userservice.constants.JWTUtil.AUTH_HEADER;
import static com.example.userservice.constants.JWTUtil.SECRET;

@AllArgsConstructor
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {


    private JWTHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException
    {
        String accessToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));

        if(accessToken != null)
        {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
            String username = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Long id = decodedJWT.getClaim("id").asLong();
            Long agenceId = decodedJWT.getClaim("agenceId").asLong();
            String email = decodedJWT.getClaim("email").asString();
            CustomUserDetails userDetails = new CustomUserDetails(User.builder().username(username).agenceId(agenceId).id(id).email(email).role(Role.valueOf(roles[0])).build());
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            for (String role : roles)
            {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }else {
            filterChain.doFilter(request, response);
        }
    }
}
