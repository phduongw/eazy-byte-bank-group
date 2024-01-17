package com.dcorp.hightech.acounts.service.impl;

import com.dcorp.hightech.acounts.constants.AccountConstants;
import com.dcorp.hightech.acounts.controllers.dto.AccountsDTO;
import com.dcorp.hightech.acounts.controllers.dto.CustomerDTO;
import com.dcorp.hightech.acounts.entities.AccountsEntity;
import com.dcorp.hightech.acounts.entities.CustomerEntity;
import com.dcorp.hightech.acounts.exception.CustomerAlreadyExistsException;
import com.dcorp.hightech.acounts.exception.ResourceNotFoundException;
import com.dcorp.hightech.acounts.mapper.AccountsMapper;
import com.dcorp.hightech.acounts.mapper.CustomerMapper;
import com.dcorp.hightech.acounts.repositories.AccountsRepository;
import com.dcorp.hightech.acounts.repositories.CustomerRepository;
import com.dcorp.hightech.acounts.service.AccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final Random random = new Random();

    @Override
    public void createAccount(CustomerDTO request) {
        CustomerEntity initialCustomer = CustomerMapper.mapToCustomerEntity(request, new CustomerEntity());
        Optional<CustomerEntity> optionalCustomer = findCustomerByMobileNumber(initialCustomer.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number " + initialCustomer.getMobileNumber());
        }

        initialCustomer.setCreatedAt(LocalDateTime.now());
        initialCustomer.setCreatedBy("Anonymous");
        CustomerEntity customer = customerRepository.save(initialCustomer);

        accountsRepository.save(createNewAccount(customer));
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        CustomerEntity customer = findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
                );

        AccountsEntity account = accountsRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString())
                );

        CustomerDTO response = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        response.setAccount(AccountsMapper.mapToAccountsDTO(account, new AccountsDTO()));

        return response;
    }

    @Override
    public boolean updateAccount(CustomerDTO request) {
        AccountsDTO inputAccount = request.getAccount();
        if (Objects.nonNull(inputAccount)) {
            AccountsEntity account = accountsRepository
                    .findById(inputAccount.getAccountNumber())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Account", "Account Number", inputAccount.getAccountNumber().toString())
                    );
            AccountsMapper.mapToAccountsEntity(inputAccount, account);
            account = accountsRepository.save(account);

            Long customerId = account.getCustomerId();
            CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "Customer Id", customerId.toString())
            );
            CustomerMapper.mapToCustomerEntity(request, customer);
            customerRepository.save(customer);

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        CustomerEntity customer = findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
                );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private AccountsEntity createNewAccount(CustomerEntity customer) {
        AccountsEntity account = new AccountsEntity();
        account.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 1000000000L + random.nextInt(900000000);

        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);

        return account;
    }

    private Optional<CustomerEntity> findCustomerByMobileNumber(String mobileNumber) {
        return customerRepository.findByMobileNumber(mobileNumber);
    }
}
