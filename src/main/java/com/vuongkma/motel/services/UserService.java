package com.vuongkma.motel.services;

import com.vuongkma.motel.dto.request.UserCreateRequest;
import com.vuongkma.motel.dto.response.UserCreateResponse;
import com.vuongkma.motel.entities.User;
import com.vuongkma.motel.helpers.enums.RoleUser;
import com.vuongkma.motel.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j(topic = "USER-SERVICE" )
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserCreateResponse create(UserCreateRequest userCreateRequest){

        //check username and email exist
        if (userRepository.findByEmail(userCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        // Kiểm tra username đã tồn tại chưa
        if (userRepository.findByUsername(userCreateRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username đã tồn tại!");
        }

        var createData = User.builder()
                .username(userCreateRequest.getUsername())
                .role(RoleUser.user)
                .email(userCreateRequest.getEmail())
                .password(this.passwordEncoder.encode(userCreateRequest.getPassword()))
                .phoneNumber(userCreateRequest.getPhone())
                .build();


        User user = this.userRepository.save(createData);

        return UserCreateResponse.builder().build();
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

}
