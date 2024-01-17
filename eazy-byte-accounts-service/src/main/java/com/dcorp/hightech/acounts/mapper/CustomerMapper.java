package com.dcorp.hightech.acounts.mapper;

import com.dcorp.hightech.acounts.controllers.dto.AccountsDTO;
import com.dcorp.hightech.acounts.controllers.dto.CustomerDTO;
import com.dcorp.hightech.acounts.entities.AccountsEntity;
import com.dcorp.hightech.acounts.entities.CustomerEntity;

public class CustomerMapper {

    private CustomerMapper() {

    }

    public static CustomerDTO mapToCustomerDTO(CustomerEntity source, CustomerDTO target) {
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setMobileNumber(source.getMobileNumber());

        return target;
    }

    public static CustomerEntity mapToCustomerEntity(CustomerDTO source, CustomerEntity target) {
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setMobileNumber(source.getMobileNumber());

        return target;
    }

}
