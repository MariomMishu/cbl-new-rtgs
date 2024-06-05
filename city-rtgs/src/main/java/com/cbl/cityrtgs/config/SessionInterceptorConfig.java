package com.cbl.cityrtgs.config;

import com.cbl.cityrtgs.config.authentication.SessionInterceptor;
import com.cbl.cityrtgs.services.authentication.ActiveSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class SessionInterceptorConfig implements WebMvcConfigurer {

    private final ActiveSessionService activeSessionService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new SessionInterceptor(activeSessionService))
                .addPathPatterns("/**");
    }
}
