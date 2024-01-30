package com.dcorp.hightech.gatewayserver;

import com.dcorp.hightech.gatewayserver.constants.GatewayConstants;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(path -> path
                        .path("/d-corp/accounts/**")
                        .filters(f -> f
                                        .rewritePath("/d-corp/accounts/(?<segment>.*)", GatewayConstants.SEGMENT_REGEX)
                                        .addResponseHeader(GatewayConstants.X_RESPONSE_HEADER, LocalDateTime.now().toString())
                                        // add circuit breaker start
                                        .circuitBreaker(config -> config
                                                .setName("accountsCircuitBreaker") //Create a Circuit Breaker
                                                .setFallbackUri("forward:/contactSupport")
                                        )
                                // add circuit breaker end

                        )
                        .uri("lb://ACCOUNTS")
                )
                .route(path -> path
                        .path("/d-corp/cards/**")
                        .filters(f -> f
                                .rewritePath("/d-corp/cards/(?<segment>.*)", GatewayConstants.SEGMENT_REGEX)
                                .addResponseHeader(GatewayConstants.X_RESPONSE_HEADER, LocalDateTime.now().toString())
                                .retry(retry -> retry
                                        .setRetries(3) // Retrying operation 3 times
                                        .setMethods(HttpMethod.GET) // It should be GET method, because using the POST or DELETE, data will be changed multiple time => wrong data
                                        .setBackoff(
                                                Duration.ofMillis(100), //Waiting in 100milliseconds when trying to initiate the first retry operation
                                                Duration.ofMillis(1000),
                                                3,
                                                true
                                        )
                                )
                        )
                        .uri("lb://CARDS")
                )
                .route(path -> path
                        .path("/d-corp/loans/**")
                        .filters(f -> f
                                .rewritePath("/d-corp/loans/(?<segment>.*)", GatewayConstants.SEGMENT_REGEX)
                                .addResponseHeader(GatewayConstants.X_RESPONSE_HEADER, LocalDateTime.now().toString()))
                        .uri("lb://LOANS")
                )
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                .build()
        );
    }

}
