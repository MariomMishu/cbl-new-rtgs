package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.services.configuration.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NarrationMappingService {

    private final BankService bankService;

    @Value("${narration.cbs.customer.incoming}")
    protected String c2cIncoming;

    @Value("${narration.cbs.customer.outgoing}")
    protected String c2cOutgoing;

    @Value("${narration.cbs.bank.incoming}")
    protected String b2bIncoming;

    @Value("${narration.cbs.bank.outgoing}")
    protected String b2bOutgoing;

    @Value("${narration.cbs.customer.reversal.incoming}")
    protected String c2cIncomingReversal;

    @Value("${narration.cbs.customer.reversal.outgoing}")
    protected String c2cOutgoingReversal;

    @Value("${narration.cbs.bank.reversal.incoming}")
    protected String b2bIncomingReversal;

    @Value("${narration.cbs.bank.reversal.outgoing}")
    protected String b2bOutgoingReversal;

    @Value("${narration.cbs.customer.return}")
    protected String c2cReturn;

    public String getNarrationByTransactionType(FundTransferType fundTransferType, RoutingType routingType, boolean isReversal, boolean isReturn) {
        String value;
        if (!isReversal) {
            value = switch (fundTransferType) {
                case CustomerToCustomer -> routingType == RoutingType.Incoming ? c2cIncoming : c2cOutgoing;
                case BankToBank -> routingType == RoutingType.Incoming ? b2bIncoming : b2bOutgoing;
            };
        } else {
            value = switch (fundTransferType) {
                case CustomerToCustomer ->
                        routingType == RoutingType.Incoming ? c2cIncomingReversal : c2cOutgoingReversal;
                case BankToBank -> routingType == RoutingType.Incoming ? b2bIncomingReversal : b2bOutgoingReversal;
            };
        }

        if(isReturn){
            if (fundTransferType == FundTransferType.CustomerToCustomer) {
                value = c2cReturn;
            }
        }

        return value;
    }
    public String getC2CTransactionNarration(CustomerFndTransferEntity c2cTxn, boolean isReversal, boolean isReturn) {
        String narration = getNarrationByTransactionType(FundTransferType.CustomerToCustomer, c2cTxn.getRoutingType(), isReversal, isReturn);
        String payerName = c2cTxn.getPayerName().trim();
        String benName = c2cTxn.getBenName().trim();

        if (narration != null) {

            narration = narration.replace("$payerName$", payerName);
            narration = narration.replace("$benName$", benName);

            log.info("narration:" + narration);
            if(narration.length() >50 ){
                narration = narration.substring(0, 49);
            }

            log.info("narration:" + narration);
            return narration;

        } else {
            if(c2cTxn.getRoutingType().equals(RoutingType.Outgoing)){
                narration = "RTGS To "+ benName;
            }else{
                narration = "RTGS From "+ payerName;
            }

            if(narration.length() >50 ){
                narration = narration.substring(0, 49);
            }
            return narration;
        }
    }

    public String getB2BTransactionNarration(BankFndTransferEntity b2bTxn, boolean isReversal, boolean isReturn) {
        BankResponse benBankResponse = bankService.getBankById(b2bTxn.getBenBankId());
        BankResponse payerBankResponse = bankService.getBankById(b2bTxn.getPayerBankId());
        String narration = getNarrationByTransactionType(FundTransferType.BankToBank, b2bTxn.getRoutingType(), isReversal,isReturn);

        String benBankBic = benBankResponse.getBic().trim();
        String payerBank = payerBankResponse.getName().trim();
        if (narration != null) {

            narration = narration.replace("$payerBank$", payerBank);
            narration = narration.replace("$benBankBic$", benBankBic);
            narration = b2bTxn.getNarration() != null ? narration+" "+b2bTxn.getNarration() : narration;
            if(narration.length() >50 ){
                narration = narration.substring(0, 49);
            }

            log.info("narration:" + narration);
            return narration;
        } else {
            if(b2bTxn.getRoutingType().equals(RoutingType.Outgoing)){
                narration = "RTGS To "+ benBankResponse.getBic().trim();
            }else{
                narration = "RTGS To "+ payerBankResponse.getName().trim();
            }

            narration = b2bTxn.getNarration() != null ? narration+" "+ b2bTxn.getNarration().trim() : narration;

            if(narration.length() >50 ){
                narration = narration.substring(0, 49);
            }

            return narration;
        }
    }
    public String getTxnRemarks(String txnRemarks) {

            if(txnRemarks.length() >50 ){
                txnRemarks = txnRemarks.substring(0, 49);
            }
            return txnRemarks;
        }

}
