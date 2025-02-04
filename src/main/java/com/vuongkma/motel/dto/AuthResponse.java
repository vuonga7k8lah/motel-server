package com.vuongkma.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
    private String access_token;
    private String refresh_token;
    private Integer refresh_expires_in;
    private Integer expires_in;
    private String token_type;

}
