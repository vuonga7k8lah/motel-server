package com.vuongkma.motel.controllers.admin;

import com.vuongkma.motel.services.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j(topic = "AdminRoomController:")
@RequestMapping("ap1/v1/admin/rooms")
@AllArgsConstructor
public class AdminRoomController {
    private final RoomService roomService;

}
