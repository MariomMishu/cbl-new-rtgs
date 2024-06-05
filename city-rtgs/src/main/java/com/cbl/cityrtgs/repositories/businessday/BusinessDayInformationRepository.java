package com.cbl.cityrtgs.repositories.businessday;

import com.cbl.cityrtgs.models.entitymodels.businessday.BusinessDayInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessDayInformationRepository extends JpaRepository<BusinessDayInformationEntity, Long> {
    Optional<BusinessDayInformationEntity> findByOriginalMsgId(String originalMsgId);

    Optional<BusinessDayInformationEntity> findByOrderByIdDesc();

    @Query(value = "SELECT * FROM TBL_BIZ_INF " +
            "WHERE TO_CHAR(CREATEDATE, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID DESC ", nativeQuery = true)
    BusinessDayInformationEntity getByTodayDesc();
}
