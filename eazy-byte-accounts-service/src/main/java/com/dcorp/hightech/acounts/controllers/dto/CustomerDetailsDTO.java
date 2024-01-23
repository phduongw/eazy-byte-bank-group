package com.dcorp.hightech.acounts.controllers.dto;

import com.dcorp.hightech.acounts.service.clients.dto.CardsDto;
import com.dcorp.hightech.acounts.service.clients.dto.LoansDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards, Loan information"
)
public class CustomerDetailsDTO extends CustomerDTO{

    @Schema(
            description = "Card details of the Customer"
    )
    CardsDto cardsDto;

    @Schema(
            description = "Loan details of the Customer"
    )
    LoansDto loansDto;

}
