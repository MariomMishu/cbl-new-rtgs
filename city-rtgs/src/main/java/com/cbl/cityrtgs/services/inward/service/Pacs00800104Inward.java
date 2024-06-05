package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.common.enums.SequenceType;
import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.common.logger.RtgsLogger;
import com.cbl.cityrtgs.common.utility.XMLParser;
import com.cbl.cityrtgs.models.dto.message.MsgLogResponse;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionVerificationStatus;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.configuration.BankRepository;
import com.cbl.cityrtgs.repositories.configuration.BranchRepository;
import com.cbl.cityrtgs.repositories.configuration.CurrencyRepository;
import com.cbl.cityrtgs.repositories.message.InOutMsgLogRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.transaction.CustomerAccountDetailsService;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
import com.cbl.cityrtgs.services.transaction.ReferenceNoGenerateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.cbl.cityrtgs.common.utility.ValidationUtility.*;


@Slf4j
@Service
public class Pacs00800104Inward implements Inward {
    private final InterCustomerFundTransferRepository repository;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final CurrencyRepository currencyRepository;
    private final BankRepository bankRepository;
    private final BranchRepository branchRepository;
    private final InOutMsgLogRepository inOutMsgLogRepository;
    private final ReferenceNoGenerateService referenceNoGenerateService;
    private final DepartmentService departmentService;
    private final DepartmentAccountService departmentAccountService;
    private final CustomerAccountDetailsService customerAccountDetailsService;
    private final MsgLogService msgLogService;
    private final RtgsLogger rtgsLogger;
    private final ObjectMapper objectMapper;
    private final Camt05400104Inward camt05400104Inward;

