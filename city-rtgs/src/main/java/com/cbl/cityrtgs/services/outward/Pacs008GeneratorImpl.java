package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountResponse;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.common.utility.DateUtility;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class Pacs008GeneratorImpl implements Pacs008Generator {
    private final CurrencyService currencyService;
    private final BankService bankService;
    private final BranchService branchService;
    private final ShadowAccountService shadowAccountService;
    private final SettlementAccountService settlementAccountService;
    private final DateUtility dateUtility;
    private final InterCustomerFundTransferRepository interCustomerFundTransferRepository;

    public Document createMessage(CustomerFndTransferEntity OutC2CTxn) {
        InterCustomerFundTransferEntity interC2Cout = interCustomerFundTransferRepository.findAllByBatchNumberAndIsDeletedFalse(OutC2CTxn.getParentBatchNumber()).get(0);
        String pacs008Max35Text = "RTGSFIToFICustomerCredit";

        Document doc = new Document();
        FIToFICustomerCreditTransferV08 fiFiTransfer = new FIToFICustomerCreditTransferV08();
        fiFiTransfer.setGrpHdr(this.createGroupHeader(OutC2CTxn));
        fiFiTransfer.setCdtTrfTxInf(createTransactionEntry(interC2Cout, OutC2CTxn, pacs008Max35Text));
        doc.setFIToFICstmrCdtTrf(fiFiTransfer);
        // doc.getFIToFICstmrCdtTrf().getCdtTrfTxInf().add(this.createTransactionEntry(interC2Cout, OutC2CTxn, pacs008Max35Text));
        return doc;
    }

    private GroupHeader931 createGroupHeader(CustomerFndTransferEntity OutC2CTxn) {
        SettlementInstruction71 settlementInstruction = new SettlementInstruction71();
        settlementInstruction.setSttlmMtd(SettlementMethod1Code1.CLRG);
        ClearingSystemIdentification3Choice1 identification3Choice1 = new ClearingSystemIdentification3Choice1();
        identification3Choice1.setCd("RTG");
        settlementInstruction.setClrSys(identification3Choice1);
        GroupHeader931 grpHdr = new GroupHeader931();

        grpHdr.setCreDtTm(DateUtility.creDtTm());
        grpHdr.setNbOfTxs("1");
        grpHdr.setSttlmInf(settlementInstruction);
        CurrencyResponse currency = currencyService.getById(OutC2CTxn.getCurrencyId());
        ActiveOrHistoricCurrencyAndAmount ttlIntrBkSttlmAmt = new ActiveOrHistoricCurrencyAndAmount();
        ttlIntrBkSttlmAmt.setCcy(currency.getShortCode());
        ttlIntrBkSttlmAmt.setValue(OutC2CTxn.getAmount());
        grpHdr.setMsgId(OutC2CTxn.getSentMsgId());
//        grpHdr.setBtchBookg(true);
//        grpHdr.setTtlIntrBkSttlmAmt(ttlIntrBkSttlmAmt);
//        grpHdr.setIntrBkSttlmDt(dateUtility.getXMLdate());
        return grpHdr;
    }

    private CreditTransferTransaction391 createTransactionEntry(InterCustomerFundTransferEntity interC2Cout, CustomerFndTransferEntity outC2CTxn, String pacs008Max35Text) {
        CurrencyResponse currency = currencyService.getById(outC2CTxn.getCurrencyId());
        String bankBic = bankService.getBankById(outC2CTxn.getPayerBankId()).getBic();
        String branchRouting = branchService.getBranchById(outC2CTxn.getPayerBranchId()).getRoutingNumber();
        String benBankBic = bankService.getBankById(outC2CTxn.getBenBankId()).getBic();
        String benBranchRouting = branchService.getBranchById(outC2CTxn.getBenBranchId()).getRoutingNumber();
        ShadowAccountResponse shadowAccount = shadowAccountService.getShadowAcc(outC2CTxn.getBenBankId(), outC2CTxn.getCurrencyId());
        SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(outC2CTxn.getCurrencyId());

        PaymentIdentification71 paymentId = new PaymentIdentification71();
        paymentId.setTxId(outC2CTxn.getReferenceNumber());
        //paymentId.setInstrId(instrId);
        paymentId.setInstrId(outC2CTxn.getInstrId());
        paymentId.setEndToEndId(outC2CTxn.getInstrId());
        paymentId.setUETR(UUID.randomUUID().toString());
        RestrictedFINActiveCurrencyAndAmount intrBkSttlmAmt = new RestrictedFINActiveCurrencyAndAmount();
        intrBkSttlmAmt.setCcy(currency.getShortCode());
        intrBkSttlmAmt.setValue(outC2CTxn.getAmount());
        ChargeBearerType1Code chargeBearerType = ChargeBearerType1Code.fromValue("DEBT");
        PartyIdentification1352 dbtr = new PartyIdentification1352();
        dbtr.setNm(outC2CTxn.getPayerName());
        PostalAddress241 address241 = new PostalAddress241();
        address241.getAdrLine().add(outC2CTxn.getPayerAddress());
        dbtr.setPstlAdr(address241);
        CashAccount381 dbtrAcc = new CashAccount381();
        dbtrAcc.setId(new AccountIdentification4Choice1());
        dbtrAcc.getId().setOthr(new GenericAccountIdentification11());
        dbtrAcc.getId().getOthr().setId(outC2CTxn.getPayerAccNo());
        BranchAndFinancialInstitutionIdentification64 dbtrAgt = new BranchAndFinancialInstitutionIdentification64();
        dbtrAgt.setFinInstnId(new FinancialInstitutionIdentification181());
        dbtrAgt.getFinInstnId().setBICFI(bankBic);
        dbtrAgt.setBrnchId(new BranchData31());
        dbtrAgt.getBrnchId().setId(branchRouting);
        PartyIdentification1353 cdtr = new PartyIdentification1353();
        cdtr.setNm(outC2CTxn.getBenName());
        PostalAddress241 address2411 = new PostalAddress241();
        address2411.getAdrLine().add(outC2CTxn.getBenAddress());
        cdtr.setPstlAdr(address2411);
        CashAccount381 cdtrAcc = new CashAccount381();
        cdtrAcc.setId(new AccountIdentification4Choice1());
        cdtrAcc.getId().setOthr(new GenericAccountIdentification11());
        cdtrAcc.getId().getOthr().setId(outC2CTxn.getBenAccNo());
        BranchAndFinancialInstitutionIdentification64 cdtrAgt = new BranchAndFinancialInstitutionIdentification64();
        cdtrAgt.setFinInstnId(new FinancialInstitutionIdentification181());
        cdtrAgt.setBrnchId(new BranchData31());
        cdtrAgt.getFinInstnId().setBICFI(benBankBic);
        cdtrAgt.getBrnchId().setId(benBranchRouting);
        CashAccount381 dbtrAgentAccount = new CashAccount381();
        dbtrAgentAccount.setId(new AccountIdentification4Choice1());
        dbtrAgentAccount.getId().setOthr(new GenericAccountIdentification11());
        dbtrAgentAccount.getId().getOthr().setId(settlementAccount.getCode());
        CashAccount381 cdtrAgentAccount = new CashAccount381();
        cdtrAgentAccount.setId(new AccountIdentification4Choice1());
        cdtrAgentAccount.getId().setOthr(new GenericAccountIdentification11());
        if (interC2Cout.getTxnTypeCode() != null && interC2Cout.getTxnTypeCode().equals(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode())) {
            cdtrAgentAccount.getId().getOthr().setId(shadowAccount.getRtgsSettlementAccount());
        } else {
            cdtrAgentAccount.getId().getOthr().setId(shadowAccount.getRtgsSettlementAccount());
        }

        CreditTransferTransaction391 creditTxn = new CreditTransferTransaction391();
        creditTxn.setPmtId(paymentId);
        creditTxn.setIntrBkSttlmAmt(intrBkSttlmAmt);
        creditTxn.setChrgBr(chargeBearerType);
        creditTxn.setDbtr(dbtr);
        creditTxn.setDbtrAcct(dbtrAcc);
        creditTxn.setDbtrAgt(dbtrAgt);
        creditTxn.setCdtr(cdtr);
        creditTxn.setCdtrAcct(cdtrAcc);
        creditTxn.setCdtrAgt(cdtrAgt);
        creditTxn.setDbtrAgtAcct(dbtrAgentAccount);
        creditTxn.setCdtrAgtAcct(cdtrAgentAccount);
        //InstructionForNextAgent11 instructionForNextAgent3;
      /*  if (outC2CTxn.getNarration() != null && !outC2CTxn.getNarration().trim().isEmpty() && currency.getShortCode().equalsIgnoreCase("BDT")) {
            instructionForNextAgent3 = new InstructionForNextAgent11();
            instructionForNextAgent3.setInstrInf(outC2CTxn.getNarration().replaceAll("[^0-9a-zA-Z-?:().,'+]", " "));
            creditTxn.getInstrForNxtAgt().add(instructionForNextAgent3);
        } else {
            if (outC2CTxn.getFcOrgAccountType() != null && !outC2CTxn.getFcOrgAccountType().trim().isEmpty()) {
                instructionForNextAgent3 = new InstructionForNextAgent11();
                instructionForNextAgent3.setInstrInf("Org: " + outC2CTxn.getFcOrgAccountType().replaceAll("[^0-9a-zA-Z-?:().,'+]", " "));
                creditTxn.getInstrForNxtAgt().add(instructionForNextAgent3);
            }

            if (outC2CTxn.getFcRecAccountType() != null && !outC2CTxn.getFcRecAccountType().trim().isEmpty()) {
                instructionForNextAgent3 = new InstructionForNextAgent11();
                instructionForNextAgent3.setInstrInf("Rec: " + outC2CTxn.getFcRecAccountType().replaceAll("[^0-9a-zA-Z-?:().,'+]", " "));
                creditTxn.getInstrForNxtAgt().add(instructionForNextAgent3);
            }

            if (outC2CTxn.getNarration() != null && !outC2CTxn.getNarration().trim().isEmpty()) {
                instructionForNextAgent3 = new InstructionForNextAgent11();
                instructionForNextAgent3.setInstrInf("Pur: " + outC2CTxn.getNarration().replaceAll("[^0-9a-zA-Z-?:().,'+]", " "));
                creditTxn.getInstrForNxtAgt().add(instructionForNextAgent3);
            }

            if (outC2CTxn.getLcNumber() != null && !outC2CTxn.getLcNumber().trim().isEmpty()) {
                instructionForNextAgent3 = new InstructionForNextAgent11();
                instructionForNextAgent3.setInstrInf("Otr: " + outC2CTxn.getLcNumber().replaceAll("[^0-9a-zA-Z-?:().,'+]", " "));
                creditTxn.getInstrForNxtAgt().add(instructionForNextAgent3);
            }
        }*/

        BranchAndFinancialInstitutionIdentification63 instdAgt = new BranchAndFinancialInstitutionIdentification63();
        instdAgt.setFinInstnId(new FinancialInstitutionIdentification183());
        instdAgt.getFinInstnId().setBICFI(benBankBic);
        BranchAndFinancialInstitutionIdentification63 instngAgt = new BranchAndFinancialInstitutionIdentification63();
        instngAgt.setFinInstnId(new FinancialInstitutionIdentification183());
        instngAgt.getFinInstnId().setBICFI(bankBic);
        creditTxn.setPmtTpInf(this.getPaymentTypeInformation(interC2Cout, pacs008Max35Text, outC2CTxn.getPriorityCode()));
        creditTxn.setIntrBkSttlmDt(dateUtility.getXMLdate());
        creditTxn.setInstdAgt(instdAgt);
        creditTxn.setInstgAgt(instngAgt);
        RemittanceInformation161 remittanceInformation;
        if (interC2Cout.getTxnTypeCode() != null && interC2Cout.getTxnTypeCode().equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {
            remittanceInformation = new RemittanceInformation161();
            remittanceInformation.setUstrd(outC2CTxn.getRmtCustOfficeCode() + " " + outC2CTxn.getRmtRegYear() + " " + outC2CTxn.getRmtRegNum() + " " + outC2CTxn.getRmtDeclareCode() + " " + outC2CTxn.getRmtCusCellNo());
//            StructuredRemittanceInformation161 remittanceInformation161 = new StructuredRemittanceInformation161();
//            CreditorReferenceInformation21 referenceInformation21 = new CreditorReferenceInformation21();
//            referenceInformation21.setRef("");
//            CreditorReferenceType21 referenceType21 = new CreditorReferenceType21();
//            referenceType21.setIssr("");
//            CreditorReferenceType1Choice1 referenceType1Choice1 = new CreditorReferenceType1Choice1();
//            referenceType1Choice1.setPrtry("");
//            referenceType1Choice1.setCd(DocumentType3Code.valueOf(""));
//            referenceType21.setCdOrPrtry(referenceType1Choice1);
//            referenceInformation21.setTp(referenceType21);
//            remittanceInformation161.setCdtrRefInf(referenceInformation21);
            //  remittanceInformation.getStrd().add(outC2CTxn.getRmtCustOfficeCode() + " " + outC2CTxn.getRmtRegYear() + " " + outC2CTxn.getRmtRegNum());
            //   remittanceInformation.getStrd().add(outC2CTxn.getRmtDeclareCode() + " " + outC2CTxn.getRmtCusCellNo());
            creditTxn.setRmtInf(remittanceInformation);
        } else if (interC2Cout.getTxnTypeCode() != null && interC2Cout.getTxnTypeCode().equals(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode())) {
            remittanceInformation = new RemittanceInformation161();
            remittanceInformation.setUstrd(outC2CTxn.getBinCode() + " " + outC2CTxn.getCommissionerateEconomicCode());

            //   remittanceInformation.getUstrd().add(outC2CTxn.getBinCode());
            //   remittanceInformation.getUstrd().add(outC2CTxn.getCommissionerateEconomicCode());
            creditTxn.setRmtInf(remittanceInformation);
        } else {
            remittanceInformation = new RemittanceInformation161();
            remittanceInformation.setUstrd(outC2CTxn.getNarration());
            creditTxn.setRmtInf(remittanceInformation);
        }

        return creditTxn;
    }

    private PaymentTypeInformation281 getPaymentTypeInformation(InterCustomerFundTransferEntity interC2Cout, String pacs008Max35Text, String priorityCode) {
        PaymentTypeInformation281 pmtTpInf = new PaymentTypeInformation281();
        CategoryPurpose1Choice1 categoryPurpose1Choice = new CategoryPurpose1Choice1();
        categoryPurpose1Choice.setCd("CASH");
//        if (interC2Cout.getTxnTypeCode() == null) {
//            categoryPurpose1Choice.setPrtry(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode());
//        } else {
//            categoryPurpose1Choice.setPrtry(interC2Cout.getTxnTypeCode());
//        }
        LocalInstrument2Choice1 localInstrument2Choice = new LocalInstrument2Choice1();
        localInstrument2Choice.setPrtry(pacs008Max35Text);
        ServiceLevel8Choice1 serviceLevel8Choice = new ServiceLevel8Choice1();
        serviceLevel8Choice.setPrtry("TTC:" + interC2Cout.getTxnTypeCode() + ",PRI:" + priorityCode);
        pmtTpInf.setCtgyPurp(categoryPurpose1Choice);
        pmtTpInf.setLclInstrm(localInstrument2Choice);
        pmtTpInf.getSvcLvl().add(serviceLevel8Choice);
        pmtTpInf.setInstrPrty(Priority2Code.HIGH);
        return pmtTpInf;
    }

}
