package com.cbl.cityrtgs.mapper.transaction;

import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferDetails;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferRequest;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferResponse;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.services.configuration.BankService;
import com.cbl.cityrtgs.services.configuration.BranchService;
import com.cbl.cityrtgs.services.configuration.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Objects;
import static com.cbl.cityrtgs.common.utility.ValidationUtility.validateText;

@Component
@RequiredArgsConstructor
public class BankFundTransferMapper {
    private final CurrencyService currencyService;
    private final BankService bankService;
    private final BranchService branchService;

    public BankFundTransferResponse entityToDomain(InterBankTransferEntity interB2B, BankFndTransferEntity b2bTxn) {
        CurrencyResponse currency = currencyService.getById(b2bTxn.getCurrencyId());
        BankFundTransferResponse response = new BankFundTransferResponse();
        BankResponse benBank = bankService.getBankById(b2bTxn.getBenBankId());
        BranchResponse benBranch = branchService.getBranchById(b2bTxn.getBenBranchId());
        BankResponse payerBank = bankService.getBankById(b2bTxn.getPayerBankId());
        BranchResponse payerBranch = branchService.getBranchById(b2bTxn.getPayerBranchId());
        response
                .setEntryUser(!Strings.isBlank(interB2B.getEntryUser()) ? interB2B.getEntryUser() : "SYSTEM")
                .setCreatedAt(interB2B.getCreatedAt())
                .setTxnTypeCode(interB2B.getTxnTypeCode())
                .setEventId(interB2B.getEventId())
                .setEventName("Bank To Bank Fund Transfer")
                .setParentBatchNumber(interB2B.getBatchNumber())
                .setId(interB2B.getId())
                .setAmount(b2bTxn.getAmount())
                .setPriorityCode(b2bTxn.getPriorityCode())
                .setOutgoingGlAccount(b2bTxn.getTransactionGlAccount())
                .setRtgsSettlementAccount(b2bTxn.getPayerAccNo())
                .setOutgoingGlAccount(b2bTxn.getPayerCbsAccNo())
                .setReferenceNumber(b2bTxn.getReferenceNumber())
                .setNarration(b2bTxn.getNarration())
                .setFcRecBranchId(b2bTxn.getFcRecBranchId())
                .setFcRecBranchName(Objects.nonNull(b2bTxn.getFcRecBranchId()) ? branchService.getBranchById(b2bTxn.getFcRecBranchId()).getName() : null)
                .setStatus(b2bTxn.getTransactionStatus().toString())
                .setCreatedAt(b2bTxn.getCreatedAt())
                .setCurrencyId(b2bTxn.getCurrencyId())
                .setCurrency(currency.getShortCode())
                .setBillNumber(b2bTxn.getBillNumber())
                .setLcNumber(b2bTxn.getLcNumber())
                .setBenBankId(b2bTxn.getBenBankId())
                .setBenBankName(benBank.getName())
                .setBenBankBic(benBank.getBic())
                .setBenBranchId(b2bTxn.getBenBranchId())
                .setBenBranchRoutingNo(benBranch.getRoutingNumber())
                .setBenBranchName(benBranch.getName())
                .setPayerBankId(b2bTxn.getPayerBankId())
                .setPayerBankName(payerBank.getName())
                .setPayerBranchId(b2bTxn.getPayerBranchId())
                .setPayerBranchName(payerBranch.getName())
                .setReturnCode(b2bTxn.getReturnCode())
                .setReturnReason(b2bTxn.getReturnReason())
                .setReturnDateTime(b2bTxn.getReturnDateTime())
                .setInwardActionStatus(interB2B.getInwardActionStatus())
                .setSettlementDate(b2bTxn.getSettlementDate())
                .setTransactionDate(b2bTxn.getTransactionDate())
                .setTransactions(interB2B.getId())
                .setRoutingType(b2bTxn.getRoutingType())
                .setPartyName(b2bTxn.getFcRecAccountType())
                .setFundTransferType(FundTransferType.BankToBank.toString());
        return response;
    }

    public InterBankTransferEntity domainToEntity(BankFundTransferRequest domain) {
        InterBankTransferEntity entity = new InterBankTransferEntity();
        entity
                .setCreateTime(new Date())
                .setEventId("OPACS009")
                .setRoutingType(RoutingType.Outgoing)
                .setSettlementDate(new Date())
                .setType("1")
                .setTxnTypeCode(domain.getTxnTypeCode());
        entity.setCreatedAt(new Date());
        return entity;
    }

    public BankFndTransferEntity domainToBankFndEntity(BankFundTransferDetails domain) {
        BankFndTransferEntity entity = new BankFndTransferEntity();
        entity
                .setAmount(domain.getAmount())
                .setNarration(validateText(domain.getNarration(), 35))
                .setBatchTxn(domain.isBatchTxn())
                .setBenBankId(domain.getBenBankId())
                .setBenBranchId(domain.getBenBranchId())
                .setBillNumber(validateText(domain.getBillNumber(), 35))
                .setRoutingType(RoutingType.Outgoing)
                .setPayerAccNo(domain.getRtgsSettlementAccount())
                .setPayerCbsAccNo(domain.getOutgoingGlAccount())
                .setBatchTransactionChargeWaived(false);
        return entity;
    }

}