    @Autowired
    public Pacs00800104Inward(
            InterCustomerFundTransferRepository repository,
            CustomerFndTransferRepository customerFndTransferRepository,
            CurrencyRepository currencyRepository,
            BankRepository bankRepository, BranchRepository branchRepository,
            InOutMsgLogRepository inOutMsgLogRepository,
            DepartmentService departmentService,
            DepartmentAccountService departmentAccountService,
            CustomerAccountDetailsService customerAccountDetailsService,
            ReferenceNoGenerateService referenceNoGenerateService,
            MsgLogService msgLogService,
            RtgsLogger rtgsLogger,
            ObjectMapper objectMapper,
            Camt05400104Inward camt05400104Inward) {
        this.repository = repository;
        this.currencyRepository = currencyRepository;
        this.bankRepository = bankRepository;
        this.branchRepository = branchRepository;
        this.inOutMsgLogRepository = inOutMsgLogRepository;
        this.customerFndTransferRepository = customerFndTransferRepository;
        this.departmentService = departmentService;
        this.departmentAccountService = departmentAccountService;
        this.customerAccountDetailsService = customerAccountDetailsService;
        this.referenceNoGenerateService = referenceNoGenerateService;
        this.msgLogService = msgLogService;
        this.rtgsLogger = rtgsLogger;
        this.objectMapper = objectMapper;
        this.camt05400104Inward = camt05400104Inward;
    }

    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.PACS008.value();
    }

    @Override
    public void processInward(long id, Object doc) {

        log.info("----------- Pacs00800104Inward --------------");

        if (msgLogService.isExist(id)) {
            return;
        }

        Document document = (iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document) doc;

        //  for (int i = 0; i < document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().size(); ++i) {
        String errorMessage = null;
        boolean isError = false;
        InterCustomerFundTransferEntity interCustomerFndTransfer = new InterCustomerFundTransferEntity();
        CustomerFndTransferEntity customerFndTransferTxn = new CustomerFndTransferEntity();

        CreditTransferTransaction391 creditTransferTransaction7 = document.getFIToFICstmrCdtTrf().getCdtTrfTxInf();

        try {
            if (customerFndTransferRepository.existsByReferenceNumberAndSentMsgId(creditTransferTransaction7.getPmtId().getTxId(), document.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId())) {
                errorMessage = "Duplicate transaction id found " + creditTransferTransaction7.getPmtId().getTxId();

            } else {
                var optionalCurrency = currencyRepository.findByShortCodeAndIsDeletedFalse(creditTransferTransaction7.getIntrBkSttlmAmt().getCcy());
                if (!optionalCurrency.isPresent()) {
                    isError = true;
                    errorMessage = "Currency Not Found for this Code: " + creditTransferTransaction7.getIntrBkSttlmAmt().getCcy();

                } else {
                    var currency = optionalCurrency.get();
                    customerFndTransferTxn.setCurrencyId(currency.getId());
                    DepartmentEntity department = departmentService.getDeptByName("CMO-INWARD");

                    if (department == null) {
                        isError = true;
                        errorMessage = "CMO-INWARD Department Not Found ";

                    } else {
                        var departmentAcc = departmentAccountService.getDepartmentAccEntity(department.getId(), currency.getId(), RoutingType.Incoming);
                        if (Objects.nonNull(departmentAcc)) {
                            customerFndTransferTxn.setDepartmentAccountId(departmentAcc.getId());
                            customerFndTransferTxn.setTxnGlAccount(departmentAcc.getAccountNumber());
                        } else {
                            isError = true;
                            errorMessage = "CMO-INWARD Department Account Setup Not Found ";
                        }
                    }

                    Date txnDate = new Date();
                    interCustomerFndTransfer.setEventId("IPACS008");
                    if (document.getFIToFICstmrCdtTrf().getGrpHdr().getCreDtTm() != null) {
                        interCustomerFndTransfer.setCreateDate(document.getFIToFICstmrCdtTrf().getGrpHdr().getCreDtTm().toGregorianCalendar().getTime());
                    } else {
                        interCustomerFndTransfer.setCreateDate(new Date());
                    }
                    if (currency.isC2cManualTxn()) {
                        interCustomerFndTransfer.setTransactionStatus(TransactionStatus.Pending);
                        interCustomerFndTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Arrived);
                        interCustomerFndTransfer.setVerificationStatus(7);
                    } else {
                        interCustomerFndTransfer.setTransactionStatus(TransactionStatus.Pending);
                        interCustomerFndTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Approved);
                        interCustomerFndTransfer.setVerificationStatus(3);
                    }
                    var beneficiaryBranch = branchRepository.findByRoutingNumberAndIsDeletedFalse(creditTransferTransaction7.getCdtrAgt().getBrnchId().getId());
                    if (!beneficiaryBranch.isPresent()) {
                        isError = true;
                        errorMessage = "No beneficiary branch found for the routing number:" + creditTransferTransaction7.getCdtrAgt().getBrnchId().getId();

                    } else {
                        customerFndTransferTxn.setBenBranchId(beneficiaryBranch.get().getId());
                    }

                    var payerBranch = branchRepository.findByRoutingNumberAndIsDeletedFalse(creditTransferTransaction7.getDbtrAgt().getBrnchId().getId());

                    if (!payerBranch.isPresent()) {
                        isError = true;
                        errorMessage = "No payer branch found for the routing number:" + creditTransferTransaction7.getDbtrAgt().getBrnchId().getId();

                    } else {
                        customerFndTransferTxn.setPayerBranchId(payerBranch.get().getId());
                    }

                    var payerBank = bankRepository.findByBicAndIsDeletedFalse(creditTransferTransaction7.getDbtrAgt().getFinInstnId().getBICFI());
                    if (!payerBank.isPresent()) {
                        isError = true;
                        errorMessage = "No payer Bank found for " + creditTransferTransaction7.getDbtrAgt().getFinInstnId().getBICFI();
                    } else {
                        customerFndTransferTxn.setPayerBankId(payerBank.get().getId());
                    }

                    var beneficiaryBank = bankRepository.findByBicAndIsDeletedFalse(creditTransferTransaction7.getCdtrAgt().getFinInstnId().getBICFI());

                    if (!payerBank.isPresent()) {
                        isError = true;
                        errorMessage = "No beneficiary bank  found for " + creditTransferTransaction7.getCdtrAgt().getFinInstnId().getBICFI();
                    } else {
                        customerFndTransferTxn.setBenBankId(beneficiaryBank.get().getId());
                    }
                    String payerAcc = creditTransferTransaction7.getDbtrAcct().getId().getOthr().getId();
                    if (StringUtils.isBlank(payerAcc)) {
                        payerAcc = "NOT_GIVEN";
                        isError = true;
                        errorMessage = "Payer Account not given";
                        customerFndTransferTxn.setPayerAccNo(payerAcc);
                    } else {
                        customerFndTransferTxn.setPayerAccNo(creditTransferTransaction7.getDbtrAcct().getId().getOthr().getId());
                    }

                    interCustomerFndTransfer.setCreateTime(interCustomerFndTransfer.getCreateDate());
                    interCustomerFndTransfer.setMsgId(document.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId());
                    interCustomerFndTransfer.setRoutingType(RoutingType.Incoming);
                    // interCustomerFndTransfer.setTxnTypeCode((document.getFIToFICstmrCdtTrf().getCdtTrfTxInf()).getPmtTpInf().getCtgyPurp().getPrtry());
                    interCustomerFndTransfer.setTxnTypeCode(extractTTCValue(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().getPmtTpInf().getSvcLvl().get(0).getPrtry()));
                    interCustomerFndTransfer.setEntryUser("SYSTEM");
                    interCustomerFndTransfer.setCreatedAt(new Date());

                    ReferenceGenerateResponse batchNo = referenceNoGenerateService.getReferenceNo(SequenceType.IN.name());
                    interCustomerFndTransfer.setBatchNumber(batchNo.getBatchRefNo());
                    interCustomerFndTransfer = repository.save(interCustomerFndTransfer);
                    rtgsLogger.trace(String.format("Inter Customer FT : %s", objectMapper.writeValueAsString(interCustomerFndTransfer)));

                    customerFndTransferTxn.setSentMsgId(document.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId());
                    customerFndTransferTxn.setBenAccNo(creditTransferTransaction7.getCdtrAcct().getId().getOthr().getId());
                    customerFndTransferTxn.setBenName(creditTransferTransaction7.getCdtr().getNm());
                    customerFndTransferTxn.setBenAddress(creditTransferTransaction7.getCdtr().getPstlAdr().getAdrLine().get(0));
                    customerFndTransferTxn.setBenAccNoOrg(creditTransferTransaction7.getCdtrAcct().getId().getOthr().getId());
                    customerFndTransferTxn.setBenNameOrg(creditTransferTransaction7.getCdtr().getNm());
                    customerFndTransferTxn.setBenAccNo(creditTransferTransaction7.getCdtrAcct().getId().getOthr().getId());
                    customerFndTransferTxn.setPayerInsNo("");
                    customerFndTransferTxn.setPayerName(creditTransferTransaction7.getDbtr().getNm());
                    customerFndTransferTxn.setPayerAddress(creditTransferTransaction7.getDbtr().getPstlAdr().getAdrLine().get(0));

                    customerFndTransferTxn.setAmount(creditTransferTransaction7.getIntrBkSttlmAmt().getValue());
                    customerFndTransferTxn.setReferenceNumber(creditTransferTransaction7.getPmtId().getTxId());
                    customerFndTransferTxn.setEndToEndId(creditTransferTransaction7.getPmtId().getEndToEndId());
                    customerFndTransferTxn.setInstrId(creditTransferTransaction7.getPmtId().getInstrId());
                    if (creditTransferTransaction7.getIntrBkSttlmDt() != null) {
                        customerFndTransferTxn.setSettlementDate(creditTransferTransaction7.getIntrBkSttlmDt().toGregorianCalendar().getTime());
                    } else {
                        // customerFndTransferTxn.setSettlementDate(document.getFIToFICstmrCdtTrf().getGrpHdr().getIntrBkSttlmDt().toGregorianCalendar().getTime());
                        customerFndTransferTxn.setSettlementDate(document.getFIToFICstmrCdtTrf().getGrpHdr().getCreDtTm().toGregorianCalendar().getTime());
                    }

                    customerFndTransferTxn.setTransactionDate(txnDate);
                    customerFndTransferTxn.setTransactionStatus(TransactionStatus.Pending.toString());
                    customerFndTransferTxn.setVoucherNumber(null);
                    // if (!creditTransferTransaction7.getInstrForNxtAgt().isEmpty() && currency.getShortCode().equalsIgnoreCase("BDT")) {
                    if (!creditTransferTransaction7.getRmtInf().getUstrd().isEmpty() && currency.getShortCode().equalsIgnoreCase("BDT")) {
                        //  customerFndTransferTxn.setNarration((creditTransferTransaction7.getInstrForNxtAgt().get(0)).getInstrInf());
                        if (Objects.equals(extractTTCValue(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().getPmtTpInf().getSvcLvl().get(0).getPrtry()), TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {
                            String[] customDutyInfo = extractCustomDutyNarration(creditTransferTransaction7.getRmtInf().getUstrd());
                            customerFndTransferTxn.setRmtCustOfficeCode(customDutyInfo[0]);
                            customerFndTransferTxn.setRmtRegYear(Integer.parseInt(customDutyInfo[1]));
                            customerFndTransferTxn.setRmtRegNum(customDutyInfo[2]);
                            customerFndTransferTxn.setRmtDeclareCode(customDutyInfo[3]);
                            customerFndTransferTxn.setRmtCusCellNo(customDutyInfo[4]);
                        } else {
                            customerFndTransferTxn.setNarration(creditTransferTransaction7.getRmtInf().getUstrd());
                        }
                    } else {
                        for (int fc = 0; fc < creditTransferTransaction7.getInstrForNxtAgt().size(); ++fc) {
                            String instrInfo = (creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf();
                            if (instrInfo != null && (creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf().contains("Org")) {
                                customerFndTransferTxn.setFcOrgAccountType((creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf().substring(4));
                            } else if (instrInfo != null && (creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf().contains("Rec")) {
                                customerFndTransferTxn.setFcRecAccountType((creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf().substring(4));
                            } else if (instrInfo != null && (creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf().contains("Pur")) {
                                customerFndTransferTxn.setNarration((creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf().substring(4));
                            } else if (instrInfo != null && (creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf().contains("Otr")) {
                                customerFndTransferTxn.setLcNumber((creditTransferTransaction7.getInstrForNxtAgt().get(fc)).getInstrInf().substring(4));
                            } else {
                                customerFndTransferTxn.setNarration((creditTransferTransaction7.getInstrForNxtAgt().get(0)).getInstrInf());
                            }
                        }
                    }

                    String cbsName = customerAccountDetailsService.getCbsName(creditTransferTransaction7.getCdtrAcct().getId().getOthr().getId());
                    customerFndTransferTxn.setCbsName(cbsName);
                    customerFndTransferTxn.setTransactions(interCustomerFndTransfer.getId());
                    customerFndTransferTxn.setDepartmentId(department.getId());

                    customerFndTransferTxn.setRoutingType(RoutingType.Incoming);
                    customerFndTransferTxn.setParentBatchNumber(interCustomerFndTransfer.getBatchNumber());
                    customerFndTransferTxn.setPriorityCode(extractPRIValue(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().getPmtTpInf().getSvcLvl().get(0).getPrtry()));
                    //  customerFndTransferTxn.setPriorityCode((document.getFIToFICstmrCdtTrf().getCdtTrfTxInf()).getPmtTpInf().getSvcLvl().get(0).getPrtry());
                    customerFndTransferTxn.setCreatedAt(new Date());
                    if (cbsName.equals(CbsName.ABABIL.name())) {
                        customerFndTransferTxn.setAbabilReferenceNumber(batchNo.getAbabilRefNo());
                    }
                    customerFndTransferRepository.save(customerFndTransferTxn);
                    rtgsLogger.trace(String.format("Customer Transaction : %s", objectMapper.writeValueAsString(customerFndTransferTxn)));

                    if (!inOutMsgLogRepository.existsByTxnReferenceNumberAndRouteType(customerFndTransferTxn.getReferenceNumber(), RoutingType.Incoming.toString())) {
                        InOutMsgLogEntity inOutMsgLog = new InOutMsgLogEntity();
                        inOutMsgLog.setCreatedAt(new Date());
                        inOutMsgLog.setMsgId(document.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId());
                        inOutMsgLog.setMsgType(MessageDefinitionIdentifier.PACS008.value());
                        inOutMsgLog.setMsgCreationDate(new Date());
                        inOutMsgLog.setRouteType(RoutingType.Incoming.toString());
                        inOutMsgLog.setTxnReferenceNumber(customerFndTransferTxn.getReferenceNumber());
                        inOutMsgLog.setEndToEndId(customerFndTransferTxn.getEndToEndId());
                        inOutMsgLog.setInstrId(customerFndTransferTxn.getInstrId());
                        inOutMsgLog.setBatchNumber(customerFndTransferTxn.getParentBatchNumber());
                        inOutMsgLogRepository.save(inOutMsgLog);

                        rtgsLogger.trace(String.format("Transaction IN/OUT log  : %s", objectMapper.writeValueAsString(inOutMsgLog)));
                    }
                }

                try {
                    if (isError) {
                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), errorMessage);
                    } else {
                        msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                        //Check camt arrived or not in message log table with queued status in same date
                        MsgLogResponse camtMessage = msgLogService.getCamtInfoByTxnReference(customerFndTransferTxn.getReferenceNumber());
                        if (Objects.nonNull(camtMessage)) {
                            String block4 = camtMessage.getMxMessage();
                            Map<String, Object> map = XMLParser.convertStringToPDUDocument(block4);
                            camt05400104Inward.processInward(camtMessage.getId(), map.get("document"));
                        }
                    }
                } catch (Exception e) {
                    log.error("{}", e.getMessage());

                    errorMessage = errorMessage + " " + e.getMessage();
                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), id + "--" + errorMessage);
                }
            }

        } catch (Exception ex) {
            log.error("{}", ex.getMessage());
            errorMessage = errorMessage + " " + ex.getMessage();

            msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), id + "--" + errorMessage);
        }
        //  }
    }
}
