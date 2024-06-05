package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.schemecode.ConfigurationKey;
import com.cbl.cityrtgs.models.dto.configuration.schemecode.SchemeCodeResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CardDetailsResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.PayerDetailsResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.SignatureInfoResponse;
import com.cbl.cityrtgs.services.configuration.ExemptionChargeSetupService;
import com.cbl.cityrtgs.services.configuration.SchemeCodeService;
import com.cbl.cityrtgs.services.soap.AccountDetailsSoapService;
import com.cbl.cityrtgs.services.soap.CardDetailsSoapService;
import com.cbl.cityrtgs.services.soap.SignatureInfoSoapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerAccountDetailsService {
    private final AccountDetailsSoapService accountDetailsSoapService;
    private final ExemptionChargeSetupService exemptionChargeSetupService;
    private final SchemeCodeService schemeCodeService;
    private final SignatureInfoSoapService signatureInfoSoapService;
    private final CardDetailsSoapService cardDetailsSoapService;

    public PayerDetailsResponse getAccountDetails(String accountNumber) {
        return accountDetailsSoapService.getAccountDetailsFromSoap(accountNumber);
    }


    public String getCbsName(String accountNumber) {
        if (accountNumber.length() > 14 && (accountNumber.length() == 15 || accountNumber.length() == 16)) {
            return CbsName.CARD.toString();
        } else {
            return accountNumber.startsWith("178") ? CbsName.ABABIL.toString() : CbsName.FINACLE.toString();
        }
    }

    public boolean getChargeEnabled(String payerAccNo, String customerSchemeCode) {
        boolean isChargeEnable = true;

        SchemeCodeResponse schemeCodeResponse = schemeCodeService.getByConfigKey(ConfigurationKey.CHARGE_WAIVER_SCHEMES);
        if (StringUtils.isNotBlank(customerSchemeCode)) {
            String[] configSchemeCodes = schemeCodeResponse.getSchemeCodeValue().split(","); // {"SBSTF", "CBFT", "TT", "TEST"};
            for (String schemeCode : configSchemeCodes) {
                if (schemeCode.trim().equals(customerSchemeCode)) {
                    isChargeEnable = false;
                }
            }
        }
        if (exemptionChargeSetupService.existByAccountNo(payerAccNo)) {
            isChargeEnable = false;
        }
        return isChargeEnable;
    }


    public CardDetailsResponse getCardDetails(String cardNumber) {
        return cardDetailsSoapService.getCardDetailsFromSoap(cardNumber);
    }

    public SignatureInfoResponse getPayerSignatureDetailsByAccountNumber(String accountNumber) {
        SignatureInfoResponse signatureInfo = new SignatureInfoResponse();
        if (getCbsName(accountNumber).equals(CbsName.ABABIL.toString())) {
            signatureInfo = signatureInfoSoapService.getAbabilSignatureInfo(accountNumber);
        } else {
            PayerDetailsResponse response = accountDetailsSoapService.getAccountDetailsFromSoap(accountNumber);
            if (response.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                signatureInfo = signatureInfoSoapService.getFinacleSignatureInfo(accountNumber, response.getCustId());

            } else {
                signatureInfo.setResponseCode(response.getResponseCode());
                signatureInfo.setResponseMessage(response.getResponseMessage());
            }
        }

        return signatureInfo;
    }

}
