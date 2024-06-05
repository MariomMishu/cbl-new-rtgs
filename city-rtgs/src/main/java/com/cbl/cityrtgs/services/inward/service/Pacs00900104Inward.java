package com.cbl.cityrtgs.services.inward.service;

import com.cbl.cityrtgs.common.enums.SequenceType;
import com.cbl.cityrtgs.common.logger.RtgsLogger;
import com.cbl.cityrtgs.common.utility.XMLParser;
import com.cbl.cityrtgs.models.dto.message.MsgLogResponse;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionVerificationStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.entitymodels.configuration.*;
import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.repositories.configuration.*;
import com.cbl.cityrtgs.repositories.message.InOutMsgLogRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
import com.cbl.cityrtgs.services.transaction.ReferenceNoGenerateService;
import com.cbl.cityrtgs.test.LogDataEntity;
import com.cbl.cityrtgs.test.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class Pacs00900104Inward implements Inward {
    private final InterBankFundTransferRepository repository;
    private final BankFndTransferRepository bankFndTransferRepository;
    private final CurrencyService currencyService;
    private final BankRepository bankRepository;
    private final InOutMsgLogRepository inOutMsgLogRepository;
    private final ReferenceNoGenerateService referenceNoGenerateService;
    private final DepartmentService departmentService;
    private final DepartmentAccountService departmentAccountService;
    private final ShadowAccountRepository shadowAccountRepository;
    private final MsgLogService msgLogService;
    private final TxnCfgSetupRepository txnCfgSetupRepository;
    private final RtgsLogger rtgsLogger;
    private final ObjectMapper objectMapper;
    private final AccountTypesRepository accountTypesRepository;
    private final Camt05400104Inward camt05400104Inward;
    private final CurrencyRepository currencyRepository;
    private final SettlementAccountRepository settlementAccountRepository;
    private final BranchRepository branchRepository;
    private final BankService bankService;
    private final TestService testService;


    @Autowired
    public Pacs00900104Inward(InterBankFundTransferRepository repository,
                              BankFndTransferRepository bankFndTransferRepository,
                              CurrencyService currencyService,
                              BankRepository bankRepository,
                              InOutMsgLogRepository inOutMsgLogRepository,
                              ReferenceNoGenerateService referenceNoGenerateService,
                              DepartmentService departmentService,
                              DepartmentAccountService departmentAccountService,
                              ShadowAccountRepository shadowAccountRepository,
                              MsgLogService msgLogService,
                              TxnCfgSetupRepository txnCfgSetupRepository,
                              RtgsLogger rtgsLogger,
                              ObjectMapper objectMapper,
                              AccountTypesRepository accountTypesRepository,
                              Camt05400104Inward camt05400104Inward,
                              CurrencyRepository currencyRepository,
                              SettlementAccountRepository settlementAccountRepository,
                              BranchRepository branchRepository,
                              BankService bankService, TestService testService) {
        this.repository = repository;
        this.currencyService = currencyService;
        this.bankRepository = bankRepository;
        this.inOutMsgLogRepository = inOutMsgLogRepository;
        this.shadowAccountRepository = shadowAccountRepository;
        this.branchRepository = branchRepository;
        this.bankFndTransferRepository = bankFndTransferRepository;
        this.referenceNoGenerateService = referenceNoGenerateService;
        this.departmentService = departmentService;
        this.departmentAccountService = departmentAccountService;
        this.msgLogService = msgLogService;
        this.txnCfgSetupRepository = txnCfgSetupRepository;
        this.rtgsLogger = rtgsLogger;
        this.objectMapper = objectMapper;
        this.accountTypesRepository = accountTypesRepository;
        this.camt05400104Inward = camt05400104Inward;
        this.currencyRepository = currencyRepository;
        this.settlementAccountRepository = settlementAccountRepository;
        this.bankService = bankService;
        this.testService = testService;
    }

    @Override
    public String getServiceType() {
        return MessageDefinitionIdentifier.PACS009.value();
    }

    @Override
    public void processInward(long id, Object doc) {

        log.info("----------- Pacs00900104Inward --------------");

        Document document = (iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document) doc;
        InterBankTransferEntity fundTransfer = new InterBankTransferEntity();
        BankFndTransferEntity txn = new BankFndTransferEntity();
        CurrencyEntity currency;
        String errorMessage = null;
        boolean isError = false;
        Date date = new Date();
        PACS009LclInstrm localInstrument = document.getFICdtTrf().getCdtTrfTxInf().getPmtTpInf().getLclInstrm().getPrtry();
        CreditTransferTransaction8 isoTxn = document.getFICdtTrf().getCdtTrfTxInf();
        LogDataEntity entity = new LogDataEntity();
        entity.setMsgid(document.getFICdtTrf().getGrpHdr().getMsgId());
        entity.setDatalog(document.getFICdtTrf().getGrpHdr().getMsgId());
        entity.setLogtype("PACS9");
        testService.dataLogSave(entity);
        try {
            fundTransfer.setEventId("IPACS009");
            GroupHeader49 grpHeader = document.getFICdtTrf().getGrpHdr();
            fundTransfer.setMsgId(document.getFICdtTrf().getGrpHdr().getMsgId());
            fundTransfer.setCreateDate(grpHeader.getCreDtTm().toGregorianCalendar().getTime());
            fundTransfer.setCreateTime(fundTransfer.getCreateDate());
            fundTransfer.setBankToBankType(localInstrument.name());
            fundTransfer.setTxnTypeCode(document.getFICdtTrf().getCdtTrfTxInf().getPmtTpInf().getCtgyPurp().getPrtry());

            txn.setSentMsgId(document.getFICdtTrf().getGrpHdr().getMsgId());
            txn.setReferenceNumber(isoTxn.getPmtId().getTxId());
            txn.setEndToEndId(isoTxn.getPmtId().getEndToEndId());
            txn.setInstrId(isoTxn.getPmtId().getInstrId());
            txn.setAmount(isoTxn.getIntrBkSttlmAmt().getValue());
            txn.setPriorityCode(isoTxn.getPmtTpInf().getSvcLvl().getPrtry());

            var beneficiaryBranch = branchRepository.findByRoutingNumberAndIsDeletedFalse(isoTxn.getCdtr().getBrnchId().getId());
            var payerBranch = branchRepository.findByRoutingNumberAndIsDeletedFalse(isoTxn.getDbtr().getBrnchId().getId());
            var payerBank = bankRepository.findByBicAndIsDeletedFalse(isoTxn.getDbtr().getFinInstnId().getBICFI());
            var beneficiaryBank = bankRepository.findByBicAndIsDeletedFalse(isoTxn.getCdtr().getFinInstnId().getBICFI());
            var optCurrency = currencyRepository.findByShortCodeAndIsDeletedFalse(isoTxn.getIntrBkSttlmAmt().getCcy());
            if (optCurrency.isEmpty()) {
                isError = true;
                errorMessage = "Currency Code Not Found for this Code: " + isoTxn.getIntrBkSttlmAmt().getCcy();
            } else {
                currency = optCurrency.get();
                txn.setCurrencyId(currency.getId());
                DepartmentEntity department = departmentService.getDeptByName("TREASURY");
                DepartmentAccountEntity departmentAcc = departmentAccountService.getDepartmentAccEntity(department.getId(), currency.getId(), RoutingType.Incoming);
                if (payerBank.isEmpty()) {
                    isError = true;
                    errorMessage = "Payer Bank was not found forBIC:" + isoTxn.getDbtr().getFinInstnId().getBICFI();
                } else {
                    txn.setPayerBankId(payerBank.get().getId());
                    txn.setPayerName(payerBank.get().getName());
                    var shadowAcc = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(payerBank.get().getId(), currency.getId());
                    if (shadowAcc.isPresent()) {
                        txn.setPayerAccNo(isoTxn.getDbtrAcct().getId().getOthr().getId());
                        txn.setPayerInsNo("");
                        txn.setPayerCbsAccNo(shadowAcc.get().getIncomingGl());
                        txn.setPayerAccNo(shadowAcc.get().getRtgsSettlementAccount());
                    } else {
                        isError = true;
                        errorMessage = "Shadow Not Found.";
                    }
                }
                if (departmentAcc == null) {
                    isError = true;
                    errorMessage = "Department Account not found.";
                } else {
                    txn.setDepartmentId(department.getId());
                    txn.setDepartmentAccountId(departmentAcc.getId());

                    var settlementAcc = settlementAccountRepository.findByCurrencyIdAndIsDeletedFalse(currency.getId());
                    if (payerBank.isPresent()) {
                        if (settlementAcc.isPresent() && payerBank.get().isSettlementBank()) {
                            var _accType = accountTypesRepository.findByRtgsAccountIdAndCbsNameAndIsDeletedFalse(settlementAcc.get().getId(), CbsName.FINACLE);
                            if (_accType.isPresent()) {
                                var accType = _accType.get();
                                txn.setTransactionGlAccount(accType.getCbsAccountNumber());
                            } else {
                                txn.setTransactionGlAccount(departmentAcc.getAccountNumber());
                            }
                        } else {
                            txn.setTransactionGlAccount(departmentAcc.getAccountNumber());
                        }
                    } else {
                        isError = true;
                        errorMessage = "Payer Bank was not found for BIC: " + isoTxn.getDbtr().getFinInstnId().getBICFI();
                    }

                }
                if (beneficiaryBank.isEmpty()) {
                    isError = true;
                    errorMessage = "Beneficiary bank was not found for BIC: " + isoTxn.getCdtr().getFinInstnId().getBICFI();
                } else {
                    txn.setBenBankId(beneficiaryBank.get().getId());
                }
                if (beneficiaryBranch.isEmpty()) {
                    isError = true;
                    errorMessage = "Beneficiary branch was not found for routing number: " + isoTxn.getCdtr().getBrnchId().getId();
                } else {
                    txn.setBenBranchId(beneficiaryBranch.get().getId());
                }
                txn.setBenAccNo(isoTxn.getCdtrAcct().getId().getOthr().getId());
                txn.setBenName(isoTxn.getCdtr().getFinInstnId().getBICFI());
                txn.setBenAccNoOrg(isoTxn.getCdtrAcct().getId().getOthr().getId());

                if (currency.isB2bManualTxn()) {
                    fundTransfer.setTransactionStatus(TransactionStatus.Pending);
                    fundTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Arrived);
                    fundTransfer.setVerificationStatus(7);
                } else {
                    fundTransfer.setTransactionStatus(TransactionStatus.Pending);
                    fundTransfer.setTxnVerificationStatus(TransactionVerificationStatus.Approved);
                    fundTransfer.setVerificationStatus(3);
                }

                if (payerBranch.isEmpty()) {
                    isError = true;
                    errorMessage = "Payer branch was not found for routing number: " + isoTxn.getCdtr().getBrnchId().getId();
                } else {
                    txn.setPayerBranchId(payerBranch.get().getId());
                }
                txn.setTransactionStatus(TransactionStatus.Pending);
                txn.setRoutingType(RoutingType.Incoming);
                txn.setSettlementDate(isoTxn.getIntrBkSttlmDt().toGregorianCalendar().getTime());
                txn.setTransactionDate(date);

                if (isoTxn.getInstrForNxtAgt() != null && localInstrument.name().trim().equalsIgnoreCase(PACS009LclInstrm.RTGS_FICT.name()) && isoTxn.getInstrForNxtAgt().size() > 0 && currency.getShortCode().equalsIgnoreCase("BDT")) {
                    txn.setNarration((isoTxn.getInstrForNxtAgt().get(0)).getInstrInf());
                } else {
                    List<InstructionForNextAgent1> instructions;
                    if (localInstrument.name().trim().equalsIgnoreCase(PACS009LclInstrm.RTGS_FICT.name()) && isoTxn.getInstrForNxtAgt().size() > 0) {
                        instructions = isoTxn.getInstrForNxtAgt();
                        if (instructions.size() == 5) {
                            txn.setBillNumber(instructions.get(0).getInstrInf());
                            txn.setLcNumber(instructions.get(1).getInstrInf());
                            txn.setFcRecAccountType(instructions.get(2).getInstrInf());
                            var fcBeneficiaryBranch = branchRepository.findByRoutingNumberAndIsDeletedFalse(instructions.get(3).getInstrInf());
                            if (fcBeneficiaryBranch.isPresent()) {
                                txn.setFcRecBranchId(fcBeneficiaryBranch.get().getId());
                            } else {
                            }
                            txn.setNarration(instructions.get(4).getInstrInf());
                        } else {
                            txn.setNarration((isoTxn.getInstrForNxtAgt().get(0)).getInstrInf());
                        }
                    } else if (isoTxn.getInstrForNxtAgt() != null && localInstrument.name().trim().equalsIgnoreCase(PACS009LclInstrm.RTGS_RETN.name())) {
                        instructions = isoTxn.getInstrForNxtAgt();
                        if (instructions != null && instructions.size() == 6) {
                            String reasonCode = instructions.get(1).getInstrInf().replaceAll("/", "");
                            String orgInsId = instructions.get(2).getInstrInf().replaceAll("/MREF/", "");
                            String orgTxnRef = instructions.get(3).getInstrInf().replaceAll("/TREF/", "");
                            String charge = instructions.get(4).getInstrInf().replaceAll("/CHGS/", "");
                            String narration = instructions.get(5).getInstrInf().replaceAll("/TEXT/", "");
                            fundTransfer.setOriginalBatchNumber(orgInsId);
                            fundTransfer.setReturnReason(reasonCode);
                            fundTransfer.setReturnCharge(new BigDecimal(charge));
                            txn.setNarration(narration);
                            txn.setOriginalTransactionReference(orgTxnRef);
                        }
                    } else {
                        txn.setNarration("Narration not found from payer bank.");
                    }
                }

                if (isoTxn.getCdtrAcct() != null) {
                    txn.setCdtrAccount(isoTxn.getCdtrAcct().getId().getOthr().getId());
                }

                if (isoTxn.getDbtrAcct() != null) {
                    txn.setDbtrAccount(isoTxn.getDbtrAcct().getId().getOthr().getId());
                }
                if (!isError) {
                    fundTransfer.setTransactionDate(date);
                    fundTransfer.setBranchId(beneficiaryBranch.get().getId());
                    fundTransfer.setRoutingType(RoutingType.Incoming);
                    ReferenceGenerateResponse batchNo = referenceNoGenerateService.getReferenceNo(SequenceType.IN.name());
                    fundTransfer.setBatchNumber(batchNo.getBatchRefNo());
                    if (!repository.existsByBatchNumber(fundTransfer.getBatchNumber())) {
                        if (!bankFndTransferRepository.existsByReferenceNumber(txn.getReferenceNumber())) {
                            fundTransfer.setCreatedAt(new Date());
                            fundTransfer.setEntryUser("SYSTEM");
                            fundTransfer = repository.save(fundTransfer);
                            rtgsLogger.trace(String.format("Inter Bank FT : %s", objectMapper.writeValueAsString(fundTransfer)));
                            txn.setTransactions(fundTransfer.getId());
                            txn.setParentBatchNumber(fundTransfer.getBatchNumber());
                            txn.setCreatedAt(new Date());
                            txn = bankFndTransferRepository.save(txn);
                            rtgsLogger.trace(String.format(" Bank Transaction : %s", objectMapper.writeValueAsString(txn)));
                        }
                    }
                }
                if (!inOutMsgLogRepository.existsByTxnReferenceNumberAndRouteType(txn.getReferenceNumber(), RoutingType.Incoming.toString())) {
                    InOutMsgLogEntity inOutMsgLog = new InOutMsgLogEntity();
                    inOutMsgLog.setCreatedAt(new Date());
                    inOutMsgLog.setMsgId(document.getFICdtTrf().getGrpHdr().getMsgId());
                    inOutMsgLog.setMsgType(MessageDefinitionIdentifier.PACS009.value());
                    inOutMsgLog.setMsgCreationDate(new Date());
                    inOutMsgLog.setRouteType(RoutingType.Incoming.toString());
                    inOutMsgLog.setTxnReferenceNumber(txn.getReferenceNumber());
                    inOutMsgLog.setInstrId(txn.getInstrId());
                    inOutMsgLog.setEndToEndId(txn.getEndToEndId());
                    inOutMsgLog.setBatchNumber(txn.getParentBatchNumber());
                    inOutMsgLogRepository.save(inOutMsgLog);
                    rtgsLogger.trace(String.format(" Bank Transaction IN/OUT log: %s", objectMapper.writeValueAsString(inOutMsgLog)));
                }
            }

            try {
                if (isError) {
                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), errorMessage);
                } else {
                    this.doTransactionActive(document);
                    msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.PROCESSED), "");
                    //Check camt arrived or not in message log table with queued status in same date
                    MsgLogResponse camtMessage = msgLogService.getCamtInfoByTxnReference(txn.getReferenceNumber());
                    if (Objects.nonNull(camtMessage)) {
                        String block4 = camtMessage.getMxMessage();
                        Map<String, Object> map = XMLParser.convertStringToPDUDocument(block4);
                        camt05400104Inward.processInward(camtMessage.getId(), map.get("document"));
                    }
                }
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), errorMessage);
            }
        } catch (Exception e) {
            msgLogService.updateRtgsMsgLog(id, String.valueOf(MessageProcessStatus.UNPROCESSED), e.getMessage());
        }
    }

    public void doTransactionActive(Document document) {
        String dbtrBank = document.getFICdtTrf().getCdtTrfTxInf().getDbtr().getFinInstnId().getBICFI();

        BankEntity bankEntity = bankService.getByBicAndSettlementBankIsTrue(dbtrBank);

        if (bankEntity != null) {
            String ccy = document.getFICdtTrf().getCdtTrfTxInf().getIntrBkSttlmAmt().getCcy();
            CurrencyResponse currency = currencyService.getByCurrencyShortCode(ccy);

            if (currency != null) {

                long currencyId = currency.getId();
                Optional<TxnCfgSetupEntity> optionalTxnCfgSetupEntity = txnCfgSetupRepository.findByCurrencyIdAndIsDeletedFalse(currencyId);

                if (optionalTxnCfgSetupEntity.isPresent()) {

                    TxnCfgSetupEntity txnCfgSetupEntity = optionalTxnCfgSetupEntity.get();

                    if (!txnCfgSetupEntity.getTxnActive()) {
                        txnCfgSetupEntity.setCurrencyId(currencyId);
                        txnCfgSetupEntity.setTxnActive(true);
                        txnCfgSetupRepository.save(txnCfgSetupEntity);
                    }
                }
            }
        }
    }
}
