package com.dcorp.hightech.acounts.service.clients.fallback;

import com.dcorp.hightech.acounts.service.clients.LoansFeignClient;
import com.dcorp.hightech.acounts.service.clients.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoansDetails(String mobileNumber, String correlationId) {
        return null;
    }
}
