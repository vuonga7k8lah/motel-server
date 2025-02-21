package com.vuongkma.motel.controllers.admin;

import com.vuongkma.motel.dto.request.service.CreateServiceRequest;
import com.vuongkma.motel.dto.response.PageResponse;
import com.vuongkma.motel.dto.response.ResponseData;
import com.vuongkma.motel.dto.response.service.CreateServiceResponse;
import com.vuongkma.motel.dto.response.service.GetAllServiceResponse;
import com.vuongkma.motel.helpers.GetUserIdFromJWT;
import com.vuongkma.motel.services.ServiceRoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/services")
@AllArgsConstructor
@Slf4j(topic = "AdminServiceController")
public class AdminServiceController {
    private final ServiceRoomService serviceRoomService;

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseData<CreateServiceResponse> create(@RequestBody CreateServiceRequest createServiceRequest, Authentication authentication) {
        Long userId = GetUserIdFromJWT.getUserId(authentication);
        createServiceRequest.setUser_id(userId);
        CreateServiceResponse data = serviceRoomService.create(createServiceRequest);
        return ResponseData.<CreateServiceResponse>builder().data(data).code(201).message("Create service success").build();
    }
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseData<PageResponse<GetAllServiceResponse>> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "s", required = false, defaultValue = "") String search,
            Authentication authentication) {
        Long userId = GetUserIdFromJWT.getUserId(authentication);

        PageResponse<GetAllServiceResponse> data = serviceRoomService.getAll(page,size,search);
        return ResponseData.<PageResponse<GetAllServiceResponse>>builder().data(data).code(201).message("Get all service success").build();
    }

}
