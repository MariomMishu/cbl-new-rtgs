package com.cbl.cityrtgs.repositories.transaction;


import com.cbl.cityrtgs.models.entitymodels.transaction.SettlementAccountStatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SettlementAccountStatementRepository extends JpaRepository<SettlementAccountStatementEntity, Long> {

}
