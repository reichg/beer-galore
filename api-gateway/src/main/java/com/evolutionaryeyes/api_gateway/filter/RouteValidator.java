package com.evolutionaryeyes.api_gateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of("/auth/register",
                                                                "/auth/login",
                                                                "/eureka",
                                                                "/api-docs/**",
                                                                "/swagger-ui.html",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**",
                                                                "/swagger-resources/**",
                                                                "/aggregate/**"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
                                                                               .noneMatch(
                                                                                       endpoint -> request.getURI()
                                                                                                             .getPath()
                                                                                                             .contains(endpoint)
                                                                               );
}
