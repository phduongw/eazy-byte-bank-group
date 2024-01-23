package com.dcorp.hightech.acounts.controllers;

import com.dcorp.hightech.acounts.controllers.dto.CustomerDetailsDTO;
import com.dcorp.hightech.acounts.controllers.response.ErrorResponse;
import com.dcorp.hightech.acounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "CRUD REST APIs for Customer Details in EazyBank",
        description = "CRUD REST APIs in EazyBank to FETCH Customer Details"
)
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/fetchCustomerDetails")
    @Operation(
            summary = "Fetch Customer Details REST APIs",
            description = "REST API to FETCH Customer, Loan, Card & Account details based on mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(@RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits") String mobileNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.fetchCustomerDetails(mobileNumber));
    }

}
