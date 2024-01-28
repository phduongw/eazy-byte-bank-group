package com.dcorp.hightech.acounts.service.clients.fallback;

import com.dcorp.hightech.acounts.service.clients.CardsFeignClient;
import com.dcorp.hightech.acounts.service.clients.LoansFeignClient;
import com.dcorp.hightech.acounts.service.clients.dto.CardsDto;
import com.dcorp.hightech.acounts.service.clients.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {

    @Override
    public ResponseEntity<CardsDto> fetchCardsDetails(String mobileNumber, String correlationId) {
        return null;
    }
}
