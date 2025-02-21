package com.vuongkma.motel.mapper.admin;

import com.vuongkma.motel.dto.response.UserResponse;
import com.vuongkma.motel.dto.response.service.GetAllServiceResponse;
import com.vuongkma.motel.entities.Services;

import java.util.List;

public class ServiceMapper {

    public static List<GetAllServiceResponse> mapper(List<Services> services){
        return services.stream().map(service->GetAllServiceResponse.builder()
                .id(service.getId())
                .icon(service.getIcon())
                .status(service.getStatus().name())
                .title(service.getTitle())
                .content(service.getContent())
                .created_at(service.getCreated_at())
                .user(UserResponse.builder()
                        .email(service.getUser().getEmail())
                        .role(service.getUser().getRole().name())
                        .username(service.getUser().getUsername())
                        .id(service.getUser().getId())
                        .build())
                .build()).toList();
    }
}
