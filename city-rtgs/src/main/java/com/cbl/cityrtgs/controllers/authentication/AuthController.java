package com.cbl.cityrtgs.controllers.authentication;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.authentication.AuthRequest;
import com.cbl.cityrtgs.models.dto.authentication.LogoutRequest;
import com.cbl.cityrtgs.services.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public APIResponse login(@RequestBody @Valid AuthRequest request) {

        log.info("Into Login...");

        return authenticationService.login(request);
    }

    @DeleteMapping("/logout")
    public APIResponse logout(@RequestBody LogoutRequest request) {

        return authenticationService.logout(request);
    }
}
