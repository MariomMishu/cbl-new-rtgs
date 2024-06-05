package com.cbl.cityrtgs.controllers.user;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.authentication.ActiveSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class ActiveSessionController {

    private final ActiveSessionService activeSessionService;

    @GetMapping
    public APIResponse getActiveSessions(@RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {

        ResponseDTO response = activeSessionService.getAllActiveSessions(page, size);

        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @DeleteMapping("/{id}")
    public APIResponse delete(@PathVariable("id") Long id) {

        ResponseDTO response = activeSessionService.deleteSession(id);

        if (response.isError()) {

            return APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }
}
