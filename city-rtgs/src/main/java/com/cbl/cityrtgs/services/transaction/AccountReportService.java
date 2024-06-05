package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.dto.reconcile.IReconcileDto;
import com.cbl.cityrtgs.models.dto.reconcile.SettlementAccountStatementSearchFilter;
import com.cbl.cityrtgs.repositories.transaction.AccountReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class AccountReportService {
private final AccountReportRepository accountReportRepository;

    public IReconcileDto getSettlementAccountReconcileMismatchReport(SettlementAccountStatementSearchFilter filter) {
        return accountReportRepository.getSettlementAccountReconcileMismatchReport(filter.getAccountNumber(),filter.getCurrencyCode());
    }

    public IReconcileDto getSettlementAccountReport(String accountNumber, String currency) {
        var reconciledList = accountReportRepository.getSettlementAccountReport(accountNumber,currency);
        if(!reconciledList.isEmpty()){
            return reconciledList.get(0);
        }
        return null;
    }

}
