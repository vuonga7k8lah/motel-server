package com.vuongkma.motel.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.vuongkma.motel.dto.request.LogoutRequest;
import com.vuongkma.motel.dto.request.SignInRequest;
import com.vuongkma.motel.dto.response.SignInResponse;
import com.vuongkma.motel.entities.User;
import com.vuongkma.motel.exceptions.AppException;
import com.vuongkma.motel.exceptions.ErrorCode;
import com.vuongkma.motel.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


//    @Transactional(rollbackFor = Exception.class)
    public SignInResponse signIn(SignInRequest request, HttpServletResponse response) {
        log.info("Authentication start ...!");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = (User) authentication.getPrincipal();


        final String accessToken = jwtService.generateAccessToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);


        userRepository.save(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true nếu chỉ cho gửi qua HTTPS
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 24 * 60 * 60); // 2 tuần

        response.addCookie(cookie);

        return SignInResponse.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .id(user.getId())
                .role(user.getRole().toString())
                .build();
    }

//    public ResponseEntity<Object> refreshToken(String refreshToken) {
//        log.info("refresh token");
//        if (StringUtils.isBlank(refreshToken)) {
//            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
//        }
//        String email = jwtService.extractUserName(refreshToken);
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//        if (!Objects.equals(refreshToken, user.getRefreshToken()) || StringUtils.isBlank(user.getRefreshToken()))
//            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
//
//        try {
//            boolean isValidToken = jwtService.verificationToken(refreshToken, user);
//            if (!isValidToken) {
//                throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
//            }
//            String accessToken = jwtService.generateAccessToken(user);
//            log.info("refresh token success");
//            return RefreshTokenResponse.builder()
//                    .accessToken(accessToken)
//                    .userId(user.getId())
//                    .build();
//        } catch (ParseException | JOSEException e) {
//            log.error("Error while refresh token");
//            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
//        }
//    }

    public void signOut(LogoutRequest request, HttpServletResponse response) {
        String email = jwtService.extractUserName(request.getAccessToken());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        long accessTokenExp = jwtService.extractTokenExpired(request.getAccessToken());
        if (accessTokenExp > 0) {
            try {
                String jwtId = SignedJWT.parse(request.getAccessToken()).getJWTClaimsSet().getJWTID();
                //redisService.save(jwtId, request.getAccessToken(), accessTokenExp, TimeUnit.MILLISECONDS);
                user.setRefreshToken(null);
                userRepository.save(user);
                deleteRefreshTokenCookie(response);
            } catch (ParseException e) {
                throw new AppException(ErrorCode.SIGN_OUT_FAILED);
            } catch (java.text.ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
