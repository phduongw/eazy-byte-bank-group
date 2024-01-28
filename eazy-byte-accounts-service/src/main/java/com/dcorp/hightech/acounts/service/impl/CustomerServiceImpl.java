package com.dcorp.hightech.acounts.service.impl;

import com.dcorp.hightech.acounts.controllers.dto.AccountsDTO;
import com.dcorp.hightech.acounts.controllers.dto.CustomerDTO;
import com.dcorp.hightech.acounts.controllers.dto.CustomerDetailsDTO;
import com.dcorp.hightech.acounts.entities.AccountsEntity;
import com.dcorp.hightech.acounts.entities.CustomerEntity;
import com.dcorp.hightech.acounts.exception.ResourceNotFoundException;
import com.dcorp.hightech.acounts.repositories.AccountsRepository;
import com.dcorp.hightech.acounts.repositories.CustomerRepository;
import com.dcorp.hightech.acounts.service.CustomerService;
import com.dcorp.hightech.acounts.service.clients.CardsFeignClient;
import com.dcorp.hightech.acounts.service.clients.LoansFeignClient;
import com.dcorp.hightech.acounts.service.clients.dto.CardsDto;
import com.dcorp.hightech.acounts.service.clients.dto.LoansDto;
import com.dcorp.hightech.acounts.service.mapper.AccountsMapper;
import com.dcorp.hightech.acounts.service.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId) {
        CustomerEntity customer = findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
                );

        AccountsEntity account = accountsRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString())
                );

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDTO(customer, new CustomerDetailsDTO());
        customerDetailsDTO.setAccount(AccountsMapper.mapToAccountsDTO(account, new AccountsDTO()));
        customerDetailsDTO.setLoansDto(fetchLoansDTO(mobileNumber, correlationId).getBody());
        customerDetailsDTO.setCardsDto(fetchCardsDTO(mobileNumber, correlationId).getBody());
        return customerDetailsDTO;
    }

    private Optional<CustomerEntity> findCustomerByMobileNumber(String mobileNumber) {
        return customerRepository.findByMobileNumber(mobileNumber);
    }

    private ResponseEntity<LoansDto> fetchLoansDTO(String mobileNumber, String correlationId) {
        return loansFeignClient.fetchLoansDetails(mobileNumber, correlationId);
    }

    private ResponseEntity<CardsDto> fetchCardsDTO(String mobileNumber, String correlationId) {
        return cardsFeignClient.fetchCardsDetails(mobileNumber, correlationId);
    }

}
