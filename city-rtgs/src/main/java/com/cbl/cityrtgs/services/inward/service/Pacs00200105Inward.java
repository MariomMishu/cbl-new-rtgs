package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionVerificationStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.notification.ReportStatusNotificationEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.notification.StatusReasonEntity;
import com.cbl.cityrtgs.repositories.message.InOutMsgLogRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.notification.StatusNotificationRepository;
import com.cbl.cityrtgs.repositories.transaction.notification.StatusReasonRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
import com.cbl.cityrtgs.services.transaction.c2c.IbFundTransferService;
import com.cbl.cityrtgs.services.transaction.reverseTxn.ReverseFundTransferService;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.Document;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTransaction33CMA;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReasonInformation9CMA1;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.TransactionIndividualStatus3Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

@Slf4j
@Service
public class Pacs00200105Inward implements Inward {
    private final InterBankFundTransferRepository repository;
    private final BankFndTransferRepository bankFndTransferRepository;
    private final InOutMsgLogRepository inOutMsgLogRepository;
    private final InterCustomerFundTransferRepository interCustomerRepository;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final ReverseFundTransferService reversalService;
    private final StatusNotificationRepository statusNotificationRepository;
    private final StatusReasonRepository statusReasonRepository;
    private final MsgLogService msgLogService;
    private final IbFundTransferService ibFundTransferService;

