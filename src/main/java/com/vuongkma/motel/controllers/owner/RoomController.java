package com.vuongkma.motel.controllers.owner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/owner/rooms")
@Slf4j(topic = "OWNER-RoomController")
public class RoomController {
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public void testRole(Authentication authentication) {
        log.info("Current Authentication: {}", authentication);
        log.info("Authorities: {}", authentication.getAuthorities());
        System.out.println("role ");
    }
}
