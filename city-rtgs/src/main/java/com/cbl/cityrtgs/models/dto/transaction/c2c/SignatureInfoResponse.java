package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class SignatureInfoResponse {
    private String responseCode="";
    private String responseMessage="";
    private List<SignatureInfo> signatureList;
}
