package com.cbl.cityrtgs.services.transaction.returnTxn;

import com.cbl.cityrtgs.models.dto.message.ReturnReason;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.transaction.TransactionVerificationStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.transaction.MessageGenerateService;
import com.cbl.cityrtgs.services.transaction.ReturnReasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReturnFundTransferService {
    private final InterCustomerFundTransferRepository interCustomerFundTransferRepository;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final ReturnReasonService returnReasonService;
    private final MessageGenerateService messageGenerateService;

    public ResponseDTO sendPaymentReturnMessage(CustomerFndTransferEntity fundTransferTxn, String returnCode){

        String errorMessage = "";
        ReturnReason returnReason;
        Optional<InterCustomerFundTransferEntity> fundTransferEntity = interCustomerFundTransferRepository.getByBatchNumberAndRoutingTypeAndDate(fundTransferTxn.getParentBatchNumber());
        InterCustomerFundTransferEntity fundTransfer;
        if (fundTransferEntity.isPresent()) {
            fundTransfer = fundTransferEntity.get();
            Optional<CustomerFndTransferEntity> customerFndTransferEntity = customerFndTransferRepository.findByReferenceNumberAndIsDeletedFalse(fundTransferTxn.getReferenceNumber());
            if (customerFndTransferEntity.isPresent()) {
                fundTransferTxn = customerFndTransferEntity.get();
                if (fundTransferTxn.getTransactionStatus().equals(TransactionStatus.Returned.toString())) {
                    errorMessage = "Transaction already returned.";
                   return ResponseDTO.builder().error(true).message(errorMessage).build();
                } else {
                    fundTransfer.setVerificationStatus(2);
                    fundTransfer.setTransactionStatus(TransactionStatus.Returned);
                    fundTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Returned);

                    fundTransferTxn.setTransactionStatus(TransactionStatus.Returned.toString());
                    fundTransferTxn.setVerificationStatus(TransactionVerificationStatus.Returned.toString());
                    if(returnCode != null){
                         returnReason = returnReasonService.getReturnReasonByCode(returnCode);
                        if (Objects.isNull(returnReason)) {
                            returnReason = new ReturnReason();
                            log.warn("Return reason not found with code: {}, using default return reason", returnCode);
                            returnReason.setCode("R 09").setDescription("Entry Refused by the Receiver");
                        }
                    }else{
                        returnReason = new ReturnReason();
                        log.warn("Return reason not found with code: {}, using default return reason", returnCode);
                        returnReason.setCode("R 09").setDescription("Entry Refused by the Receiver");
                    }

                    fundTransferTxn.setReturnCode(returnReason.getCode());
                    fundTransferTxn.setReturnReason(returnReason.getDescription());
                    log.info("Sending return message.");
                    messageGenerateService.processPacs004OutwardRequest(fundTransfer, fundTransferTxn, returnReason);
                    customerFndTransferRepository.save(fundTransferTxn);
                    interCustomerFundTransferRepository.save(fundTransfer);
                    return ResponseDTO.builder().error(false).message("Transaction Return Message Sent Successfully").build();
               }

            }else{
               return ResponseDTO.builder().error(true).message("Transaction Not Found!").build();
            }
        }else{
            return ResponseDTO.builder().error(true).message("Transaction Not Found!").build();
        }
    }
}
