package com.cbl.cityrtgs.models.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CbsResponse implements Serializable {

    private String responseCode = "";

    private String responseMessage = "";

    private String transactionRefNumber = "";

    private String transactionDateTime = "";

    private String transactionDate = "";

    private String transactionId = "";

    private String ababilVoucher = "";

}
