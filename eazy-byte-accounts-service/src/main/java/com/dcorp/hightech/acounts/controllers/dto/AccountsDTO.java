package com.dcorp.hightech.acounts.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account Information"
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountsDTO {

    @Schema(
            description = "Account Number of EazyBank Account",
            example = "0001233571"
    )
    @NotEmpty(message = "Account Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account Number must be 10 digits")
    Long accountNumber;


    @Schema(
            description = "Account Type of EazyBank Account",
            example = "Savings"
    )
    @NotEmpty(message = "Account Type can't be a bull or empty")
    String accountType;

    @Schema(
            description = "EazyBank branch address",
            example = "Ba Dinh Ha Noi"
    )
    @NotEmpty(message = "Branch Address can't be a null or empty")
    String branchAddress;

}
