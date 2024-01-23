package com.dcorp.hightech.acounts.service.mapper;

import com.dcorp.hightech.acounts.controllers.dto.AccountsDTO;
import com.dcorp.hightech.acounts.entities.AccountsEntity;

public class AccountsMapper {
    private AccountsMapper() {

    }

    public static AccountsDTO mapToAccountsDTO(AccountsEntity source, AccountsDTO target) {
        target.setAccountType(source.getAccountType());
        target.setAccountNumber(source.getAccountNumber());
        target.setBranchAddress(source.getBranchAddress());

        return target;
    }

    public static AccountsEntity mapToAccountsEntity(AccountsDTO source, AccountsEntity target) {
        target.setAccountType(source.getAccountType());
        target.setAccountNumber(source.getAccountNumber());
        target.setBranchAddress(source.getBranchAddress());

        return target;
    }

}
