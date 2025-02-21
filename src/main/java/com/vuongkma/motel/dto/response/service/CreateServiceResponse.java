package com.vuongkma.motel.dto.response.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CreateServiceResponse {
    private Long id;
    private String content;
    private String icon;
    private String status;
    private String title;
    private Long user_id;
    private Date created_at;
}
