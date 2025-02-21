package com.vuongkma.motel.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    public String username;
    public String email;
    public Number id;
    public String role;
}
