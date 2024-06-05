package com.cbl.cityrtgs.config.authentication;

import com.cbl.cityrtgs.services.authentication.ActiveSessionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@AllArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    private final ActiveSessionService activeSessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

            String accessToken = bearerToken.split(" ")[1];

            if(activeSessionService.isTokenValid(accessToken)){

                if(!request.getRequestURL().toString().contains("logout")){

                    activeSessionService.updateExpirationTime(accessToken);
                }

                return true;
            }

        }

        return true;
    }
}
