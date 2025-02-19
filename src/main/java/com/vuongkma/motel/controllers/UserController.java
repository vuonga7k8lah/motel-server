package com.vuongkma.motel.controllers;

import com.vuongkma.motel.dto.request.UserCreateRequest;
import com.vuongkma.motel.dto.response.ResponseData;
import com.vuongkma.motel.dto.response.UserCreateResponse;
import com.vuongkma.motel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@Slf4j(topic = "UserController")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("creation")
    ResponseData<UserCreateResponse> create(@RequestBody UserCreateRequest userCreateRequest) {

        UserCreateResponse data = userService.create(userCreateRequest);
        return ResponseData.<UserCreateResponse>builder().data(data).message("Save user success").code(201).build();
    }



}
