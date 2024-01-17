package com.dcorp.hightech.acounts.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDTO {

    @Schema(
            description = "Status code in the response"
    )
    String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    String statusMessage;

}