    @Autowired
    public Pacs00200105Inward(InterBankFundTransferRepository repository,
                              BankFndTransferRepository bankFndTransferRepository,
                              InOutMsgLogRepository inOutMsgLogRepository,
                              InterCustomerFundTransferRepository interCustomerRepository,
                              CustomerFndTransferRepository customerFndTransferRepository,
                              ReverseFundTransferService reversalService,
                              StatusNotificationRepository statusNotificationRepository,
                              StatusReasonRepository statusReasonRepository,
                              MsgLogService msgLogService,
                              IbFundTransferService ibFundTransferService) {
        this.repository = repository;
        this.bankFndTransferRepository = bankFndTransferRepository;
        this.inOutMsgLogRepository = inOutMsgLogRepository;
        this.interCustomerRepository = interCustomerRepository;
        this.customerFndTransferRepository = customerFndTransferRepository;
        this.reversalService = reversalService;
        this.statusNotificationRepository = statusNotificationRepository;
        this.statusReasonRepository = statusReasonRepository;
        this.msgLogService = msgLogService;
        this.ibFundTransferService = ibFundTransferService;

    }

    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.PACS002.value();
    }

    @Override
    public void processInward(long id, Object doc) {

        log.info("ID: {}", id);

        log.info("----------- Pacs00200104Inward --------------");
        String errorMessage = null;
        Document document = (iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.Document) doc;
        TransactionResponse txnResponse;
        try {
            String ref = "";
            ReportStatusNotificationEntity reportStatusNotification = new ReportStatusNotificationEntity();

            XMLGregorianCalendar creationDate = document.getFIToFIPmtStsRpt().getGrpHdr().getCreDtTm();
            reportStatusNotification.setCreationDate(creationDate != null ? creationDate.toGregorianCalendar().getTime() : null);

            reportStatusNotification.setGroupSts(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getGrpSts().toString());
            reportStatusNotification.setMsgId(document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());

            XMLGregorianCalendar orgnlCreDtTm = document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlCreDtTm();
            reportStatusNotification.setOriginalCreationDate(orgnlCreDtTm != null ? orgnlCreDtTm.toGregorianCalendar().getTime().toString() : null);

            reportStatusNotification.setOriginalMsgId(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgId());
            reportStatusNotification.setOriginalMsgNmId(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId());
            Set<StatusReasonEntity> reasons = new HashSet<>();
            StatusReasonInformation9CMA1 reason = document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getStsRsnInf();
            StatusReasonEntity statusReason = new StatusReasonEntity();
            statusReason.setReason(reason.getRsn().getPrtry());
            statusReason.setAddtnalInfs(new ArrayList<>());
            statusReason.getAddtnalInfs().add(reason.getAddtlInf());
            Iterator<PaymentTransaction33CMA> txInfAndStsList = document.getFIToFIPmtStsRpt().getTxInfAndSts().iterator();
            reportStatusNotification = statusNotificationRepository.save(reportStatusNotification);
            statusReason.setStsNotification(reportStatusNotification);
            reasons.add(statusReason);
            statusReasonRepository.saveAll(reasons);
            PaymentTransaction33CMA txn;

            while (txInfAndStsList.hasNext()) {
                txn = txInfAndStsList.next();
                BankFndTransferEntity bankFndTransferTxn;
                CustomerFndTransferEntity customerFndTransferTxn;
                if (txn.getTxSts() == TransactionIndividualStatus3Code.RJCT) {
                    if (document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS009.value())) {
                        ref = document.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxId();
                        Optional<BankFndTransferEntity> optionalBankFndTransferTxn = bankFndTransferRepository.findByReferenceNumberAndIsDeletedFalse(txn.getOrgnlTxId());
                        if (optionalBankFndTransferTxn.isPresent()) {
                            bankFndTransferTxn = optionalBankFndTransferTxn.get();
                            InterBankTransferEntity interBankFundTransfer = repository.findByBatchNumberAndIsDeletedFalse(bankFndTransferTxn.getParentBatchNumber()).get();
                            interBankFundTransfer.setVerificationStatus(3);
                            interBankFundTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Approved);
                            if (bankFndTransferTxn.getTransactionStatus().equals(TransactionStatus.Pending)) {
                                bankFndTransferTxn.setTransactionStatus(TransactionStatus.Rejected);
                                bankFndTransferTxn.setReturnCode(txn.getStsRsnInf().getRsn().getPrtry());
                                bankFndTransferTxn.setReturnReason(txn.getStsRsnInf().getAddtlInf());
                                reversalService.doReversal(bankFndTransferTxn, interBankFundTransfer.getEntryUser());
                                bankFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS002.value() + ": " + document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
                                bankFndTransferTxn.setId(bankFndTransferTxn.getId());
                                bankFndTransferRepository.save(bankFndTransferTxn);
                                interBankFundTransfer.setId(interBankFundTransfer.getId());
                                repository.save(interBankFundTransfer);
                            }
                        }

                    } else if (document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS008.value())) {
                        ref = document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgId();
                        Optional<CustomerFndTransferEntity> optionalCustomerFndTransferTxn = customerFndTransferRepository.findByReferenceNumberAndIsDeletedFalse(txn.getOrgnlTxId());
                        if (optionalCustomerFndTransferTxn.isPresent()) {
                            customerFndTransferTxn = optionalCustomerFndTransferTxn.get();
                            InterCustomerFundTransferEntity interCustomerFundTransfer = interCustomerRepository.findByBatchNumberAndIsDeletedFalse(customerFndTransferTxn.getParentBatchNumber()).get();
                            if (customerFndTransferTxn.getTransactionStatus().equals("Pending")) {
                                if (customerFndTransferTxn.isBatchTxn()) {
                                    var totalTxnCount = customerFndTransferRepository.totalCountByBatch(customerFndTransferTxn.getParentBatchNumber());
                                    var rejectedCount = customerFndTransferRepository.existsByBatchNumber(customerFndTransferTxn.getParentBatchNumber(), customerFndTransferTxn.getId());
                                    if ((totalTxnCount - 1) == rejectedCount && interCustomerFundTransfer.isBatchChargeEnable()) {
                                        txnResponse = reversalService.doReversalWithCharge(customerFndTransferTxn, interCustomerFundTransfer.getEntryUser(), MessageDefinitionIdentifier.PACS002.value());
                                        customerFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS002.value() + ": " + document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
                                        log.info("doReversalWithCharge, {}", customerFndTransferTxn.getReferenceNumber());
                                    } else {
                                        txnResponse = reversalService.doReversalWithOutCharge(customerFndTransferTxn, interCustomerFundTransfer.getEntryUser(), MessageDefinitionIdentifier.PACS002.value());
                                        customerFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS002.value() + ": " + document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
                                        log.info("doReversalWithOutCharge, {}", customerFndTransferTxn.getReferenceNumber());
                                    }
                                } else {
                                    txnResponse = reversalService.doReversal(customerFndTransferTxn, interCustomerFundTransfer.getEntryUser(), MessageDefinitionIdentifier.PACS002.value());
                                    customerFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS002.value() + ": " + document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
                                    if (customerFndTransferTxn.isIbTxn() && !txnResponse.isError()) {
                                        ibFundTransferService.updateIbTxn(customerFndTransferTxn.getReferenceNumber(), TransactionStatus.Rejected.toString(), false, "pacs.002.001.05: " + document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
                                        log.info("Update Ib Txn, {}", customerFndTransferTxn.getReferenceNumber());
                                    }
                                }
                                if (!txnResponse.isError()) {
                                    customerFndTransferTxn.setTransactionStatus(TransactionStatus.Rejected.toString());
                                    customerFndTransferTxn.setReturnCode(txn.getStsRsnInf().getRsn().getPrtry());
                                    customerFndTransferTxn.setReturnReason(txn.getStsRsnInf().getAddtlInf());
                                    customerFndTransferRepository.save(customerFndTransferTxn);

                                    interCustomerFundTransfer.setVerificationStatus(3);
                                    interCustomerFundTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Approved);
                                    interCustomerRepository.save(interCustomerFundTransfer);
                                }

                            }
                        }

                    }
                } else if (txn.getTxSts() == TransactionIndividualStatus3Code.ACCP) {
                    if (document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS009.value())) {
                        ref = document.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxId();
                        Optional<BankFndTransferEntity> optionalBankFndTransferTxn = bankFndTransferRepository.findByReferenceNumberAndIsDeletedFalse(txn.getOrgnlTxId());
                        if (optionalBankFndTransferTxn.isPresent()) {
                            bankFndTransferTxn = optionalBankFndTransferTxn.get();
                            bankFndTransferTxn.setTransactionStatus(TransactionStatus.Accepted);
                            bankFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS002.value() + ": ACCEPTED");
                            bankFndTransferTxn.setId(bankFndTransferTxn.getId());
                            bankFndTransferRepository.save(bankFndTransferTxn);
                        }

                    } else if (document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS008.value())) {
                        ref = document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgId();
                        Optional<CustomerFndTransferEntity> optionalCustomerFndTransferTxn = customerFndTransferRepository.findByReferenceNumberAndIsDeletedFalse(txn.getOrgnlTxId());
                        if (optionalCustomerFndTransferTxn.isPresent()) {
                            customerFndTransferTxn = optionalCustomerFndTransferTxn.get();
                            customerFndTransferTxn.setTransactionStatus(TransactionStatus.Accepted.toString());
                            customerFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS002.value() + ": ACCEPTED");
                            customerFndTransferRepository.save(customerFndTransferTxn);
                        }
                    }
                } else if (txn.getTxSts() == TransactionIndividualStatus3Code.PDNG) {
                    if (document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS009.value())) {
                        ref = document.getFIToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlTxId();
                        Optional<BankFndTransferEntity> optionalBankFndTransferTxn = bankFndTransferRepository.findByReferenceNumberAndIsDeletedFalse(txn.getOrgnlTxId());
                        if (optionalBankFndTransferTxn.isPresent()) {
                            bankFndTransferTxn = optionalBankFndTransferTxn.get();
                            bankFndTransferTxn.setTransactionStatus(TransactionStatus.Pending);
                            bankFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS002.value() + ": PENDING");
                            bankFndTransferTxn.setId(bankFndTransferTxn.getId());
                            bankFndTransferRepository.save(bankFndTransferTxn);
                        }

                    } else if (document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().equals(MessageDefinitionIdentifier.PACS008.value())) {
                        ref = document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgId();
                        Optional<CustomerFndTransferEntity> optionalCustomerFndTransferTxn = customerFndTransferRepository.findByReferenceNumberAndIsDeletedFalse(txn.getOrgnlTxId());
                        if (optionalCustomerFndTransferTxn.isPresent()) {
                            customerFndTransferTxn = optionalCustomerFndTransferTxn.get();
                            customerFndTransferTxn.setTransactionStatus(TransactionStatus.Pending.toString());
                            customerFndTransferTxn.setErrorMsg(MessageDefinitionIdentifier.PACS002.value() + ": PENDING");
                            customerFndTransferRepository.save(customerFndTransferTxn);
                        }
                    }
                }

            }
            Optional<InOutMsgLogEntity> inOutMsgLogEntity = inOutMsgLogRepository.getInOutMsgLogByTxnRefAndDate(ref, RoutingType.Outgoing.toString());
            log.info("Handle pacs002 request: " + ref);

            try {
                if (inOutMsgLogEntity.isPresent()) {
                    InOutMsgLogEntity inOutMsgLog;
                    inOutMsgLog = inOutMsgLogEntity.get();
                    inOutMsgLog.setMsgCreationDate(new Date());
                    inOutMsgLog.setMsgId(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgId());
                    inOutMsgLog.setResponseMsgId(document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
                    inOutMsgLog.setResponseMsgType(MessageDefinitionIdentifier.PACS002.value());
                    inOutMsgLog.setId(inOutMsgLog.getId());
                    inOutMsgLog.setCreatedAt(inOutMsgLog.getCreatedAt());
                    inOutMsgLogRepository.save(inOutMsgLog);
                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                } else {
                    errorMessage = ref + ": Transaction Not Found in In Out Log";
                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), errorMessage);
                }

            } catch (Exception ex) {
                errorMessage = errorMessage + " " + ex.getMessage();
                msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), errorMessage);
                log.error("{}", ex.getMessage());
            }

        } catch (Exception e) {
            log.error("handle pacs002 inward Message(): {}", e.getMessage());
            errorMessage = errorMessage + " " + e.getMessage();
            msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), errorMessage);
            log.error("handle pacs002 inward Message(): {}", e.getMessage());
        }
    }

}
