package com.dcorp.hightech.acounts.controllers.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@Schema(
        name = "Error Response",
        description = "Schema to hold error response information"
)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

    @Schema(
            description = "API path invoked by client"
    )
    String apiPath;

    @Schema(
        description = "Error code representing the error happened"
    )
    HttpStatusCode errorCode;

    @Schema(
            description = "Error message representing the error happened"
    )
    String errorMessage;

    @Schema(
            description = "Time representing when the error happened"
    )
    LocalDateTime errorTime;

}
