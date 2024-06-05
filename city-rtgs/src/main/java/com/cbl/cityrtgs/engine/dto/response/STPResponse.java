package com.cbl.cityrtgs.engine.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class STPResponse {

    private String type;
    private String datetime;
    private String mir;
    private String signature;
    private String responseCode;
}
