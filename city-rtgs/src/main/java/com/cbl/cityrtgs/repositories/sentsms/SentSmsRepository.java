package com.cbl.cityrtgs.repositories.sentsms;

import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.sentsms.SentSmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SentSmsRepository extends JpaRepository<SentSmsEntity, Long>, JpaSpecificationExecutor<MsgLogEntity> {

    //  Optional<SentSmsEntity> findByIdAndIsDeletedFalse(Long id);

}
