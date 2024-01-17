package com.dcorp.hightech.acounts.controllers.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "accounts")
public class AccountContactDto {
    String message;
    Map<String, String> contactDetails;
    List<String> onCallSupport;
}
