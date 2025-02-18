package com.vuongkma.motel.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vuongkma.motel.helpers.enums.RoleUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserCreateResponse {
    private Number id;
    private String email;
    private String username;
    private String phone;
    private RoleUser role;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate create_at;
}
