package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class SignatureInfo implements Serializable {

    private String signatureid = "";

    private String returnedsignature = "";

    private String signrequestid = "";

}
