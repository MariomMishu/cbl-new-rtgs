package com.cbl.cityrtgs.repositories.businessday;

import com.cbl.cityrtgs.models.entitymodels.businessday.GetBusinessDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GetBusinessDayRepository extends JpaRepository<GetBusinessDayEntity, Long> {

}
