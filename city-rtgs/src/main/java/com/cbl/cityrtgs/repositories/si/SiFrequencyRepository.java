package com.cbl.cityrtgs.repositories.si;


import com.cbl.cityrtgs.models.entitymodels.si.SiFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface SiFrequencyRepository extends JpaRepository<SiFrequency, Long> {

    Optional<SiFrequency> findByFrequency(String frequency);
}
