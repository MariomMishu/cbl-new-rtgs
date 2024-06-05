package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountResponse;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.common.utility.DateUtility;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.Document;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class Pacs009GeneratorImpl implements Pacs009Generator {
    private final BankService bankService;
    private final BranchService branchService;
    private final ShadowAccountService shadowAccountService;
    private final SettlementAccountService settlementAccountService;
    private final CurrencyService currencyService;
    private final DateUtility dateUtility;

    public Document createMessage(BankFndTransferEntity outB2BTxn) {
        Document doc = new Document();
        FinancialInstitutionCreditTransferV08 finIns = new FinancialInstitutionCreditTransferV08();
        finIns.setGrpHdr(this.createGrpHdr(outB2BTxn));
        doc.setFICdtTrf(finIns);
        doc.getFICdtTrf().setCdtTrfTxInf(this.createTransactionEntry(outB2BTxn));
        return doc;
    }

    private GroupHeader931 createGrpHdr(BankFndTransferEntity outB2B) {
        GroupHeader931 grpHdr = new GroupHeader931();

        SettlementInstruction71 settlementInstruction = new SettlementInstruction71();
        settlementInstruction.setSttlmMtd(SettlementMethod1Code1.CLRG);
        grpHdr.setMsgId(outB2B.getParentBatchNumber());
        grpHdr.setCreDtTm(DateUtility.creDtTm());
        grpHdr.setNbOfTxs("1");
        grpHdr.setSttlmInf(settlementInstruction);
        return grpHdr;
    }

    private CreditTransferTransaction361 createTransactionEntry(BankFndTransferEntity outB2BTxn) {
        CurrencyResponse currency = currencyService.getById(outB2BTxn.getCurrencyId());

        String bankBic = bankService.getBankById(outB2BTxn.getPayerBankId()).getBic();
        String branchRouting = branchService.getBranchById(outB2BTxn.getPayerBranchId()).getRoutingNumber();
        String benBankBic = bankService.getBankById(outB2BTxn.getBenBankId()).getBic();
        String benBranchRouting = branchService.getBranchById(outB2BTxn.getBenBranchId()).getRoutingNumber();
        ShadowAccountResponse shadowAccount = shadowAccountService.getShadowAcc(outB2BTxn.getBenBankId(), outB2BTxn.getCurrencyId());
        SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(outB2BTxn.getCurrencyId());
        PaymentIdentification71 paymentId = new PaymentIdentification71();
        paymentId.setTxId(outB2BTxn.getReferenceNumber());
        paymentId.setInstrId(outB2BTxn.getParentBatchNumber());
        paymentId.setEndToEndId(outB2BTxn.getParentBatchNumber());
        BranchAndFinancialInstitutionIdentification63 instgAgt = new BranchAndFinancialInstitutionIdentification63();
        instgAgt.setFinInstnId(new FinancialInstitutionIdentification183());
        instgAgt.getFinInstnId().setBICFI("CIBLBDDH");
        BranchAndFinancialInstitutionIdentification63 instdAgt = new BranchAndFinancialInstitutionIdentification63();
        instdAgt.setFinInstnId(new FinancialInstitutionIdentification183());
        instdAgt.getFinInstnId().setBICFI(benBankBic);
        RestrictedFINActiveCurrencyAndAmount amount = new RestrictedFINActiveCurrencyAndAmount();
        amount.setValue(outB2BTxn.getAmount());
        amount.setCcy(currency.getShortCode());
        BranchAndFinancialInstitutionIdentification61 identificationCdtrDbtr = new BranchAndFinancialInstitutionIdentification61();
        identificationCdtrDbtr.setFinInstnId(new FinancialInstitutionIdentification181());
        identificationCdtrDbtr.getFinInstnId().setBICFI(bankBic);
        // identificationCdtrDbtr.setBrnchId(new BranchData2());
        // identificationCdtrDbtr.getBrnchId().setId(branchRouting);
        BranchAndFinancialInstitutionIdentification61 identificationCdtr = new BranchAndFinancialInstitutionIdentification61();
        identificationCdtr.setFinInstnId(new FinancialInstitutionIdentification181());
        identificationCdtr.getFinInstnId().setBICFI(benBankBic);
        // identificationCdtr.setBrnchId(new BranchData2());
        // identificationCdtr.getBrnchId().setId(benBranchRouting);
        CashAccount381 dbtrAccount = new CashAccount381();
        dbtrAccount.setId(new AccountIdentification4Choice1());
        dbtrAccount.getId().setOthr(new GenericAccountIdentification11());
        dbtrAccount.getId().getOthr().setId(settlementAccount.getCode());
        CashAccount381 cdtrAccount = new CashAccount381();
        cdtrAccount.setId(new AccountIdentification4Choice1());
        cdtrAccount.getId().setOthr(new GenericAccountIdentification11());
        cdtrAccount.getId().getOthr().setId(shadowAccount.getRtgsSettlementAccount());
        CreditTransferTransaction361 creditTransferTransaction = new CreditTransferTransaction361();
        creditTransferTransaction.setPmtId(paymentId);
        creditTransferTransaction.setInstgAgt(instgAgt);
        creditTransferTransaction.setInstdAgt(instdAgt);

        InstructionForNextAgent11 instructionForNextAgent;
        if (currency.getShortCode().equalsIgnoreCase("BDT")) {
            creditTransferTransaction.setPmtTpInf(this.getPaymentTypeInformation(outB2BTxn));
            if (outB2BTxn.getNarration() != null && !outB2BTxn.getNarration().trim().isEmpty()) {
                instructionForNextAgent = new InstructionForNextAgent11();
                instructionForNextAgent.setInstrInf(outB2BTxn.getNarration());
                creditTransferTransaction.getInstrForNxtAgt().add(instructionForNextAgent);
            }
        } else {
            creditTransferTransaction.setPmtTpInf(this.getPaymentTypeInformation(outB2BTxn));
            if (outB2BTxn.getBillNumber() != null && !outB2BTxn.getBillNumber().trim().isEmpty()) {
                instructionForNextAgent = new InstructionForNextAgent11();
                instructionForNextAgent.setInstrInf(outB2BTxn.getBillNumber());
                creditTransferTransaction.getInstrForNxtAgt().add(instructionForNextAgent);
            }

            if (outB2BTxn.getLcNumber() != null && !outB2BTxn.getLcNumber().trim().isEmpty()) {
                instructionForNextAgent = new InstructionForNextAgent11();
                instructionForNextAgent.setInstrInf(outB2BTxn.getLcNumber());
                creditTransferTransaction.getInstrForNxtAgt().add(instructionForNextAgent);
            }

            if (outB2BTxn.getFcRecAccountType() != null && !outB2BTxn.getFcRecAccountType().trim().isEmpty()) {
                instructionForNextAgent = new InstructionForNextAgent11();
                instructionForNextAgent.setInstrInf(outB2BTxn.getFcRecAccountType());
                creditTransferTransaction.getInstrForNxtAgt().add(instructionForNextAgent);
            }

            if (benBranchRouting != null) {
                instructionForNextAgent = new InstructionForNextAgent11();
                instructionForNextAgent.setInstrInf(benBranchRouting);
                creditTransferTransaction.getInstrForNxtAgt().add(instructionForNextAgent);
            }

            if (outB2BTxn.getNarration() != null && !outB2BTxn.getNarration().trim().isEmpty()) {
                instructionForNextAgent = new InstructionForNextAgent11();
                instructionForNextAgent.setInstrInf(outB2BTxn.getNarration());
                creditTransferTransaction.getInstrForNxtAgt().add(instructionForNextAgent);
            }
        }

        creditTransferTransaction.setIntrBkSttlmAmt(amount);
        creditTransferTransaction.setIntrBkSttlmDt(dateUtility.getXMLdate());
        creditTransferTransaction.setDbtr(identificationCdtrDbtr);
        creditTransferTransaction.setCdtrAcct(cdtrAccount);
        creditTransferTransaction.setDbtrAcct(dbtrAccount);
        creditTransferTransaction.setCdtr(identificationCdtr);
        return creditTransferTransaction;
    }

    private PaymentTypeInformation281 getPaymentTypeInformation(BankFndTransferEntity outB2BTxn) {
        PaymentTypeInformation281 pmtTpInf = new PaymentTypeInformation281();
        CategoryPurpose1Choice1 categoryPurpose1Choice = new CategoryPurpose1Choice1();
        categoryPurpose1Choice.setPrtry(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode());
        LocalInstrument2Choice1 localInstrument2Choice = new LocalInstrument2Choice1();
        localInstrument2Choice.setPrtry("RTGS_FICT");
        //localInstrument2Choice.setPrtry(PACS009LclInstrm.RTGS_FICT);
        ServiceLevel8Choice1 serviceLevel8Choice = new ServiceLevel8Choice1();
        serviceLevel8Choice.setPrtry(outB2BTxn.getPriorityCode());
        pmtTpInf.setCtgyPurp(categoryPurpose1Choice);
        pmtTpInf.setLclInstrm(localInstrument2Choice);
        pmtTpInf.setInstrPrty(Priority2Code.HIGH);
//        pmtTpInf.setSvcLvl(serviceLevel8Choice);
//        pmtTpInf.setClrChanl(ClearingChannel2Code.RTGS);
        return pmtTpInf;
    }

}
