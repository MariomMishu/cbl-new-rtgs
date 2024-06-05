package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.SettlementAccountStatementDetailEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.SettlementAccountStatementEntity;
import com.cbl.cityrtgs.repositories.transaction.SettlementAccountStatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SettlementAccountStatementService {

    private final SettlementAccountStatementRepository repository;
    private final SettlementAccountStatementDetailService statementDetailService;

    public void saveAll(List<SettlementAccountStatementEntity> statements) {

        for (SettlementAccountStatementEntity statement : statements) {
            this.save(statement);
        }

    }

    public void save(SettlementAccountStatementEntity statement) {
       repository.save(statement);
        if (statement.getDetails() != null) {

            for (SettlementAccountStatementDetailEntity detail : statement.getDetails()) {
                detail.setStatement(statement);
                this.statementDetailService.save(detail);
            }
        }

    }

}
