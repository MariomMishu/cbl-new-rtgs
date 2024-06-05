package com.cbl.cityrtgs.repositories.si;

import com.cbl.cityrtgs.models.entitymodels.si.SiConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SiConfigurationRepository extends JpaRepository<SiConfiguration, Long> {

}
