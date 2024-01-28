package com.dcorp.hightech.acounts.service;

import com.dcorp.hightech.acounts.controllers.dto.CustomerDetailsDTO;

public interface CustomerService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @param correlationId - Input Correlation ID is included from gateway server
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId);

}
