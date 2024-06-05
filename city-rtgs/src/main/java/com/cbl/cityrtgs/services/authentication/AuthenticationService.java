package com.cbl.cityrtgs.services.authentication;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.authentication.ActiveSessionRequest;
import com.cbl.cityrtgs.models.dto.authentication.AuthRequest;
import com.cbl.cityrtgs.models.dto.authentication.AuthResponse;
import com.cbl.cityrtgs.models.dto.authentication.LogoutRequest;
import com.cbl.cityrtgs.models.dto.menu.response.GroupMenuResponse;
import com.cbl.cityrtgs.models.entitymodels.authentication.ActiveSession;
import com.cbl.cityrtgs.models.entitymodels.menu.GroupMenu;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.UserInfoRepository;
import com.cbl.cityrtgs.repositories.menu.GroupMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${app.access.token.expire.time}")
    private long EXPIRE_TIME;

    private final GroupMenuRepository groupMenuRepository;
    private final AuthenticationManager authenticationManager;
    private final ActiveSessionService activeSessionService;
    private final UserInfoRepository userInfoRepository;

    public APIResponse login(AuthRequest request) {

        if (!StringUtils.isEmpty(request.getUsername()) || !StringUtils.isEmpty(request.getPassword())) {
            Optional<UserInfoEntity> optional = userInfoRepository.findByUsername(request.getUsername());
            if (!optional.isEmpty()) {
                Long id = optional.get().getId();
                if (activeSessionService.isUserActive(id)) {
                    ActiveSession activeSession = activeSessionService.getActiveUser(id);
                    activeSessionService.deleteSession(activeSession.getId());
                }
                try {
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword().trim()));
                    String accessToken = UUID.randomUUID().toString().replaceAll("-", "");

                    LocalDateTime start = LocalDateTime.now();
                    LocalDateTime end = start.plusMinutes(EXPIRE_TIME);
                    Map<String, Object> map = Map.of("start", start, "end", end, "accessToken", accessToken);
                    return getAuthDetails(map, LoggedInUserDetails.getCurrentUser(authentication));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return APIResponse.builder()
                            .statusCode(400)
                            .status(HttpStatus.FORBIDDEN)
                            .message(e.getMessage())
                            .build();
                }
            } else {
                return APIResponse.builder()
                        .statusCode(403)
                        .status(HttpStatus.FORBIDDEN)
                        .message("Invalid Username")
                        .body(new ArrayList<>())
                        .build();
            }

        } else {
            return APIResponse.builder()
                    .statusCode(403)
                    .status(HttpStatus.FORBIDDEN)
                    .message("Please provide Username/Password")
                    .body(new ArrayList<>())
                    .build();

        }


    }

    private APIResponse getAuthDetails(Map<String, Object> map, UserInfoEntity user) {

        try {
            activeSessionService.save(ActiveSessionRequest.toDTO(user, map));

            Set<String> roles = new HashSet<>();
            Set<String> rights = new HashSet<>();

            user.getRoles().forEach(role -> {

                if (role != null) {

                    roles.add(role.getName());

                    role.getRights().forEach(right -> {

                        if (right != null) {

                            rights.add(right.getName());
                        }
                    });
                }
            });

            GroupMenuResponse menuAccess = null;

            Optional<GroupMenu> optional = groupMenuRepository.findGroupMenuByUserId(user.getId());

            if (optional.isPresent()) {
                menuAccess = GroupMenuResponse.toDTO(optional.get());
            }

            String accessToken = (String) map.get("accessToken");
            AuthResponse authResponse = AuthResponse.toDTO(user, accessToken, roles, rights, menuAccess);

            return APIResponse.builder()
                    .statusCode(200)
                    .status(HttpStatus.OK)
                    .message("Login Successful!")
                    .body(authResponse)
                    .build();
        } catch (Exception e) {

            log.error("{}", e.getMessage());

            return APIResponse.builder()
                    .statusCode(403)
                    .status(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .body(new ArrayList<>())
                    .build();
        }
    }

    public APIResponse logout(LogoutRequest request) {

        if (request.getAccessToken() == null) {

            return APIResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .statusCode(400)
                    .message("Please provide access token")
                    .build();
        }

        ActiveSession activeSession = activeSessionService.findByAccessToken(request.getAccessToken());
        activeSessionService.deleteActiveSession(activeSession.getId());

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Logged out successful")
                .build();
    }
}
