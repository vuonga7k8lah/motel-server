package com.vuongkma.motel.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongkma.motel.dto.response.ErrorResponse;
import com.vuongkma.motel.exceptions.ErrorCode;
import com.vuongkma.motel.helpers.ResponseFormat;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Date;

public class JwtAccessDined implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);




        ResponseFormat.build(
                ErrorCode.ACCESS_DINED.toString()
        );
    }
}
