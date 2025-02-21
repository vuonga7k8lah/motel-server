package com.vuongkma.motel.services;

import com.vuongkma.motel.dto.request.service.CreateServiceRequest;
import com.vuongkma.motel.dto.response.PageResponse;
import com.vuongkma.motel.dto.response.service.CreateServiceResponse;
import com.vuongkma.motel.dto.response.service.GetAllServiceResponse;
import com.vuongkma.motel.entities.Services;
import com.vuongkma.motel.entities.User;
import com.vuongkma.motel.helpers.enums.StatusService;
import com.vuongkma.motel.helpers.enums.TypeService;
import com.vuongkma.motel.mapper.admin.ServiceMapper;
import com.vuongkma.motel.repositories.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceRoomService {
    private final ServiceRepository serviceRepository;
    private final UserService userService;

    public CreateServiceResponse create(CreateServiceRequest createServiceRequest){
        Set<String> validTypes = Arrays.stream(TypeService.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        if (!validTypes.contains(createServiceRequest.getType())) {
            throw new RuntimeException("Invalid type: " + createServiceRequest.getType());
        }
        TypeService typeService = TypeService.valueOf(createServiceRequest.getType());
        Set<String> status = new HashSet<>();
        status.add("publish");
        status.add("draft");

        if (!status.contains(createServiceRequest.getStatus())) {
            throw new RuntimeException("Invalid status: " + createServiceRequest.getType());
        }
        User user = userService.findById(createServiceRequest.getUser_id()).orElseThrow();

        StatusService statusService = StatusService.valueOf(createServiceRequest.getStatus());

        Services services = this.serviceRepository.save(Services.builder()
                        .user(user)
                        .content(createServiceRequest.getContent())
                        .type(typeService)
                        .title(createServiceRequest.getTitle())
                        .icon(createServiceRequest.getIcon())
                        .status(statusService)
                .build());


        return CreateServiceResponse.builder()
                .id(services.getId())
                .content(services.getContent())
                .title(services.getTitle())
                .user_id(services.getUser().getId())
                .icon(services.getIcon())
                .status(services.getStatus().name())
                .build();
    }

    public PageResponse<GetAllServiceResponse> getAll(Integer page, Integer limit, String search){
        Pageable pageable = PageRequest.of(page - 1, limit);
        var data = serviceRepository.findAll(pageable);
        return PageResponse.<GetAllServiceResponse>builder()
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .currentPage(page)
                .pageSize(pageable.getPageSize())
                .data(ServiceMapper.mapper(data.getContent()))
                .build();
    }
}
