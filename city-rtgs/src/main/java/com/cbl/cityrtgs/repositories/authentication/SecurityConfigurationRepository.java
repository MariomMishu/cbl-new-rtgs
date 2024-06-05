package com.cbl.cityrtgs.repositories.authentication;

import com.cbl.cityrtgs.models.entitymodels.authentication.SecurityConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface SecurityConfigurationRepository extends JpaRepository<SecurityConfiguration, Long> {
}
