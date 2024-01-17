package com.dcorp.hightech.acounts.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountsEntity extends BaseEntity{

    @Id
    Long accountNumber;

    Long customerId;

    String accountType;

    String branchAddress;

}
