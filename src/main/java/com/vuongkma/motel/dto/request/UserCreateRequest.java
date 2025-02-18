package com.vuongkma.motel.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String email;
    private String password;
    private String phone;
    private String username;
}
