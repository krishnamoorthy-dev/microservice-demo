package com.krishna.api_gateway.filter;

import com.krishna.api_gateway.util.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JWTAuthFilter extends AbstractGatewayFilterFactory<JWTAuthFilter.Config> {

    public JWTAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
                ServerHttpRequest request = exchange.getRequest();
                if(!request.getHeaders().containsHeader(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
                }
                String authToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authToken == null || !authToken.startsWith("Bearer ")) {
                    return onError(exchange, "Invalid Authorization Header Format", HttpStatus.UNAUTHORIZED);
                }
                authToken = authToken.substring(7);
                // Verify token
                if(!JWTUtil.verifyToken(authToken)) {
                    return onError(exchange, "Invalid or Expired Token", HttpStatus.UNAUTHORIZED);
                }
                return chain.filter(exchange);
            });
    }

    public static class Config {}

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorResponse = String.format("{\"error\": \"%s\", \"status\": %d}", err, httpStatus.value());
        DataBuffer buffer = response.bufferFactory().wrap(errorResponse.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
