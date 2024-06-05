package com.cbl.cityrtgs.repositories.configuration;

import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
    Boolean existsByShortCode(String shortCode);

   // CurrencyEntity findByShortCodeAndIsDeletedFalse(String shortCode);
    Optional<CurrencyEntity> findByShortCodeAndIsDeletedFalse(String shortCode);

    Optional<CurrencyEntity> findByIdAndIsDeletedFalse(Long id);

    List<CurrencyEntity> findAllByIsDeletedFalse();
    Page<CurrencyEntity> findAllByIsDeletedFalse(Pageable pageable);
}
