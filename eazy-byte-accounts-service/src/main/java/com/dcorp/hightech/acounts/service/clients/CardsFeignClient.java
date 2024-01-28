package com.dcorp.hightech.acounts.service.clients;

import com.dcorp.hightech.acounts.constants.AccountConstants;
import com.dcorp.hightech.acounts.service.clients.dto.CardsDto;
import com.dcorp.hightech.acounts.service.clients.fallback.CardsFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    ResponseEntity<CardsDto> fetchCardsDetails(@RequestParam String mobileNumber, @RequestHeader(AccountConstants.CORRELATION_DEFAULT) String correlationId);

}
