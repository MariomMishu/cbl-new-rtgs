package com.cbl.cityrtgs.models.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransactionStatusResponse implements Serializable {

    private String responseCode = "";

    private String responseMessage = "";

    private String tranId = "";
    private String tranAmt = "";
    private String tranParticular = "";
    private String tranParticular2 = "";
    private String tranRemarks = "";
    private String tranType = "";

}
