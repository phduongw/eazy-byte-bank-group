package com.dcorp.hightech.acounts.controllers;

import com.dcorp.hightech.acounts.constants.AccountConstants;
import com.dcorp.hightech.acounts.controllers.dto.AccountContactDto;
import com.dcorp.hightech.acounts.controllers.dto.CustomerDTO;
import com.dcorp.hightech.acounts.controllers.dto.ResponseDTO;
import com.dcorp.hightech.acounts.controllers.response.ErrorResponse;
import com.dcorp.hightech.acounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "CRUD REST APIs for Accounts in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH and DELETE Account Details"
)
public class AccountsController {

    private final AccountsService accountsService;

//    @Value("${build.version}")
//    private String buildVersion;

    private final Environment environment;

    private final AccountContactDto contactDto;

    @PostMapping("/create")
    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO request) {
        accountsService.createAccount(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    @Operation(
            summary = "Fetch Account REST API",
            description = "REST API to FETCH Customer & Account details based on a Mobile Number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    public ResponseEntity<CustomerDTO> fetchAccountDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits") String mobileNumber
    ) {
        CustomerDTO customer = accountsService.fetchAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PutMapping("/update")
    @Operation(
            summary = "UPDATE Account REST API",
            description = "REST API to update Customer & Account details based on a Mobile Number"
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
    @ApiResponse(
            responseCode = "417",
            description = "Exception Failed"
    )
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO request) {
        boolean isUpdated = accountsService.updateAccount(request);

        return responseOfModifyingObject(isUpdated, AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE);
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "DELETE Account REST API",
            description = "REST API to delete Customer & Account details based on a Mobile Number"
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
    @ApiResponse(
            responseCode = "417",
            description = "Exception Failed"
    )
    public ResponseEntity<ResponseDTO> deleteAccountDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits") String mobileNumber
    ) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        return responseOfModifyingObject(isDeleted, AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE);
    }

    private ResponseEntity<ResponseDTO> responseOfModifyingObject(boolean isUpdated, String statusCode, String message) {
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }

        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDTO(statusCode, message));
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("build.version"));
    }

    @GetMapping("/contact-details")
    public ResponseEntity<AccountContactDto> getContactDetails() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactDto);
    }
}
