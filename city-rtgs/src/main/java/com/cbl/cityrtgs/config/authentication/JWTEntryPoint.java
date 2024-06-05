package com.cbl.cityrtgs.config.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)  {

        try {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
