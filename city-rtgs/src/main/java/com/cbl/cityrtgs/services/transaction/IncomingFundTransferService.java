package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.message.ReturnReason;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.transaction.CancelTxn;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.models.dto.transaction.ManualTxnReturn;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.dto.transaction.c2c.IncomingFundTransferResponse;
import com.cbl.cityrtgs.mapper.message.InwardTransactionHandlerService;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.configuration.CurrencyService;
import com.cbl.cityrtgs.services.user.UserInfoService;
import com.cbl.cityrtgs.services.transaction.returnTxn.ReturnFundTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class IncomingFundTransferService {
    private final CurrencyService currencyService;
    private final InterCustomerFundTransferRepository interC2CRepo;
    private final CustomerFndTransferRepository c2cTxnRepo;
    private final InterBankFundTransferRepository interB2BRepo;
    private final BankFndTransferRepository b2bxnRepo;
    private final ReturnReasonService returnReasonService;
    private final UserInfoService userInfoService;
    private final InwardTransactionHandlerService inwardHandle;
    private final ReturnFundTransferService returnFundTransferService;


    public List<IncomingFundTransferResponse> getAllIncomingPending() {
        List<IncomingFundTransferResponse> response = new ArrayList<>();
        List<IncomingFundTransferResponse> b2bList = getAllForB2B();
        List<IncomingFundTransferResponse> c2cList = getAllForC2C();
        response.addAll(b2bList);
        response.addAll(c2cList);
        return response;
    }

    public List<IncomingFundTransferResponse> getAllForB2B() {
        List<IncomingFundTransferResponse> incomingFundTransferResponses = new ArrayList<>();
        List<InterBankTransferEntity> entities = interB2BRepo.findAllByVerificationStatusInAndRoutingTypeAndIsDeletedFalse();

        entities.forEach(p -> {
            IncomingFundTransferResponse response;
            response = b2bTxnToIncomingResponse(p.getId(), p.getTransactionStatus(), p.getCreateDate());
            if (response != null) {
                incomingFundTransferResponses.add(response);
            }
        });
        return incomingFundTransferResponses;
    }

    public IncomingFundTransferResponse b2bTxnToIncomingResponse(Long id, TransactionStatus txnVerificationStatus, Date createDate) {
        Optional<BankFndTransferEntity> fndTransferEntity = b2bxnRepo.findByTransactionsAndIsDeletedFalse(id);
        IncomingFundTransferResponse domain = new IncomingFundTransferResponse();
        if (fndTransferEntity.isPresent()) {
            CurrencyEntity currency = currencyService.getEntityById(fndTransferEntity.get().getCurrencyId());
            domain.setId(fndTransferEntity.get().getTransactions());
            domain.setFundTransferType(FundTransferType.BankToBank.toString());
            domain.setStatus(txnVerificationStatus.toString());
            domain.setMessageIdentification(fndTransferEntity.get().getSentMsgId());
            domain.setCurrencyId(fndTransferEntity.get().getCurrencyId());
            domain.setCurrencyCode(currency.getShortCode());
            domain.setCreationDate(createDate.toString());
            domain.setEntryUser("SYSTEM");
        }

        return domain;
    }

    public List<IncomingFundTransferResponse> getAllForC2C() {
        List<IncomingFundTransferResponse> incomingFundTransferResponses = new ArrayList<>();
        List<InterCustomerFundTransferEntity> entities = interC2CRepo.findAllByVerificationStatusInAndRoutingTypeAndIsDeletedFalse();
        entities.forEach(p -> {
            IncomingFundTransferResponse response = c2cToIncomingResponse(p.getId(), p.getTransactionStatus(), p.getCreateDate());
            if (response != null) {
                incomingFundTransferResponses.add(response);
            }

        });
        return incomingFundTransferResponses;
    }

    public IncomingFundTransferResponse c2cToIncomingResponse(Long id, TransactionStatus txnStatus, Date createDate) {
        List<CustomerFndTransferEntity> c2cTxns = c2cTxnRepo.getAllByTransactions(id);
        IncomingFundTransferResponse domain = new IncomingFundTransferResponse();
        if (!c2cTxns.isEmpty()) {

            CurrencyEntity currency = currencyService.getEntityById(c2cTxns.get(0).getCurrencyId());
            domain.setId(c2cTxns.get(0).getTransactions());
            domain.setFundTransferType(FundTransferType.CustomerToCustomer.toString());
            domain.setStatus(txnStatus.toString());
            domain.setMessageIdentification(c2cTxns.get(0).getSentMsgId());
            domain.setCurrencyId(c2cTxns.get(0).getCurrencyId());
            domain.setCurrencyCode(currency.getShortCode());
            domain.setEntryUser("SYSTEM");
            domain.setCreationDate(createDate.toString());
        }

        return domain;
    }



    public ResponseDTO honourInwardTransaction(Long id, FundTransferType fundTransferType) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        if (fundTransferType.equals(FundTransferType.CustomerToCustomer)) {
            Optional<InterCustomerFundTransferEntity> interC2cEntityOptional = interC2CRepo.findByIdAndIsDeletedFalse(id);
            if (interC2cEntityOptional.isPresent()) {
                InterCustomerFundTransferEntity interC2cEntity = interC2cEntityOptional.get();
                List<CustomerFndTransferEntity> c2cTxnOptional =
                        c2cTxnRepo.getAllByTransactionsAndParentBatchNumber(interC2cEntity.getId(), interC2cEntity.getBatchNumber());
                if (!c2cTxnOptional.isEmpty()) {

                    CustomerFndTransferEntity c2cTxn = c2cTxnOptional.get(0);
                    c2cTxn.setDepartmentAccountId(userInfoDetails.getDept().getId());
                    c2cTxn
                            .setTransactionStatus(TransactionStatus.Pending.toString());
                    c2cTxn.setCreatedAt(new Date());
                    c2cTxn.setCreatedBy(currentUser.getId());
                    c2cTxn.setUpdatedBy(currentUser.getId());
                    c2cTxn.setUpdatedAt(new Date());

                    interC2cEntity
                            .setInwardActionStatus(0L)
                            .setVerificationStatus(0);
                    interC2cEntity.setCreatedAt(new Date());
                    interC2cEntity.setCreatedBy(currentUser.getId());
                    interC2cEntity.setUpdatedBy(currentUser.getId());
                    interC2cEntity.setUpdatedAt(new Date());
                    c2cTxnRepo.save(c2cTxn);
                    interC2CRepo.save(interC2cEntity);
                    log.info("Honour Customer To Customer Inward" + c2cTxn.getReferenceNumber());
                } else {
                    return ResponseDTO
                            .builder()
                            .error(false)
                            .message("Transaction Not Found!")
                            .build();
                }
            } else {
                return ResponseDTO
                        .builder()
                        .error(false)
                        .message("Transaction Not Found!")
                        .build();
            }

        } else {
            Optional<InterBankTransferEntity> interb2bEntityOptional = interB2BRepo.findByIdAndIsDeletedFalse(id);
            if (interb2bEntityOptional.isPresent()) {
                InterBankTransferEntity interb2bEntity = interb2bEntityOptional.get();
                Optional<BankFndTransferEntity> b2bTxnOptional =
                        b2bxnRepo.findByTransactionsAndIsDeletedFalse(interb2bEntity.getId());
                if (b2bTxnOptional.isPresent()) {
                    BankFndTransferEntity b2bTxn = b2bTxnOptional.get();
                    b2bTxn.setDepartmentAccountId(userInfoDetails.getDept().getId());
                    b2bTxn
                            .setTransactionStatus(TransactionStatus.Pending)
                            .setUpdatedBy(currentUser.getId());
                    b2bTxn.setUpdatedAt(new Date());
                    b2bTxn.setCreatedAt(new Date());
                    b2bTxn.setCreatedBy(currentUser.getId());
                    interb2bEntity
                            .setInwardActionStatus(0L)
                            .setVerificationStatus(0)
                            .setUpdatedBy(currentUser.getId());
                    interb2bEntity.setUpdatedAt(new Date());
                    interb2bEntity.setCreatedAt(new Date());
                    interb2bEntity.setCreatedBy(currentUser.getId());
                    b2bxnRepo.save(b2bTxn);
                    interB2BRepo.save(interb2bEntity);
                    log.info("Honour Bank To Bank Inward" + b2bTxn.getReferenceNumber());
                } else {
                    return ResponseDTO
                            .builder()
                            .error(false)
                            .message("Transaction Not Found!")
                            .build();
                }
            } else {
                return ResponseDTO
                        .builder()
                        .error(false)
                        .message("Transaction Not Found!")
                        .build();
            }

        }
        return ResponseDTO
                .builder()
                .error(false)
                .message("Transaction Honored Successfully")
                .build();
    }

    public ResponseDTO returnInwardTransaction(Long id, FundTransferType fundTransferType, String returnCode) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        ReturnReason returnReason = returnReasonService.getReturnReasonByCode(returnCode);
        if (fundTransferType.equals(FundTransferType.CustomerToCustomer)) {
            Optional<InterCustomerFundTransferEntity> interC2cEntityOptional = interC2CRepo.findByIdAndIsDeletedFalse(id);
            if (interC2cEntityOptional.isPresent()) {
                InterCustomerFundTransferEntity interC2cEntity = interC2cEntityOptional.get();
                List<CustomerFndTransferEntity> c2cTxnOptional =
                        c2cTxnRepo.getAllByTransactionsAndParentBatchNumber(interC2cEntity.getId(), interC2cEntity.getBatchNumber());
                if (!c2cTxnOptional.isEmpty()) {
//                Optional<CustomerFndTransferEntity> c2cTxnOptional =
//                        c2cTxnRepo.findByTransactionsAndParentBatchNumber(interC2cEntity.getId(), interC2cEntity.getBatchNumber());
//                if (c2cTxnOptional.isPresent()) {
//                    if (interC2cEntity.getInwardActionStatus() == 1 && interC2cEntity.getVerificationStatus() == 5 && interC2cEntity.getTransactionStatus().equals(TransactionStatus.Failed)) {
//                        return ResponseDTO
//                                .builder()
//                                .error(false)
//                                .message("Transaction Already Returned!")
//                                .build();
//                    }
//                    if (interC2cEntity.getInwardActionStatus() == 0 && interC2cEntity.getVerificationStatus() == 0) {
//                        return ResponseDTO
//                                .builder()
//                                .error(false)
//                                .message("Transaction Already Honored!")
//                                .build();
//                    }
                    CustomerFndTransferEntity c2cTxn = c2cTxnOptional.get(0);
                    c2cTxn.setDepartmentAccountId(userInfoDetails.getDept().getId());
                    c2cTxn
                            .setId(c2cTxn.getId())
                            .setTransactionStatus(TransactionStatus.Failed.toString())
                            .setReturnCode(returnCode)
                            .setReturnReason(returnReason.getDescription())
                            .setReturnUser(currentUser.getUsername())
                            .setReturnDateTime(new Date());
                    c2cTxn.setCreatedAt(new Date());
                    c2cTxn.setCreatedBy(currentUser.getId());
                    c2cTxn.setUpdatedBy(currentUser.getId());
                    c2cTxn.setUpdatedAt(new Date());
                    interC2cEntity
                            .setInwardActionStatus(1L)
                            .setVerificationStatus(5)
                            .setTransactionStatus(TransactionStatus.Failed)
                            .setReturnReason(returnReason.getDescription())
                            .setReturnCode(returnCode)
                            .setReturnUser(currentUser.getUsername())
                            .setReturnDateTime(new Date())
                            .setId(id);
                    interC2cEntity.setEntryUser(currentUser.getUsername());
                    interC2cEntity.setCreatedAt(new Date());
                    interC2cEntity.setCreatedBy(currentUser.getId());
                    interC2cEntity.setUpdatedBy(currentUser.getId());
                    interC2cEntity.setUpdatedAt(new Date());
                    c2cTxnRepo.save(c2cTxn);
                    interC2CRepo.save(interC2cEntity);
                    log.info("Return Customer To Customer Inward" + c2cTxn.getReferenceNumber());
                } else {
                    return ResponseDTO
                            .builder()
                            .error(false)
                            .message("Transaction Not Found!")
                            .build();
                }
            } else {
                return ResponseDTO
                        .builder()
                        .error(false)
                        .message("Transaction Not Found!")
                        .build();
            }

        } else {
            Optional<InterBankTransferEntity> interb2bEntityOptional = interB2BRepo.findByIdAndIsDeletedFalse(id);
            if (interb2bEntityOptional.isPresent()) {
                InterBankTransferEntity interb2bEntity = interb2bEntityOptional.get();
                Optional<BankFndTransferEntity> b2bTxnOptional =
                        b2bxnRepo.findByTransactionsAndIsDeletedFalse(interb2bEntity.getId());
                if (b2bTxnOptional.isPresent()) {
                    BankFndTransferEntity b2bTxn = b2bTxnOptional.get();
//                    if (interb2bEntity.getInwardActionStatus() == 1 && interb2bEntity.getVerificationStatus() == 5 && interb2bEntity.getTransactionStatus().equals(TransactionStatus.Failed)) {
//                        return ResponseDTO
//                                .builder()
//                                .error(false)
//                                .message("Transaction Already Returned!")
//                                .build();
//                    }
//                    if (interb2bEntity.getInwardActionStatus() == 0 && interb2bEntity.getVerificationStatus() == 0) {
//                        return ResponseDTO
//                                .builder()
//                                .error(false)
//                                .message("Transaction Already Honored!")
//                                .build();
//                    }
                    b2bTxn.setDepartmentAccountId(userInfoDetails.getDept().getId());
                    b2bTxn
                            .setId(b2bTxn.getId())
                            .setTransactionStatus(TransactionStatus.Failed)
                            .setReturnCode(returnCode)
                            .setReturnReason(returnReason.getDescription())
                            .setReturnUser(currentUser.getUsername())
                            .setReturnDateTime(new Date());
                    b2bTxn.setCreatedAt(new Date());
                    b2bTxn.setCreatedBy(currentUser.getId());

                    interb2bEntity

                            .setInwardActionStatus(1L)
                            .setVerificationStatus(5)
                            .setTransactionStatus(TransactionStatus.Failed)
                            .setReturnReason(returnReason.getDescription())
                            .setReturnCode(returnCode)
                            .setReturnUser(currentUser.getUsername())
                            .setReturnDateTime(new Date())
                            .setId(id);
                    interb2bEntity.setEntryUser(currentUser.getUsername());
                    interb2bEntity.setCreatedAt(new Date());
                    interb2bEntity.setCreatedBy(currentUser.getId());
                    b2bTxn.setCreatedAt(new Date());
                    b2bTxn.setCreatedBy(currentUser.getId());
                    b2bxnRepo.save(b2bTxn);
                    interB2BRepo.save(interb2bEntity);
                    log.info("Return Bank To Bank Inward" + b2bTxn.getReferenceNumber());
                } else {
                    return ResponseDTO
                            .builder()
                            .error(false)
                            .message("Transaction Not Found!")
                            .build();
                }
            } else {
                return ResponseDTO
                        .builder()
                        .error(false)
                        .message("Transaction Not Found!")
                        .build();
            }
        }
        return ResponseDTO
                .builder()
                .error(true)
                .message("Transaction Returned Successfully!")
                .build();
    }

    public List<IncomingFundTransferResponse> getAllIncomingRejected() {
        List<IncomingFundTransferResponse> response = new ArrayList<>();
        List<IncomingFundTransferResponse> b2bList = this.getAllForB2BRejected();
        List<IncomingFundTransferResponse> c2cList = this.getAllForC2CRejected();
        response.addAll(b2bList);
        response.addAll(c2cList);
        return response;
    }

    public List<IncomingFundTransferResponse> getAllForB2BRejected() {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        List<IncomingFundTransferResponse> incomingFundTransferResponses = new ArrayList<>();
        List<InterBankTransferEntity> entities = interB2BRepo.findAllByVerificationStatusAndRoutingType();
        entities.forEach(p -> {
            if (p.getCreatedBy() == currentUser.getId()) {
                IncomingFundTransferResponse response;
                response = this.b2bTxnToIncomingResponse(p.getId(), p.getTransactionStatus(), p.getCreateDate());
                incomingFundTransferResponses.add(response);
            }

        });
        return incomingFundTransferResponses;
    }

    public List<IncomingFundTransferResponse> getAllForC2CRejected() {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        List<IncomingFundTransferResponse> incomingFundTransferResponses = new ArrayList<>();
        List<InterCustomerFundTransferEntity> entities = interC2CRepo.findAllByVerificationStatusAndRoutingType();
        entities.forEach(p -> {
            if (p.getCreatedBy() == currentUser.getId()) {
                IncomingFundTransferResponse response;
                response = this.c2cToIncomingResponse(p.getId(), p.getTransactionStatus(), p.getCreateDate());
                incomingFundTransferResponses.add(response);
            }

        });
        return incomingFundTransferResponses;
    }

    public ResponseDTO sendTransactionReturn(ManualTxnReturn manualTxnReturn) {
        ResponseDTO response = new ResponseDTO();
        Optional<InterCustomerFundTransferEntity> optionalEntity = interC2CRepo.findByBatchNumberAndRoutingTypeAndIsDeletedFalse(manualTxnReturn.getParentBatchNumber(), RoutingType.Incoming);
        if (optionalEntity.isPresent()) {
            Optional<CustomerFndTransferEntity> optionalTxn = c2cTxnRepo.findByReferenceNumberAndIsDeletedFalse(manualTxnReturn.getReferenceNumber());
            if (optionalTxn.isPresent()) {
                CustomerFndTransferEntity c2cTxn = optionalTxn.get();
                response = returnFundTransferService.sendPaymentReturnMessage(c2cTxn, null);
            }
        }
        return ResponseDTO.builder().error(response.isError()).message(response.getMessage()).build();
    }


    public ResponseDTO cancelTransaction(CancelTxn cancelTxn) {
        if (cancelTxn.getFundTransferType().equals(FundTransferType.CustomerToCustomer.toString())) {
            Optional<InterCustomerFundTransferEntity> optionalEntity = interC2CRepo.findByBatchNumberAndRoutingTypeAndIsDeletedFalse(cancelTxn.getParentBatchNumber(), RoutingType.Incoming);
            if (optionalEntity.isPresent()) {
                InterCustomerFundTransferEntity entity = optionalEntity.get();
                entity.setTransactionStatus(TransactionStatus.Cancelled);
                Optional<CustomerFndTransferEntity> optionalTxn = c2cTxnRepo.findByReferenceNumberAndIsDeletedFalse(cancelTxn.getReferenceNumber());
                if (optionalTxn.isPresent()) {
                    CustomerFndTransferEntity c2cTxn = optionalTxn.get();
                    c2cTxn.setTransactionStatus(TransactionStatus.Cancelled.toString());
                    try {
                        c2cTxnRepo.save(c2cTxn);
                        interC2CRepo.save(entity);
                    } catch (Exception e) {
                        return ResponseDTO.builder().error(true).message(e.getMessage()).build();
                    }
                } else {
                    return ResponseDTO.builder().error(true).message("Transaction Not Found!").build();
                }
            } else {
                return ResponseDTO.builder().error(true).message("Transaction Not Found!").build();
            }
        }
        if (cancelTxn.getFundTransferType().equals(FundTransferType.BankToBank.toString())) {
            Optional<InterBankTransferEntity> optionalEntity = interB2BRepo.findByBatchNumberAndRoutingTypeAndIsDeletedFalse(cancelTxn.getParentBatchNumber(), RoutingType.Incoming);
            if (optionalEntity.isPresent()) {
                InterBankTransferEntity entity = optionalEntity.get();
                entity.setTransactionStatus(TransactionStatus.Cancelled);
                Optional<BankFndTransferEntity> optionalTxn = b2bxnRepo.findByReferenceNumberAndIsDeletedFalse(cancelTxn.getReferenceNumber());
                if (optionalTxn.isPresent()) {
                    BankFndTransferEntity b2bTxn = optionalTxn.get();
                    b2bTxn.setTransactionStatus(TransactionStatus.Cancelled);
                    try {
                        b2bxnRepo.save(b2bTxn);
                        interB2BRepo.save(entity);
                    } catch (Exception e) {
                        return ResponseDTO.builder().error(true).message(e.getMessage()).build();
                    }

                } else {
                    return ResponseDTO.builder().error(true).message("Transaction Not Found!").build();
                }
            } else {
                return ResponseDTO.builder().error(true).message("Transaction Not Found!").build();
            }
        }
        return ResponseDTO.builder().error(false).message("Cancel Request Sent Successfully!").build();
    }

    public void handleInward(String requestMessage) {
        String message;
        try {
            message = "<DataPDU xmlns=\"urn:swift:saa:xsd:saa.2.0\">\n" +
                    "<Revision>2.0.5</Revision>\n" +
                    "<Body>\n" +
                    "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.01\">\n" +
                    "<Fr><FIId><FinInstnId><BICFI>BBHOBDDH</BICFI></FinInstnId></FIId></Fr>\n" +
                    "<To><FIId><FinInstnId><BICFI>CIBLBDDH</BICFI></FinInstnId></FIId></To>\n" +
                    "<BizMsgIdr>240321BBHOBDDHARTG0001293418</BizMsgIdr>\n" +
                    "<MsgDefIdr>pacs.009.001.04</MsgDefIdr>\n" +
                    "<BizSvc>RTGS_GSMT</BizSvc>\n" +
                    "<CreDt>2024-03-21T03:53:35Z</CreDt>\n" +
                    "</AppHdr>\n" +
                    "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.04\"><FICdtTrf><GrpHdr><MsgId>BBHO032112383</MsgId><CreDtTm>2024-03-21T09:52:02Z</CreDtTm><NbOfTxs>1</NbOfTxs><SttlmInf><SttlmMtd>CLRG</SttlmMtd></SttlmInf></GrpHdr><CdtTrfTxInf><PmtId><InstrId>2383</InstrId><EndToEndId>NOTPROVIDED</EndToEndId><TxId>BBHO032112383</TxId></PmtId><PmtTpInf><ClrChanl>RTGS</ClrChanl><SvcLvl><Prtry>0010</Prtry></SvcLvl><LclInstrm><Prtry>RTGS_FICT</Prtry></LclInstrm><CtgyPurp><Prtry>013</Prtry></CtgyPurp></PmtTpInf><IntrBkSttlmAmt Ccy=\"BDT\">3000000.0</IntrBkSttlmAmt><IntrBkSttlmDt>2024-03-21</IntrBkSttlmDt><Dbtr><FinInstnId><BICFI>BBHOBDDH</BICFI></FinInstnId><BrnchId><Id>025271119</Id></BrnchId></Dbtr><DbtrAcct><Id><Othr><Id>GL1001USD</Id></Othr></Id></DbtrAcct><Cdtr><FinInstnId><BICFI>CIBLBDDH</BICFI></FinInstnId><BrnchId><Id>225270381</Id></BrnchId></Cdtr><CdtrAcct><Id><Othr><Id>30001708021</Id></Othr></Id></CdtrAcct></CdtTrfTxInf></FICdtTrf></Document>\n" +
                    "</Body>\n" +
                    "</DataPDU>";

            inwardHandle.handleInwardBlock4Message(requestMessage);
        } catch (Exception e) {
            System.err.println("Error processing file : " + e.getMessage());

            throw new RuntimeException(e.getMessage());
        }

    }
}
