package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PayerDetailsResponse {

    private String responseCode = "";

    private String responseMessage = "";

    private String payerAccNo = "";

    private String payerName = "";

    private String permanentAddress = "";

    private String payerAccId = "";

    private String availBalance = "";

    private String balance = "";

    private String status = "";

    private String schemeCode = "";

    private String schemeType = "";

    private String solId = "";

    private String acctType = "";

    private String custId = "";

    private SignatureInfoResponse signatureInfo;

    private String currencyCode = "";

    private String payerBranchName = "";
}
