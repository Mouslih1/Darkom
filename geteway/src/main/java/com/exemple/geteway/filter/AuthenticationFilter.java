package com.exemple.geteway.filter;

import com.exemple.geteway.Util.JwtUtil;
import com.exemple.geteway.helpers.JWTHelper;
import com.exemple.geteway.model.DataFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import static com.exemple.geteway.constants.JWTUtil.BEARER_PREFIX;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JWTHelper jwtHelper;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest()))
            {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                String authorizationHeader = jwtHelper.extractTokenFromHeaderIfExists(authHeader);

                try {

                    jwtUtil.validateToken(authorizationHeader);
                    DataFromToken dataUserFromToken = jwtUtil.getFromToken(authorizationHeader);
                    return chain.filter(
                            exchange.mutate().request(
                                            exchange.getRequest().mutate()
                                                    .header("id", String.valueOf(dataUserFromToken.getId()))
                                                    .header("username", dataUserFromToken.getUsername())
                                                    .header("email", dataUserFromToken.getEmail())
                                                    .header("agenceId", String.valueOf(dataUserFromToken.getAgenceId()))
                                                    .build())
                                    .build()
                    );
                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
