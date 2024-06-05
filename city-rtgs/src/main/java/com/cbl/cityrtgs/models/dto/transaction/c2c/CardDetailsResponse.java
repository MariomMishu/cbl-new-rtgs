package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CardDetailsResponse {

    private String responseCode = "";

    private String responseMessage = "";

    private String clientId = "";

    private String CASAScheme = "";

    private String cardUID = "";

    private String cardHolderName = "";

    private String accountType = "";

    private String cardType = "";

    private String accountTypeUsd = "";

    private String branchCode = "";

    private String expiryDate = "";

    private String cardNoActual = "";

    private String bdtAccount = "";

    private String usdAccount = "";

    private String accountTypeBdt = "";

    private String cardContract = "";

    private String cardStatus = "";
}
