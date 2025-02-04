package com.vuongkma.motel.configuration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.vuongkma.motel.entities.User;
import com.vuongkma.motel.exceptions.AppException;
import com.vuongkma.motel.exceptions.ErrorCode;
import com.vuongkma.motel.repositories.UserRepository;
import com.vuongkma.motel.services.JwtService;
import com.vuongkma.motel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;
@Component
@RequiredArgsConstructor
@Slf4j(topic = "JWT-DECODER")
public class JwtDecoderCustomizer implements JwtDecoder {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final UserService userService;
    private final JwtService jwtService;
    private NimbusJwtDecoder nimbusJwtDecoder;

    @Override
    public Jwt decode(String token) throws JwtException {
        if(Objects.isNull(nimbusJwtDecoder)) {
            SecretKey key = new SecretKeySpec(secretKey.getBytes(), JWSAlgorithm.HS512.toString());
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(key)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        String email = jwtService.extractUserName(token);
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        try {
            boolean isValid = jwtService.verificationToken(token, user);
            if(isValid) {
                return nimbusJwtDecoder.decode(token);
            }
        } catch (ParseException | JOSEException e) {
            log.error("Jwt decoder: Token invalid");
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        throw new JwtException("Invalid token");
    }
}
