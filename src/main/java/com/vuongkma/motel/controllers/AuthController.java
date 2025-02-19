package com.vuongkma.motel.controllers;


import com.vuongkma.motel.dto.request.LogoutRequest;
import com.vuongkma.motel.dto.request.SignInRequest;
import com.vuongkma.motel.dto.response.ResponseData;
import com.vuongkma.motel.dto.response.SignInResponse;
import com.vuongkma.motel.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j(topic = "Auth-Controller")
public class AuthController {
    private final AuthService authenticationService;
    @PostMapping("/sign-in")
    ResponseData<SignInResponse> signIn(@RequestBody @Valid SignInRequest request,
                                        HttpServletResponse response) {

        SignInResponse result = authenticationService.signIn(request, response);
        return ResponseData.<SignInResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Sign in success")
                .data(result)
                .build();
    }

//    @PostMapping("/refresh-token")
//    ResponseData<RefreshTokenResponse> refreshToken(@CookieValue(name = "refreshToken") String refreshToken) {
//        var result = authenticationService.refreshToken(refreshToken);
//        return ResponseData.<RefreshTokenResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("Refreshed token success")
//                .data(result)
//                .build();
//    }

    @PostMapping("/logout")
    ResponseData<Void> logout(@RequestBody @Valid LogoutRequest request, HttpServletResponse response) {
        authenticationService.signOut(request, response);
        return ResponseData.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Sign out success")
                .build();
    }

}
