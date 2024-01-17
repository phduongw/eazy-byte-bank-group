package com.dcorp.hightech.acounts.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer ad Account Information"
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO {

    @Schema(
            description = "Name of customer",
            example = "Egan Pham"
    )
    @NotEmpty(message = "Name can't be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    String name;

    @Schema(
            description = "Email of customer",
            example = "duongph.4a@gmail.com"
    )
    @Email(message = "Email address should be a valid value")
    @NotEmpty(message = "Email address can't be a null or empty")
    String email;

    @Schema(
            description = "Mobile Number of customer",
            example = "0989211621"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    String mobileNumber;

    @Schema(
            description = "Account Details of Customer"
    )
    AccountsDTO account;
}
