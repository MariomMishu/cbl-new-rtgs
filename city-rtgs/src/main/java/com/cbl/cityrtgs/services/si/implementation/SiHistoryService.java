package com.cbl.cityrtgs.services.si.implementation;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.si.SiHistoryResponse;
import com.cbl.cityrtgs.models.entitymodels.si.SiFrequency;
import com.cbl.cityrtgs.models.entitymodels.si.SiHistory;
import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.repositories.configuration.BankRepository;
import com.cbl.cityrtgs.repositories.configuration.BranchRepository;
import com.cbl.cityrtgs.repositories.si.SiFrequencyRepository;
import com.cbl.cityrtgs.repositories.si.SiHistoryRepository;
import com.cbl.cityrtgs.repositories.specification.SILogSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiHistoryService {

    private final SiFrequencyRepository siFrequencyRepository;
    private final SiHistoryRepository siHistoryRepository;
    private final BankRepository bankRepository;
    private final BranchRepository branchRepository;

    public ResponseDTO search(String description, String error, String status, String frequency, String date, int pageNo, int pageSize) {

        Specification<SiHistory> specification = Specification.where(null);

        Long departmentId = LoggedInUserDetails.getUserInfoDetails().getDept().getId();

        specification = specification.and(SILogSpecification.hasDepartment(departmentId));

        if (!description.isEmpty()) {

            specification = specification.or(SILogSpecification.hasDescription(description));
        }

        if (!error.isEmpty()) {

            specification = specification.or(SILogSpecification.hasError(error));
        }

        if (!status.isEmpty()) {

            specification = specification.or(SILogSpecification.hasStatus(status));
        }

        if (!frequency.isEmpty()) {

            Optional<SiFrequency> optional = siFrequencyRepository.findByFrequency(frequency);

            if (optional.isPresent()) {

                SiFrequency siFrequency = optional.get();

                specification = specification.or(SILogSpecification.hasFrequency(siFrequency.getId()));
            }
        }

        if (!date.equals("")) {

            specification = specification.or(SILogSpecification.hasDate(LocalDate.parse(date)));
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<SiHistory> list = siHistoryRepository.findAll(specification, pageable);
        List<SiHistoryResponse> responses = new ArrayList<>();

        if (!list.isEmpty()) {

            list.forEach(siHistory -> {

                String beneficiaryBank = "";
                String beneficiaryBranch = "";

                if (siHistory.getBeneficiaryBankId() != null) {

                    if (bankRepository.findByIdAndIsDeletedFalse(siHistory.getBeneficiaryBankId()).isPresent()) {
                        beneficiaryBank = bankRepository.findByIdAndIsDeletedFalse(siHistory.getBeneficiaryBankId()).get().getName();
                    }
                }

                if (siHistory.getBeneficiaryBranchId() != null) {

                    if (branchRepository.findByIdAndIsDeletedFalse(siHistory.getBeneficiaryBranchId()).isPresent()) {
                        beneficiaryBranch = branchRepository.findByIdAndIsDeletedFalse(siHistory.getBeneficiaryBranchId()).get().getName();
                    }
                }

                responses.add(SiHistoryResponse.toDto(siHistory, beneficiaryBank, beneficiaryBranch));
            });
        }

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " record(s) found!")
                .body(responses)
                .build();
    }

    public ResponseDTO findAllExecutedSI() {

        List<SiHistoryResponse> responses = new ArrayList<>();
        List<SiHistory> list = siHistoryRepository.findAll();

        if (!list.isEmpty()) {

            list.forEach(siHistory -> {

                String beneficiaryBank = "";
                String branchName = "";

                if (siHistory.getBeneficiaryBankId() != null) {
                    if (bankRepository.findByIdAndIsDeletedFalse(siHistory.getBeneficiaryBankId()).isPresent()) {
                        beneficiaryBank = bankRepository.findByIdAndIsDeletedFalse(siHistory.getBeneficiaryBankId()).get().getName();
                    }
                }

                if (siHistory.getBeneficiaryBranchId() != null) {
                    if (branchRepository.findByIdAndIsDeletedFalse(siHistory.getBeneficiaryBranchId()).isPresent()) {
                        branchName = branchRepository.findByIdAndIsDeletedFalse(siHistory.getBeneficiaryBranchId()).get().getName();
                    }
                }

                responses.add(SiHistoryResponse.toDto(siHistory, beneficiaryBank, branchName));
            });
        }

        return ResponseDTO.builder()
                .error(false)
                .body(responses)
                .build();
    }

    public void save(SiUpcomingItem siUpcomingItem, String status, String responseCode, String message, LocalDate dueDate) {

        try {

            SiHistory siHistory = SiHistory.builder()
                    .currency(siUpcomingItem.getSiConfiguration().getCurrency())
                    .siFrequency(siUpcomingItem.getSiConfiguration().getSiFrequency())
                    .siamountType(siUpcomingItem.getSiConfiguration().getAmountType())
                    .customerFundTransfer(siUpcomingItem.getCustomerFundTransfer())
                    .createdBy(siUpcomingItem.getSiConfiguration().getCreatedBy())
                    .executionDate(siUpcomingItem.getDeferredDate())
                    .name(siUpcomingItem.getName())
                    .payerAccountId(siUpcomingItem.getPayerAccountId())
                    .payerName(siUpcomingItem.getPayerName())
                    .payerAccountNo(siUpcomingItem.getPayerAccountNo())
                    .payerStatus(siUpcomingItem.getPayerStatus())
                    .beneficiaryName(siUpcomingItem.getBeneficiaryName())
                    .beneficiaryAccountNo(siUpcomingItem.getBeneficiaryAccountNo())
                    .beneficiaryAccountType(siUpcomingItem.getBeneficiaryAccountType())
                    .beneficiaryBankId(siUpcomingItem.getBeneficiaryBankId())
                    .beneficiaryBranchId(siUpcomingItem.getBeneficiaryBranchId())
                    .beneficiaryBranchRoutingNo(siUpcomingItem.getBeneficiaryBranchRoutingNo())
                    .beneficiaryBankBic(siUpcomingItem.getBeneficiaryBankBic())
                    .narration(siUpcomingItem.getNarration())
                    .accountBalance(siUpcomingItem.getAccountBalance())
                    .amount(siUpcomingItem.getAmount())
                    .lcNo(siUpcomingItem.getLcNo())
                    .transactionTypeCode(siUpcomingItem.getTransactionTypeCode())
                    .status(status)
                    .dueDate(dueDate)
                    .responseCode(responseCode)
                    .message(message)
                    .isFired(false)
                    .createDate(new Date())
                    .build();

            siHistoryRepository.save(siHistory);
            log.info("SI History saved: {}", siUpcomingItem.getId());
        } catch (Exception e) {
            log.error("SI History save error: {}", e.getMessage());
        }
    }
}
