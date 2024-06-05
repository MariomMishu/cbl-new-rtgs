package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.dto.message.ReturnReason;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionVerificationStatus;
import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.message.InOutMsgLogRepository;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import com.cbl.cityrtgs.services.transaction.ReturnReasonService;
import com.cbl.cityrtgs.services.transaction.c2c.CustomerFundTransferService;
import com.cbl.cityrtgs.services.transaction.reverseTxn.ReverseFundTransferService;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class Pacs00400104Inward implements Inward {
    private final InOutMsgLogRepository inOutMsgLogRepository;
    private final InterBankFundTransferRepository interBankFundTransferRepository;
    private final InterCustomerFundTransferRepository interCustomerFundTransferRepository;
    private final BankFndTransferRepository bankFndTransferRepository;
    private final MsgLogRepository msgLogRepository;
    private final CustomerFundTransferService customerFundTransferService;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final ReverseFundTransferService reversalService;
    private final ReturnReasonService returnReasonService;

    @Autowired
    public Pacs00400104Inward(InterBankFundTransferRepository interBankFundTransferRepository,
                              InterCustomerFundTransferRepository interCustomerFundTransferRepository,
                              BankFndTransferRepository bankFndTransferRepository,
                              CustomerFndTransferRepository customerFndTransferRepository,
                              CustomerFundTransferService customerFundTransferService,
                              InOutMsgLogRepository inOutMsgLogRepository,
                              MsgLogRepository msgLogRepository,
                              ReverseFundTransferService reversalService,
                              ReturnReasonService returnReasonService) {
        this.interBankFundTransferRepository = interBankFundTransferRepository;
        this.interCustomerFundTransferRepository = interCustomerFundTransferRepository;
        this.inOutMsgLogRepository = inOutMsgLogRepository;
        this.customerFndTransferRepository = customerFndTransferRepository;
        this.bankFndTransferRepository = bankFndTransferRepository;
        this.customerFundTransferService = customerFundTransferService;
        this.msgLogRepository = msgLogRepository;
        this.reversalService = reversalService;
        this.returnReasonService = returnReasonService;
    }

    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.PACS004.value();
    }

    @Override
   public void processInward(long id, Object doc) {

        log.info("----------- Pacs00400104Inward --------------");

        Document message = (iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document) doc;
        InterBankTransferEntity fundTransfer;
        Optional<InterCustomerFundTransferEntity> _interCustomerFundTransfer;
        InterCustomerFundTransferEntity interCustomerFundTransfer;
        BankFndTransferEntity bankFndTransferTxn;
        CustomerFndTransferEntity customerFndTransferTxn;
        InOutMsgLogEntity inOutMsgLog;
        String originalTxnReference;
        try {

            if (!message.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS008.value()) && !message.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS004.value())) {
                if (message.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS009.value())) {
                    originalTxnReference = message.getPmtRtr().getTxInf().getOrgnlTxId();
                    Optional<InOutMsgLogEntity> inOutMsgLogEntity = inOutMsgLogRepository.findByMsgIdAndRouteTypeAndIsDeletedFalse(message.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgId(), RoutingType.Outgoing.toString());
                    if (inOutMsgLogEntity.isPresent()) {
                        inOutMsgLog = inOutMsgLogEntity.get();
                        bankFndTransferTxn = bankFndTransferRepository.findByReferenceNumberAndIsDeletedFalse(originalTxnReference).get();
                        fundTransfer = interBankFundTransferRepository.findByBatchNumberAndIsDeletedFalse(bankFndTransferTxn.getParentBatchNumber()).get();
                        fundTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Returned);
                        bankFndTransferTxn.setTransactionStatus(TransactionStatus.Reversed);
                        if (message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getRsn().getPrtry() != null) {
                            bankFndTransferTxn.setReturnCode(message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getRsn().getPrtry());
                        }

                        if (message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getRsn().getCd() != null) {
                            bankFndTransferTxn.setReturnCode(message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getRsn().getCd());
                        }

                        if (message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getAddtlInf() != null && message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getAddtlInf().size() > 0) {
                            bankFndTransferTxn.setReturnReason(message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getAddtlInf().get(0));
                        }
                        reversalService.doReversal(bankFndTransferTxn, fundTransfer.getEntryUser());
                        bankFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS004.value()+": " + message.getPmtRtr().getGrpHdr().getMsgId());
                        inOutMsgLog.setMsgId(message.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgId());
                        inOutMsgLog.setMsgType(MessageDefinitionIdentifier.PACS009.value());
                        inOutMsgLog.setMsgCreationDate(new Date());
                        inOutMsgLog.setRouteType(RoutingType.Incoming.toString());
                        inOutMsgLog.setResponseMsgId(message.getPmtRtr().getGrpHdr().getMsgId());
                        inOutMsgLog.setResponseMsgType(MessageDefinitionIdentifier.PACS004.value());
                        inOutMsgLog.setMsgId(fundTransfer.getBatchNumber());
                        inOutMsgLog.setId(inOutMsgLog.getId());

                    } else {
                        inOutMsgLog = new InOutMsgLogEntity();
                    }
                    try {
                        inOutMsgLogRepository.save(inOutMsgLog);
                        msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.PROCESSED));
                    } catch (Exception e) {
                        log.error("{}", e.getMessage());
                        msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.UNPROCESSED));
                    }
                }
            } else {
                originalTxnReference = message.getPmtRtr().getTxInf().getOrgnlTxId();
                Optional<InOutMsgLogEntity> inOutMsgLogEntity = inOutMsgLogRepository.getInOutMsgLogByTxnRefAndDate(originalTxnReference, RoutingType.Outgoing.toString());
                if (inOutMsgLogEntity.isPresent()) {
                    try {
                        _interCustomerFundTransfer = interCustomerFundTransferRepository.getByBatchNumberAndRoutingTypeAndIsDeletedFalse(inOutMsgLogEntity.get().getBatchNumber());
                        if (_interCustomerFundTransfer.isPresent()) {
                            interCustomerFundTransfer = _interCustomerFundTransfer.get();
                            interCustomerFundTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Returned);
                            customerFndTransferTxn = customerFndTransferRepository.getByBatchNumberAndRoutingTypeAndIsDeletedFalse(inOutMsgLogEntity.get().getBatchNumber(), originalTxnReference);
                            customerFndTransferTxn.setTransactionStatus(TransactionStatus.Reversed.toString());
                            String returnCode;
                            ReturnReason returnReason;
                            if (message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getRsn().getPrtry() != null) {
                                if (message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getAddtlInf() != null && message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getAddtlInf().size() > 0) {
                                    returnCode = message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getRsn().getPrtry().toUpperCase().trim();
                                    if (returnCode.startsWith("R")) {
                                        returnCode = returnCode.replaceAll(" ", "");
                                        returnCode = returnCode.replace("R", "R ");
                                    } else {
                                        returnCode = "R 09";
                                    }

                                    customerFndTransferTxn.setReturnReason(message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getAddtlInf().get(0));
                                    customerFndTransferTxn.setReturnCode(returnCode);
                                } else {
                                    returnCode = message.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getRsn().getPrtry().toUpperCase().trim();
                                    if (returnCode.startsWith("R")) {
                                        returnCode = returnCode.replaceAll(" ", "");
                                        returnCode = returnCode.replace("R", "R ");
                                        returnReason = returnReasonService.getReturnReasonByCode(returnCode);
                                        customerFndTransferTxn.setReturnReason(returnReason.getDescription());
                                        customerFndTransferTxn.setReturnCode(returnReason.getCode());
                                    } else {
                                        customerFndTransferTxn.setReturnReason("Entry Refused by the Receiver");
                                        customerFndTransferTxn.setReturnCode("R 09");
                                    }
                                }
                            }

                            customerFundTransferService.updateStatus(interCustomerFundTransfer, customerFndTransferTxn, TransactionStatus.Reversed, originalTxnReference);
                            reversalService.doReversalWithOutCharge(customerFndTransferTxn, interCustomerFundTransfer.getEntryUser(), MessageDefinitionIdentifier.PACS004.value());
                            customerFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS004.value()+": " + message.getPmtRtr().getGrpHdr().getMsgId());
                            inOutMsgLog = new InOutMsgLogEntity();
                            inOutMsgLog.setCreatedAt(new Date());
                            inOutMsgLog.setMsgId(message.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgId());
                            inOutMsgLog.setMsgType(MessageDefinitionIdentifier.PACS008.value());
                            inOutMsgLog.setMsgCreationDate(new Date());
                            inOutMsgLog.setRouteType(RoutingType.Incoming.toString());
                            inOutMsgLog.setResponseMsgId(message.getPmtRtr().getGrpHdr().getMsgId());
                            inOutMsgLog.setResponseMsgType(MessageDefinitionIdentifier.PACS004.value());
                            inOutMsgLog.setMsgId(_interCustomerFundTransfer.get().getBatchNumber());
                            inOutMsgLogRepository.save(inOutMsgLog);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }

                try {
                    msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.PROCESSED));
                } catch (Exception e) {
                    log.error("{}", e.getMessage());
                    msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.UNPROCESSED));
                }
            }
        } catch (Exception e) {
            msgLogRepository.updateMsgLogEntityStatus(id, String.valueOf(MessageProcessStatus.UNPROCESSED));
            log.error("handle pacs004 inward Message(): {}", e.getMessage());
        }
    }
}
