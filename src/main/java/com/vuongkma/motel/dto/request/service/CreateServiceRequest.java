package com.vuongkma.motel.dto.request.service;

import com.vuongkma.motel.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateServiceRequest {
    private String content;
    private String icon;
    private String status;
    private String title;
    private String type;
    private Long user_id;
}
