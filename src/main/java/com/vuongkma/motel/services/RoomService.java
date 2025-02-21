package com.vuongkma.motel.services;

import com.vuongkma.motel.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
}
