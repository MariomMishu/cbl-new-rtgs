package com.cbl.cityrtgs.models.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ReferenceGenerateResponse {
    private String responseCode = "";
    private String responseMessage = "";
    private String batchRefNo = "";
    private String ababilRefNo = "";
    private String txnRefNo = "";
    private String messageId = "";
    private String instrId = "";
    private String businessMessageId = "";
    private String msgNetMir = "";

}
