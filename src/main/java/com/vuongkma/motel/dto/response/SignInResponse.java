package com.vuongkma.motel.dto.response;

import com.vuongkma.motel.entities.User;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class SignInResponse implements Serializable {
    public String access_token;
    public String refresh_token;
    public String username;
    public String email;
    public Number id;
    public String role;
}
