package com.dcorp.hightech.acounts.service;

import com.dcorp.hightech.acounts.controllers.dto.CustomerDTO;

public interface AccountsService {

    void createAccount(CustomerDTO request);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Account Details based on a given mobileNumber
     */
    CustomerDTO fetchAccount(String mobileNumber);

    /**
     * @param request - CustomerDTO Object
     * @return boolean indicating if the update of Account Details is successful or not
     */
    boolean updateAccount(CustomerDTO request);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account Details is successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
