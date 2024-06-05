package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountResponse;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.ReturnReason;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.common.utility.DateUtility;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class Pacs004GeneratorImpl implements Pacs004Generator{
    private final CurrencyService currencyService;
    private final BankService bankService;
    private final BranchService branchService;
    private final ShadowAccountService shadowAccountService;
    private final SettlementAccountService settlementAccountService;
    private final DateUtility dateUtility;

    public Document createMessage(CustomerFndTransferEntity customerFndTransferEntity) {
        return null;
    }
    public Document createMessage(InterCustomerFundTransferEntity fundTransfer, CustomerFndTransferEntity fundTransferTxn, ReturnReason errorReason, String msgId, String rtrId) {
        Document document = new Document();
        document.setPmtRtr(new PaymentReturnV04CMA());
        document.getPmtRtr().setGrpHdr(createGroupHeader(msgId));
        document.getPmtRtr().setOrgnlGrpInf(getOriginalGroupHeader(fundTransfer, fundTransferTxn, errorReason));
        document.getPmtRtr().setTxInf(getPaymentTransaction44(fundTransfer, fundTransferTxn,rtrId));
        return document;
    }

    private GroupHeader54CMA createGroupHeader(String msgId ) {
        GroupHeader54CMA groupHeader54 = new GroupHeader54CMA();
        SettlementInstruction1 settlementInstruction1 = new SettlementInstruction1();
        settlementInstruction1.setSttlmMtd(SettlementMethod1Code.CLRG);
        groupHeader54.setMsgId(msgId);
        groupHeader54.setSttlmInf(settlementInstruction1);
        groupHeader54.setNbOfTxs("1");
        groupHeader54.setCreDtTm(dateConvert(new Date()));
        return groupHeader54;
    }

    private OriginalGroupHeader2CMA getOriginalGroupHeader(InterCustomerFundTransferEntity fundTransfer, CustomerFndTransferEntity fundTransferTxn, ReturnReason returnReason) {
        OriginalGroupHeader2CMA originalGroupHeader2 = new OriginalGroupHeader2CMA();
        originalGroupHeader2.setOrgnlMsgId(fundTransfer.getMsgId());
        originalGroupHeader2.setOrgnlMsgNmId(MessageDefinitionIdentifier.PACS008.value());
        originalGroupHeader2.setOrgnlCreDtTm(dateConvert(fundTransferTxn.getCreatedAt()));
        ReturnReason5Choice returnReason5Choice = new ReturnReason5Choice();
        returnReason5Choice.setPrtry(returnReason.getCode());
        PaymentReturnReason1 paymentReturnReason1 = new PaymentReturnReason1();
        paymentReturnReason1.setRsn(returnReason5Choice);
        fundTransfer.setReturnCode(returnReason.getCode());
        if (fundTransfer.getFailedReason() != null && fundTransfer.getFailedReason().contains("Payee name mismatch")) {
            fundTransfer.setReturnReason("Payee name mismatch");
        } else if (fundTransfer.getFailedReason() != null && fundTransfer.getFailedReason().contains("Duplicate TxId")) {
            fundTransfer.setReturnReason("Duplicate TxId");
        } else {
            fundTransfer.setReturnReason(returnReason.getDescription());
        }

        List<PaymentReturnReason1> paymentReturnReason1s = new ArrayList<>();
        paymentReturnReason1s.add(paymentReturnReason1);
        originalGroupHeader2.setRtrRsnInf(new PaymentReturnReason1CMA());
        originalGroupHeader2.getRtrRsnInf().setRsn(returnReason5Choice);
        if (fundTransfer.getFailedReason() != null && fundTransfer.getFailedReason().contains("Payee name mismatch")) {
            originalGroupHeader2.getRtrRsnInf().getAddtlInf().add("Payee name mismatch");
        } else if (fundTransfer.getFailedReason() != null && fundTransfer.getFailedReason().contains("Duplicate TxId")) {
            originalGroupHeader2.getRtrRsnInf().getAddtlInf().add("Duplicate TxId");
        } else {
            originalGroupHeader2.getRtrRsnInf().getAddtlInf().add(returnReason.getDescription());
        }
        return originalGroupHeader2;
    }

    private PaymentTransaction44CMA getPaymentTransaction44(InterCustomerFundTransferEntity fundTransfer, CustomerFndTransferEntity fundTransferTxn, String rtrId) {
        ShadowAccountResponse shadowAccount = shadowAccountService.getShadowAcc(fundTransferTxn.getPayerBankId(), fundTransferTxn.getCurrencyId());
        SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(fundTransferTxn.getCurrencyId());
        CurrencyResponse currency = currencyService.getById(fundTransferTxn.getCurrencyId());
        PaymentTransaction44CMA paymentTransaction44 = new PaymentTransaction44CMA();
        paymentTransaction44.setRtrId(rtrId);
        paymentTransaction44.setOrgnlInstrId(fundTransferTxn.getInstrId());
        paymentTransaction44.setOrgnlEndToEndId(fundTransferTxn.getEndToEndId());
        paymentTransaction44.setOrgnlTxId(fundTransferTxn.getReferenceNumber());
        ActiveCurrencyAndAmount activeCurrencyAndAmount = new ActiveCurrencyAndAmount();
        activeCurrencyAndAmount.setValue(fundTransferTxn.getAmount());
        activeCurrencyAndAmount.setCcy(currency.getShortCode());
        paymentTransaction44.setRtrdIntrBkSttlmAmt(activeCurrencyAndAmount);
        paymentTransaction44.setIntrBkSttlmDt(dateUtility.getXMLdate());
        paymentTransaction44.setChrgBr(ChargeBearerType1Code.fromValue("SHAR"));
        paymentTransaction44.setInstgAgt(new PACS004INSTDAAGTBranchAndFinInstIdentif5());
        paymentTransaction44.getInstgAgt().setFinInstnId(new FinancialInstitutionIdentification8());
        paymentTransaction44.getInstgAgt().getFinInstnId().setBICFI(bankService.getEntityById(fundTransferTxn.getBenBankId()).getBic());
        paymentTransaction44.setInstdAgt(new PACS004INSTDAAGTBranchAndFinInstIdentif5());
        paymentTransaction44.getInstdAgt().setFinInstnId(new FinancialInstitutionIdentification8());
        paymentTransaction44.getInstdAgt().getFinInstnId().setBICFI(bankService.getEntityById(fundTransferTxn.getPayerBankId()).getBic());
        OriginalTransactionReference16CMA originalTransactionReference16 = new OriginalTransactionReference16CMA();
        ActiveOrHistoricCurrencyAndAmount interBankSettlementAmount = new ActiveOrHistoricCurrencyAndAmount();
        interBankSettlementAmount.setCcy(currency.getShortCode());
        interBankSettlementAmount.setValue(fundTransferTxn.getAmount());
        originalTransactionReference16.setIntrBkSttlmAmt(interBankSettlementAmount);
        originalTransactionReference16.setIntrBkSttlmDt(dateUtility.getXMLdate());
        PaymentTypeInformation25 paymentTypeInformation25 = new PaymentTypeInformation25();
        paymentTypeInformation25.setClrChanl(ClearingChannel2Code.RTGS);
        paymentTypeInformation25.setSvcLvl(new ServiceLevel8Choice());
        paymentTypeInformation25.getSvcLvl().setPrtry("0098");
        paymentTypeInformation25.setLclInstrm(new LocalInstrument2Choice());
        paymentTypeInformation25.getLclInstrm().setPrtry("RTGS_CSCT");
        paymentTypeInformation25.setCtgyPurp(new CategoryPurpose1Choice());
        if (fundTransfer != null && fundTransfer.getTxnTypeCode() != null) {
            paymentTypeInformation25.getCtgyPurp().setPrtry(fundTransfer.getTxnTypeCode());
        } else {
            paymentTypeInformation25.getCtgyPurp().setPrtry(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode());
        }
        originalTransactionReference16.setPmtTpInf(paymentTypeInformation25);
        originalTransactionReference16.setPmtMtd(PaymentMethod4Code.TRF);
        PartyIdentificationCMA1 debitor = new PartyIdentificationCMA1();
        if (fundTransferTxn.getPayerName() != null) {
            debitor.setNm(fundTransferTxn.getPayerName());
        } else {
            debitor.setNm("NOTPROVIDED");
        }
        originalTransactionReference16.setDbtr(debitor);
        CashAccount24CMA debitorAccount = new CashAccount24CMA();
        GenericAccountIdentificationCMA1 genericAccountIdentification2 = new GenericAccountIdentificationCMA1();
        if (fundTransferTxn.getPayerAccNo() != null) {
            genericAccountIdentification2.setId(fundTransferTxn.getPayerAccNo());
        } else {
            genericAccountIdentification2.setId("NOTPROVIDED");
        }

        AccountIdentification4ChoiceCMA accountIdentification4Choice1 = new AccountIdentification4ChoiceCMA();
        accountIdentification4Choice1.setOthr(genericAccountIdentification2);
        debitorAccount.setId(accountIdentification4Choice1);
        originalTransactionReference16.setDbtrAcct(debitorAccount);
        PACS004BranchAndFinancialInstitutionIdentification5 debitorAgent = new PACS004BranchAndFinancialInstitutionIdentification5();
        debitorAgent.setFinInstnId(new FinancialInstitutionIdentification8());
        debitorAgent.getFinInstnId().setBICFI(bankService.getEntityById(fundTransferTxn.getPayerBankId()).getBic());
        debitorAgent.setBrnchId(new BranchData2());
        debitorAgent.getBrnchId().setId(branchService.getBranchById(fundTransferTxn.getPayerBranchId()).getRoutingNumber());
        originalTransactionReference16.setDbtrAgt(debitorAgent);
        CashAccount24CMA debitorAgentAccount = new CashAccount24CMA();
        debitorAgentAccount.setId(new AccountIdentification4ChoiceCMA());
        debitorAgentAccount.getId().setOthr(new GenericAccountIdentificationCMA1());
        debitorAgentAccount.getId().getOthr().setId(shadowAccount.getRtgsSettlementAccount());
        originalTransactionReference16.setDbtrAgtAcct(debitorAgentAccount);
        PACS004BranchAndFinancialInstitutionIdentification5 creditorAgent = new PACS004BranchAndFinancialInstitutionIdentification5();
        creditorAgent.setFinInstnId(new FinancialInstitutionIdentification8());
        creditorAgent.getFinInstnId().setBICFI(bankService.getBankById(fundTransferTxn.getBenBankId()).getBic());
        creditorAgent.setBrnchId(new BranchData2());
        creditorAgent.getBrnchId().setId(branchService.getBranchById(fundTransferTxn.getBenBranchId()).getRoutingNumber());
        originalTransactionReference16.setCdtrAgt(creditorAgent);
        CashAccount24CMA creditorAgentAccount = new CashAccount24CMA();
        creditorAgentAccount.setId(new AccountIdentification4ChoiceCMA());
        creditorAgentAccount.getId().setOthr(new GenericAccountIdentificationCMA1());
        creditorAgentAccount.getId().getOthr().setId(settlementAccount.getCode());
        originalTransactionReference16.setCdtrAgtAcct(creditorAgentAccount);
        PartyIdentificationCMA1 creditor = new PartyIdentificationCMA1();
        creditor.setNm(fundTransferTxn.getBenName());
        originalTransactionReference16.setCdtr(creditor);
        CashAccount24CMA creditorAccount = new CashAccount24CMA();
        GenericAccountIdentificationCMA1 genericAccountIdentification1 = new GenericAccountIdentificationCMA1();
        genericAccountIdentification1.setId(fundTransferTxn.getBenAccNo());
        AccountIdentification4ChoiceCMA accountIdentification4Choice = new AccountIdentification4ChoiceCMA();
        accountIdentification4Choice.setOthr(genericAccountIdentification1);
        creditorAccount.setId(accountIdentification4Choice);
        originalTransactionReference16.setCdtrAcct(creditorAccount);
        paymentTransaction44.setOrgnlTxRef(originalTransactionReference16);
        return paymentTransaction44;
    }


    public XMLGregorianCalendar dateConvert(Date requestDate) {
        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(requestDate);
        try {
            xmlDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlDate;
    }



}
