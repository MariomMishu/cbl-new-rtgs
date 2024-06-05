package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class IncomingFundTransferResponse {

    private Long id;

    private Long currencyId;

    private String currencyCode;

    private String status;

    private String creationDate;

    private String messageIdentification;

    private String fundTransferType;

    private String entryUser;

}
