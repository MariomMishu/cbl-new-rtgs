package com.cbl.cityrtgs.models.dto.transaction.c2c;

import com.cbl.cityrtgs.models.dto.transaction.ErrorDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class IbTransactionLiteResponse {

    private String requestReference;

    private String responseReference;

    private String transactionStatus;

    private Boolean isError;

    private ErrorDetail errorDetail;

}
