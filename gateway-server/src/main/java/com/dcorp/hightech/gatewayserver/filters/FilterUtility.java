package com.dcorp.hightech.gatewayserver.filters;

import com.dcorp.hightech.gatewayserver.constants.GatewayConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(GatewayConstants.CORRELATION_ID) != null) {
            List<String> requestHeaderList = requestHeaders.get(GatewayConstants.CORRELATION_ID);

            return requestHeaderList.stream().findFirst().get();
        }

        return null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange
                .mutate()
                .request(
                        exchange
                                .getRequest()
                                .mutate()
                                .header(name, value)
                                .build()
                )
                .build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationID) {
        return this.setRequestHeader(
                exchange,
                GatewayConstants.CORRELATION_ID,
                correlationID
        );
    }
}
