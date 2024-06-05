package com.cbl.cityrtgs.repositories.transaction.b2b;

import com.cbl.cityrtgs.models.dto.report.TxnAuditReport;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferAudEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BankFndTransferAudRepository extends JpaRepository<BankFndTransferAudEntity, Long> {
    Page<BankFndTransferAudEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<BankFndTransferAudEntity> findByIdAndIsDeletedFalse(Long id);
    List<BankFndTransferAudEntity> findAllByIsDeletedFalse();

    @Query(value = "SELECT TXN.TRANSACTIONDATE AS txnDate, TXN.PAYER_ACC_NO AS payerAccNo, TXN.PAYER_NAME AS payerName, BNK.NAME AS payerBank, TXN.BEN_ACC_NO AS beneficiaryAccNo, TXN.BEN_NAME AS beneficiaryName, BNKTWO.NAME AS beneficiaryBank, " +
            "TXN.AMOUNT AS amount, TXN.REFERENCENUMBER AS reference, TXN.NARRATION AS narration, TXN.LCNUMBER AS lcNumber, TXN.ROUTINGTYPE AS routingType, " +
            "BRN.NAME AS payerBranch, BRNTWO.NAME AS beneficiaryBranch, TXN.VOUCHERNUMBER AS voucher, TXN.TRANSACTIONSTATUS AS txnStatus, " +
            "PTXN.ENTRYUSER AS entryUser, PTXN.BRANCHID AS branchId, " +
            "PTXN.APPROVER AS approver, PTXN.APPROVALDATETIME AS approvalDateTime, PTXN.VERIFIER AS verifier, PTXN.VERIFYDATETIME AS verifyDateTime " +
            "FROM TBL_BANKFNDTRANSFERTXN_AUD TXN " +
            "LEFT JOIN RTGS_BANKS BNK ON TXN.PAYER_BANK_ID = BNK.ID " +
            "LEFT JOIN RTGS_BANKS BNKTWO ON TXN.BEN_BANK_ID = BNKTWO.ID " +
            "LEFT JOIN RTGS_BRANCHS BRN ON TXN.PAYER_BRANCH_ID = BRN.ID " +
            "LEFT JOIN RTGS_BRANCHS BRNTWO ON TXN.BEN_BRANCH_ID = BRNTWO.ID " +
            "LEFT JOIN TBL_INTERBANKFUNDTRANSFER_AUD PTXN ON TXN.PARENTBATCHNUMBER = PTXN.BATCHNUMBER " +
            "LEFT JOIN TBL_USERINFO USR ON PTXN.ENTRYUSER = USR.USERNAME " +
            "WHERE TXN.REFERENCENUMBER like :reference " +
            "ORDER BY TXN.TRANSACTIONSTATUS DESC ", nativeQuery = true)
    List<TxnAuditReport> getB2BTxnAuditListByReference(String reference);
}
