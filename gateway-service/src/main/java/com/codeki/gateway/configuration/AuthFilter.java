package com.codeki.gateway.configuration;

import com.codeki.gateway.dto.ReqResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Value("${validate.token-url}")
    private String VALIDATE_TOKEN_URL;

    private final WebClient.Builder WEB_CLIENT;

    public AuthFilter(WebClient.Builder WEB_CLIENT) {
        super(Config.class);
        this.WEB_CLIENT = WEB_CLIENT;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String [] chunks = authHeader.split(" ");

            if (chunks.length != 2 || !chunks[0].equals("Bearer")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            return WEB_CLIENT.build()
                    .post()
                    .uri(VALIDATE_TOKEN_URL + chunks[1])
                    .retrieve()
                    .bodyToMono(ReqResponse.class)
                    .flatMap(reqResponse -> {
                        if (reqResponse.getStatusCode() == 200) {
                            exchange.getRequest()
                                    .mutate()
                                    .header("x-auth-token", String.valueOf(reqResponse.getToken()))
                                    .header("x-auth-user-name", String.valueOf(reqResponse.getUsername()));
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    });
        }));
    }

    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config {

    }
}