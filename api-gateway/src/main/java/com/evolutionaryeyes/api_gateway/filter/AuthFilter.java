package com.evolutionaryeyes.api_gateway.filter;

import com.evolutionaryeyes.api_gateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthFilter()
    {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config)
    {
        return ((exchange, chain) ->
        {
            ServerHttpRequest request = null;
            if (routeValidator.isSecured.test(exchange.getRequest()))
            {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                {
                    throw new RuntimeException("Missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token = "";
                if (authHeader != null && authHeader.startsWith("Bearer "))
                {
                    token = authHeader.substring(7);
                }

                try
                {
                    log.info("checking token in header");
                    jwtUtil.validateToken(token);

                    request = exchange.getRequest().mutate().header("user", jwtUtil.extractUser(token)).build();
                    log.info(request.getHeaders().get("user").get(0));
                } catch (Exception e)
                {
                    throw new RuntimeException("Unauthorized access to application.");
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config {

    }
}
