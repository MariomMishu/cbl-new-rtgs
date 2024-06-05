package com.cbl.cityrtgs.config.authentication;

import com.cbl.cityrtgs.models.entitymodels.authentication.ActiveSession;
import com.cbl.cityrtgs.services.authentication.ActiveSessionService;
import com.cbl.cityrtgs.services.user.UserService;
import com.cbl.cityrtgs.common.utility.SessionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final ActiveSessionService activeSessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  {

        String accessToken = getAccessTokenFromRequest(request);

        if((accessToken != null) && !accessToken.equals("")){

            if(activeSessionService.isTokenValid(accessToken)){

                ActiveSession activeSession = activeSessionService.findByAccessToken(accessToken);
                UserDetails userDetails = userService.loadUserByUsername(activeSession.getUser().getUsername());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else{
                SessionUtility.unauthorizedAccessReply(response);
                throw new RuntimeException("401! Access Denied.");
            }
        }

        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

            String accessToken = bearerToken.substring(7);

            if(StringUtils.hasText(accessToken) && !accessToken.equalsIgnoreCase("undefined")){

                return accessToken;
            }
        }

        return null;
    }
}
