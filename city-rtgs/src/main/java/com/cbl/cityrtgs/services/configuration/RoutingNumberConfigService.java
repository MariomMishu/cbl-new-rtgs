package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.RoutingNumberConfigurationEntity;
import com.cbl.cityrtgs.repositories.configuration.RoutingNumberConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoutingNumberConfigService {

    private final RoutingNumberConfigurationRepository repository;

    public RoutingNumberConfigurationEntity getRoutingNumberSetup() {
        List<RoutingNumberConfigurationEntity> setups = repository.findByIsDeletedFalse();
        return setups.size() > 0 ? setups.get(0) : null;
    }

}
