package com.cbl.cityrtgs.services.authentication;


import com.cbl.cityrtgs.models.dto.authentication.ActiveSessionRequest;
import com.cbl.cityrtgs.models.dto.authentication.ActiveSessionResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.entitymodels.authentication.ActiveSession;
import com.cbl.cityrtgs.repositories.authentication.ActiveSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveSessionService {

    @Value("${app.access.token.expire.time}")
    private long EXPIRE_TIME;

    private final ActiveSessionRepository activeSessionRepository;

    public ResponseDTO getAllActiveSessions(int pageNo, int pageSize){

        Map<String, Object> response = new HashMap<>();
        List<ActiveSessionResponse> responses = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ActiveSession> pages = activeSessionRepository.findAllActiveSessions(pageable);

        if(!pages.isEmpty()){

            pages.forEach(activeSession -> responses.add(ActiveSessionResponse.toDTO(activeSession)));
        }

        response.put("result", responses);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " records found")
                .body(response)
                .build();
    }

    @Async
    public ResponseDTO save(ActiveSessionRequest request){

        try{

            activeSessionRepository.save(ActiveSessionRequest.toMODEL(request));

            return ResponseDTO.builder()
                    .error(false)
                    .message("New User Session saved!")
                    .build();
        }
        catch (Exception e){

            log.error("Active Session Save failed! {}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Async
    public void update(ActiveSession activeSession, Map<String, Object> map){

        try{

            LocalDateTime start = (LocalDateTime) map.get("start");
            LocalDateTime end = (LocalDateTime) map.get("end");
            String jwt = (String) map.get("jwt");

            activeSession.setToken(DigestUtils.sha256Hex(jwt));
            activeSession.setSessionEndTime(start);
            activeSession.setSessionEndTime(end);
            activeSession.setActive(true);
            activeSession.setDeleted(false);

            activeSessionRepository.save(activeSession);
        }
        catch (Exception e){

            log.error("{}", e.getMessage());
        }
    }

    public boolean isUserActive(Long userId){

        return activeSessionRepository.isUserSessionActive(userId) == 1;
    }

    public ActiveSession getActiveUser(Long userId){

        return activeSessionRepository.findActiveUser(userId);
    }

    public boolean isTokenValid(String accessToken){

        return activeSessionRepository.isSessionValid(accessToken) == 1;
    }

    public boolean isTokenExist(String accessToken){

        return activeSessionRepository.findByAccessToken(accessToken) != null;
    }

    @Async
    public void updateExpirationTime(String accessToken){

        try{

            ActiveSession activeSession = activeSessionRepository.findByAccessToken(accessToken);

            //if(ChronoUnit.MINUTES.between(activeSession.getSessionStartTime(), activeSession.getSessionEndTime()) >= 1){}

            LocalDateTime start = LocalDateTime.now();
            LocalDateTime end = start.plusMinutes(EXPIRE_TIME);
            activeSession.setSessionStartTime(start);
            activeSession.setSessionEndTime(end);
            activeSessionRepository.save(activeSession);
        }
        catch (Exception e){

            log.error("{}", e.getMessage());
        }
    }

    public ResponseDTO deleteSession(Long id){

        try{

            if(activeSessionRepository.findById(id).isPresent()){

                ActiveSession activeSession = activeSessionRepository.findById(id).get();
                activeSession.setActive(false);
                activeSession.setDeleted(true);
                activeSessionRepository.save(activeSession);
            }

            return ResponseDTO.builder()
                    .error(false)
                    .message("Session deleted")
                    .build();
        }
        catch (Exception e){

            log.error("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Async
    public void deleteActiveSession(Long id){

        try{

            if(activeSessionRepository.findById(id).isPresent()){

                ActiveSession activeSession = activeSessionRepository.findById(id).get();
                activeSession.setActive(false);
                activeSession.setDeleted(true);
                activeSessionRepository.save(activeSession);
            }
        }
        catch (Exception e){

            log.error("{}", e.getMessage());
        }
    }

    public ActiveSession findByAccessToken(String accessToken){

        return activeSessionRepository.findByAccessToken(accessToken);
    }
}
