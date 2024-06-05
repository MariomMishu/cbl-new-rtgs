package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.AccountTransactionRegisterEntity;
import com.cbl.cityrtgs.repositories.transaction.AccountTransactionRegisterRepository;
import com.cbl.cityrtgs.services.configuration.AccountTypeService;
import com.cbl.cityrtgs.services.configuration.SettlementAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountTransactionRegisterService {

    private final AccountTransactionRegisterRepository registerRepository;
    private final SettlementAccountService settlementAccountService;
    private final AccountTypeService accountTypeService;
    public AccountTransactionRegisterEntity doRegister(RoutingType routingType,
                                                       Long currencyId,
                                                       BigDecimal amount,
                                                       String voucherNumber,
                                                       Long debitor,
                                                       Long creditor,
                                                       Long debitorBranch,
                                                       Long creditorBranch,
                                                       Date txnDate,
                                                       Date settlementDate,
                                                       String refNo,
                                                       String rtgsCounterAccount,
                                                       String narration,
                                                       String cbsName) {

        AccountTransactionRegisterEntity reg = new AccountTransactionRegisterEntity();
        SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(currencyId);
        AccountTypeEntity accountTypeEntity = accountTypeService.getAccountByRtgsAccountIdAndCbsName(settlementAccount.getId(), CbsName.getType(cbsName));

        if (!registerRepository.existsByTransactionReferenceNumberAndRoutingType(refNo, routingType)) {

            if (routingType.equals(RoutingType.Outgoing)) {
                reg.setCreditAmountCcy(amount);
            } else {
                reg.setDebitAmountCcy(amount);
            }
            reg.setRoutingType(routingType);
            reg.setAccountTypeId(accountTypeEntity.getId());
            reg.setAccountId(settlementAccount.getId());
            reg.setAccountNo(settlementAccount.getCode());
            reg.setDebitorId(debitor);
            reg.setCreditorId(creditor);
            reg.setDebitorBranchId(debitorBranch);
            reg.setCreditorBranchId(creditorBranch);
            reg.setVoucherNumber(voucherNumber);
            reg.setSettlementDate(settlementDate);
            reg.setTransactionDate(txnDate);
            reg.setTransactionReferenceNumber(refNo);
            reg.setCounterAccountNo(rtgsCounterAccount);
            reg.setNarration(narration);
            reg.setValid(true);
            reg.setCreatedAt(new Date());
            reg = registerRepository.save(reg);
            log.info("Transaction Registered " + refNo);
        }
        return reg;
    }
}
