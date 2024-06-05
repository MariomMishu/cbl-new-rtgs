package com.cbl.cityrtgs.services.authentication;

import com.cbl.cityrtgs.repositories.authentication.SecurityConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityConfigurationService {

    private final SecurityConfigurationRepository securityConfigurationRepository;

    //^(?=.*?[a-zA-Z$%&*_@]).{8,20}$


}
