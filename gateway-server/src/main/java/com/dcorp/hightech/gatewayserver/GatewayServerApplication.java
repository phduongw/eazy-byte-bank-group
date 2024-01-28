package com.dcorp.hightech.gatewayserver;

import com.dcorp.hightech.gatewayserver.constants.GatewayConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
								.circuitBreaker(config -> config
										.setName("accountsCircuitBreaker") //Create a Circuit Breaker
										.setFallbackUri("forward:/contactSupport")

								)
						)
                        .uri("lb://ACCOUNTS")
                )
				.route(path -> path
						.path("/d-corp/cards/**")
						.filters(f -> f
								.rewritePath("/d-corp/cards/(?<segment>.*)", GatewayConstants.SEGMENT_REGEX)
								.addResponseHeader(GatewayConstants.X_RESPONSE_HEADER, LocalDateTime.now().toString()))
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

}
