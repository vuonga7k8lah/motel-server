package com.vuongkma.motel.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vuongkma.motel.entities.User;
import com.vuongkma.motel.exceptions.AppException;
import com.vuongkma.motel.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j(topic = "JWT-SERVICE")
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;


    public String generateAccessToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet claimsSet =  new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("identity-service")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(43200, ChronoUnit.MINUTES).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("Authority", List.of(buildAuthority(user)))
                .claim("user_id", user.getId())
                .claim("Permission", buildPermissions(user))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }


    public String generateRefreshToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        var claimsSet =  new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("identity-service")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(60, ChronoUnit.DAYS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .build();

        var payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }


    public String extractUserName(String accessToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(accessToken);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
    }


    public boolean verificationToken(String token, User user) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        var jwtId = signedJWT.getJWTClaimsSet().getJWTID();
//        if(StringUtils.isNotBlank(redisService.get(jwtId))) {
//            throw new AppException(ErrorCode.TOKEN_BLACK_LIST);
//        }
        var email = signedJWT.getJWTClaimsSet().getSubject();
        var expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        if( !Objects.equals(email, user.getEmail())) {
            log.error("Email in token not match email system");
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        if(expiration.before(new Date())) {
            log.error("Token expired");
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }

        return signedJWT.verify(new MACVerifier(secretKey));
    }


    public String buildAuthority(User user) {
        return "ROLE_" + user.getRole().name().toUpperCase();
    }


    public String buildPermissions(User user) {
        StringJoiner joiner = new StringJoiner(", ");

        return null;
    }

    public long extractTokenExpired(String token) {
        try {
            long expirationTime = SignedJWT.parse(token)
                    .getJWTClaimsSet().getExpirationTime().getTime();
            long currentTime = System.currentTimeMillis();
            return Math.max(expirationTime - currentTime, 0);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
    }
}
