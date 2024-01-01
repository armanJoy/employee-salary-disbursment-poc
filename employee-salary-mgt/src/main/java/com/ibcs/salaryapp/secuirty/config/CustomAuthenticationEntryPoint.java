package com.ibcs.salaryapp.secuirty.config;

import com.ibcs.salaryapp.secuirty.exception.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

//     @Override
//     public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
//            throws IOException {
//        logger.error(e.getLocalizedMessage(), e);
//
//        String message = RestResponse.builder()
//                .status(UNAUTHORIZED)
//                .error("Unauthenticated")
//                .message("Insufficient authentication details")
//                .path(request.getRequestURI())
//                .json();
//
//        response.setStatus(UNAUTHORIZED.value());
//        response.setContentType(APPLICATION_JSON_VALUE);
//        response.getWriter().write(message);
//    }
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.AuthenticationException e) throws IOException, ServletException {

        logger.error(e.getLocalizedMessage() + " >> " + httpServletRequest.getRequestURI());

        String message = RestResponse.builder()
                .status(UNAUTHORIZED)
                .error("Unauthenticated")
                .message("Insufficient authentication details")
                .path(httpServletRequest.getRequestURI())
                .json();

        httpServletResponse.setStatus(UNAUTHORIZED.value());
        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
        httpServletResponse.getWriter().write(message);
    }

}
