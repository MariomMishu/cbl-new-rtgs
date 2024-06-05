package com.cbl.cityrtgs.repositories.businessday;

import com.cbl.cityrtgs.models.entitymodels.businessday.BusinessDayEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessDayEventRepository extends JpaRepository<BusinessDayEventEntity, Long> {
    List<BusinessDayEventEntity> findAllByBizDayInf_Id(Long bizDayInf);
}
