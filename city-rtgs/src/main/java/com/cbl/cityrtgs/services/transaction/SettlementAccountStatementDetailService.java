package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.SettlementAccountStatementDetailEntity;
import com.cbl.cityrtgs.repositories.transaction.SettlementAccountStatementDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class SettlementAccountStatementDetailService {
    private final SettlementAccountStatementDetailRepository repository;

    public void save(SettlementAccountStatementDetailEntity detail) {
        repository.save(detail);
    }

}
