package com.vuongkma.motel.dto.response.service;

import com.vuongkma.motel.dto.response.UserResponse;
import com.vuongkma.motel.entities.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class GetAllServiceResponse {
    private Long id;
    private String content;
    private String icon;
    private String status;
    private String title;
    private UserResponse user;
    private Date created_at;
}
