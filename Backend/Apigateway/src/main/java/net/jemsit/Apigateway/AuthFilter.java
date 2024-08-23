package net.jemsit.Apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    private final List<String> WHITE_LIST = List.of("/auth/login");

    @Value("${spring.auth.service.uri}")
    private String baseUri;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (permitAll(exchange)) return chain.filter(exchange);
        if (permitAuthenticated(exchange)) return chain.filter(exchange);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private static boolean matchesEndpoint(String inputString, String endpointPattern) {
        Pattern pattern = Pattern.compile(endpointPattern);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    public boolean permitAll(ServerWebExchange exchange) {
        String url = exchange.getRequest().getPath().toString();
        return WHITE_LIST.contains(url);

    }

    public boolean permitAuthenticated(ServerWebExchange exchange) {
        try {
            URI uri = new URI(baseUri + "/auth/validate-token");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", exchange.getRequest().getHeaders().get("Authorization").get(0));
            HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
            var restTemplate = new RestTemplate();
            var statusCode = restTemplate.postForObject(uri,  requestEntity, String.class);
            return statusCode.equals("Valid!");
        } catch (URISyntaxException | HttpClientErrorException e) {
            throw new RuntimeException("Wrong token");
        }
    }
}
