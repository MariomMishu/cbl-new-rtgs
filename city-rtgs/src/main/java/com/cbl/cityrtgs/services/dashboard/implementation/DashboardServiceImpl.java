package com.cbl.cityrtgs.services.dashboard.implementation;

import com.cbl.cityrtgs.models.dto.dashboard.*;
import com.cbl.cityrtgs.models.dto.projection.ApprovalEventLogProjection;
import com.cbl.cityrtgs.models.dto.projection.BalanceProjection;
import com.cbl.cityrtgs.models.dto.projection.CashLiquidityProjection;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.transaction.b2b.B2BProjection;
import com.cbl.cityrtgs.models.dto.transaction.b2b.C2CProjection;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.repositories.configuration.BankRepository;
import com.cbl.cityrtgs.repositories.configuration.BranchRepository;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;
import com.cbl.cityrtgs.repositories.transaction.AccountTransactionRegisterRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.services.dashboard.abstraction.DashboardService;
import com.cbl.cityrtgs.common.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

    private final BranchRepository branchRepository;
    private final BankRepository bankRepository;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final BankFndTransferRepository bankFndTransferRepository;
    private final AccountTransactionRegisterRepository accountTransactionRegisterRepository;
    private final MsgLogRepository msgLogRepository;

    @Value("${app.core.stpg.ip}")
    private String stpgIpAddress;

    @Value("${app.core.stpg.port}")
    private int stpgPort;


    @Override
    public ResponseDTO getSummary(String dateString) {

        LocalDate date = getLocalDate(dateString);

        try {

            long b2bPendingIncoming = bankFndTransferRepository.countAllb2bPendingIncomingTransactions(date);
            long b2bPendingOutgoing = bankFndTransferRepository.countAllb2bPendingOutgoingTransactions(date);
            long c2cPendingIncoming = customerFndTransferRepository.countAllc2cPendingIncomingTransactions(date);
            long c2cPendingOutgoing = customerFndTransferRepository.countAllc2cPendingOutgoingTransactions(date);
            long submitted = bankFndTransferRepository.countAllb2bSubmittedOutgoingTransactions(date) + customerFndTransferRepository.countAllc2cSubmittedOutgoingTransactions(date);
            long confirmed = bankFndTransferRepository.countAllConfirmedTransactions(date) + customerFndTransferRepository.countAllConfirmedTransactions(date);

            long unprocessed = msgLogRepository.countUnprocessed(date);
            long failed = bankFndTransferRepository.countAllFailedTransactions(date) + customerFndTransferRepository.countAllFailedTransactions(date);
            long reversed = bankFndTransferRepository.countAllReversedTransactions(date) + customerFndTransferRepository.countAllReversedTransactions(date);
            long rejected = bankFndTransferRepository.countAllRejectedTransactions(date) + customerFndTransferRepository.countAllRejectedTransactions(date);
            SummaryResponse response = SummaryResponse.builder()
                    .b2bPendingIncoming(b2bPendingIncoming)
                    .b2bPendingOutgoing(b2bPendingOutgoing)
                    .c2cPendingIncoming(c2cPendingIncoming)
                    .c2cPendingOutgoing(c2cPendingOutgoing)
                    .submitted(submitted)
                    .confirmed(confirmed)
                    .unprocessedMsg(unprocessed)
                    .failed(failed)
                    .reversed(reversed)
                    .rejected(rejected)
                    .build();

            return ResponseDTO.builder()
                    .error(false)
                    .body(response)
                    .build();
        } catch (Exception e) {
            log.error("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDTO getCashLiquidity(String dateString) {

        LocalDate date = getLocalDate(dateString);

        List<CashLiquidityProjection> cashLiquidityProjectionList = accountTransactionRegisterRepository.getCashLiquidity(date);
        List<BalanceProjection> balanceProjectionList = accountTransactionRegisterRepository.getBalance(date);

        if (cashLiquidityProjectionList.isEmpty() && balanceProjectionList.isEmpty()) {

            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        CashLiquidityResponse response = CashLiquidityResponse.builder()
                .cashLiquidity(cashLiquidityProjectionList)
                .balance(balanceProjectionList)
                .build();

        return ResponseDTO.builder()
                .error(false)
                .message("Data found")
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllInwardC2CTransactions(String dateString, String shortCode) {

        LocalDate date = getLocalDate(dateString);

        List<C2CProjection> response = customerFndTransferRepository.findAllInwardC2CTransactions(date, shortCode);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message(response.size() + " data found")
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllInwardC2CTransactionsDetails(String dateString, String shortCode) {

        LocalDate date = getLocalDate(dateString);

        List<DashboardC2CInwardTransaction> response = customerFndTransferRepository.findAllInwardC2CTransactionsDetails(date, shortCode);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message(response.size() + " data found")
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllInwardC2CTransactionsDetailsBankWise(String dateString, String shortCode, Long bankId) {

        LocalDate date = getLocalDate(dateString);

        List<CustomerFndTransferEntity> response = customerFndTransferRepository.findAllInwardC2CTransactionsDetailsBankWise(date, shortCode, bankId);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        List<InwardC2CTransactionsResponse> responses = new ArrayList<>();

        response.forEach(row -> {

            String beneficiaryBank = "";
            String beneficiaryBranch = "";
            String payerBranch = "";
            String payerBank = "";

            if (bankRepository.findByIdAndIsDeletedFalse(row.getBenBankId()).isPresent()) {
                beneficiaryBank = bankRepository.findByIdAndIsDeletedFalse(row.getBenBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(row.getPayerBranchId()).isPresent()) {
                beneficiaryBranch = branchRepository.findByIdAndIsDeletedFalse(row.getBenBranchId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(row.getPayerBranchId()).isPresent()) {
                payerBranch = branchRepository.findByIdAndIsDeletedFalse(row.getPayerBranchId()).get().getName();
            }

            if (bankRepository.findByIdAndIsDeletedFalse(row.getPayerBankId()).isPresent()) {
                payerBank = bankRepository.findByIdAndIsDeletedFalse(row.getPayerBankId()).get().getName();
            }

            responses.add(InwardC2CTransactionsResponse.toDTO(row, beneficiaryBank, beneficiaryBranch, payerBranch, shortCode, payerBank));
        });


        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " data found")
                .body(responses)
                .build();
    }

    @Override
    public ResponseDTO getAllOutwardC2CTransactions(String dateString, String shortCode) {

        LocalDate date = getLocalDate(dateString);

        List<C2CProjection> response = customerFndTransferRepository.findAllOutwardC2CTransactions(date, shortCode);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message(response.size() + " data found")
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllOutwardC2CTransactionsDetails(String dateString, String shortCode) {

        LocalDate date = getLocalDate(dateString);

        List<DashboardC2COutwardTransaction> response = customerFndTransferRepository.findAllOutwardC2CTransactionsDetails(date, shortCode);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message(response.size() + " data found")
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllOutwardC2CTransactionsDetailsBankWise(String dateString, String shortCode, Long bankId) {

        LocalDate date = getLocalDate(dateString);

        List<CustomerFndTransferEntity> response = customerFndTransferRepository.findAllOutwardC2CTransactionsDetailsBankWise(date, shortCode, bankId);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        List<OutwardC2CTransactionsResponse> responses = new ArrayList<>();

        response.forEach(row -> {

            String payerBranch = "";
            String beneficiaryBank = "";
            String beneficiaryBranch = "";


            if (branchRepository.findByIdAndIsDeletedFalse(row.getPayerBranchId()).isPresent()) {
                payerBranch = branchRepository.findByIdAndIsDeletedFalse(row.getPayerBranchId()).get().getName();
            }

            if (bankRepository.findByIdAndIsDeletedFalse(row.getBenBankId()).isPresent()) {
                beneficiaryBank = bankRepository.findByIdAndIsDeletedFalse(row.getBenBankId()).get().getName();
            }

            if (bankRepository.findByIdAndIsDeletedFalse(row.getPayerBankId()).isPresent()) {
                beneficiaryBranch = branchRepository.findByIdAndIsDeletedFalse(row.getBenBranchId()).get().getName();
            }

            responses.add(OutwardC2CTransactionsResponse.toDTO(row, payerBranch, beneficiaryBank, beneficiaryBranch, shortCode));
        });


        return ResponseDTO.builder()
                .error(false)
                .message(response.size() + " data found")
                .body(responses)
                .build();
    }

    @Override
    public ResponseDTO getAllInwardB2BTransactions(String dateString, String shortCode) {

        LocalDate date = getLocalDate(dateString);

        List<B2BProjection> response = bankFndTransferRepository.findAllInwardB2BTransactions(date, shortCode);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllInwardB2BTransactionsDetails(String dateString, String shortCode) {

        LocalDate date = getLocalDate(dateString);

        List<DashboardB2BInwardTransaction> response = bankFndTransferRepository.findAllInwardB2BTransactionDetails(date, shortCode);
        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message(response.size() + " data found")
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllInwardB2BTransactionsDetailsBankWise(String dateString, String shortCode, Long bankId) {

        LocalDate date = getLocalDate(dateString);

        List<BankFndTransferEntity> response = bankFndTransferRepository.findAllInwardB2BTransactionDetailsBankWise(date, shortCode, bankId);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        List<InwardB2BTransactionsResponse> responses = new ArrayList<>();

        response.forEach(row -> {

            String payerBank = "";
            String payerBranch = "";

            if (bankRepository.findByIdAndIsDeletedFalse(row.getPayerBankId()).isPresent()) {
                payerBank = bankRepository.findByIdAndIsDeletedFalse(row.getPayerBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(row.getPayerBranchId()).isPresent()) {
                payerBranch = branchRepository.findByIdAndIsDeletedFalse(row.getPayerBranchId()).get().getName();
            }

            responses.add(InwardB2BTransactionsResponse.toDTO(row, payerBank, payerBranch, shortCode));
        });

        return ResponseDTO.builder()
                .error(false)
                .body(responses)
                .build();
    }

    @Override
    public ResponseDTO getAllOutwardB2BTransactions(String dateString, String shortCode) {

        LocalDate date = getLocalDate(dateString);

        List<B2BProjection> response = bankFndTransferRepository.findAllOutwardB2BTransactions(date, shortCode);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllOutwardB2BTransactionsDetails(String dateString, String shortCode) {

        LocalDate date = getLocalDate(dateString);

        List<DashboardB2BOutwardTransaction> response = bankFndTransferRepository.findAllOutwardB2BTransactionDetails(date, shortCode);
        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message(response.size() + " data found")
                .body(response)
                .build();
    }

    @Override
    public ResponseDTO getAllOutwardB2BTransactionsDetailsBankWise(String dateString, String shortCode, Long bankId) {

        LocalDate date = getLocalDate(dateString);

        List<BankFndTransferEntity> response = bankFndTransferRepository.findAllOutwardB2BTransactionDetailsBankWise(date, shortCode, bankId);

        if (response.isEmpty()) {
            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        List<OutwardB2BTransactionsResponse> responses = new ArrayList<>();

        response.forEach(row -> {

            String beneficiaryBank = "";
            String beneficiaryBranch = "";
            String beneficiaryReceiverBranch = "";

            if (bankRepository.findByIdAndIsDeletedFalse(row.getBenBankId()).isPresent()) {
                beneficiaryBank = bankRepository.findByIdAndIsDeletedFalse(row.getBenBankId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(row.getBenBranchId()).isPresent()) {
                beneficiaryBranch = branchRepository.findByIdAndIsDeletedFalse(row.getBenBranchId()).get().getName();
            }

            if (branchRepository.findByIdAndIsDeletedFalse(row.getFcRecBranchId()).isPresent()) {
                beneficiaryReceiverBranch = branchRepository.findByIdAndIsDeletedFalse(row.getFcRecBranchId()).get().getName();
            }

            responses.add(OutwardB2BTransactionsResponse.toDTO(row, beneficiaryBank, beneficiaryBranch, beneficiaryReceiverBranch, shortCode));
        });

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " data found")
                .body(responses)
                .build();
    }

    @Override
    public ResponseDTO getApprovalEventLogs(String dateString) {

        LocalDate date = getLocalDate(dateString);

        List<ApprovalEventLogProjection> response = customerFndTransferRepository.getApprovalEventLogs(date);

        if (response.isEmpty()) {

            return ResponseDTO.builder()
                    .error(false)
                    .message("No data found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message(response.size() + " data found")
                .body(response)
                .build();
    }

    private static LocalDate getLocalDate(String dateString) {

        LocalDate date;

        if (!dateString.isEmpty()) {
            date = DateUtility.toDate(dateString);
        } else {
            date = LocalDate.now();
        }
        return date;
    }

    @Override
    public ResponseDTO checkSTPGStatus() {

        try {
            Socket socket = new Socket(stpgIpAddress, stpgPort);

            if (socket.isConnected()) {

                socket.close();

                return ResponseDTO.builder()
                        .error(false)
                        .message("AVAILABLE")
                        .build();
            }
        } catch (IOException e) {

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }

        return ResponseDTO.builder()
                .error(true)
                .message("UNAVAILABLE")
                .build();
    }
}
