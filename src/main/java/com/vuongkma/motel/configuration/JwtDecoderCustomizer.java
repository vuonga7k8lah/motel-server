package com.vuongkma.motel.configuration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.vuongkma.motel.entities.User;
import com.vuongkma.motel.exceptions.AppException;
import com.vuongkma.motel.exceptions.ErrorCode;
import com.vuongkma.motel.services.JwtService;
import com.vuongkma.motel.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j(topic = "JWT-DECODER")
public class JwtDecoderCustomizer implements JwtDecoder {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private NimbusJwtDecoder nimbusJwtDecoder;

    @PostConstruct
    public void init() {
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), JWSAlgorithm.HS256.toString());
        this.nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        String email = jwtService.extractUserName(token);
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        try {
            boolean isValid = jwtService.verificationToken(token, user);
            if (!isValid) {
                throw new JwtException("Invalid token");
            }

            Jwt decodedJwt = nimbusJwtDecoder.decode(token);

            // Trích xuất quyền từ JWT
            Collection<GrantedAuthority> authorities = extractAuthorities(decodedJwt);
            log.info("User {} has roles: {}", email, authorities);

            return decodedJwt;

        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Object authorities = jwt.getClaim("Authority");

        if (authorities instanceof String) {
            return List.of(new SimpleGrantedAuthority((String) authorities));
        } else if (authorities instanceof Collection) {
            return ((Collection<String>) authorities)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
