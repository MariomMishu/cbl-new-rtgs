package com.cbl.cityrtgs.models.dto.configuration.accounttype;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AccountTypeFilter {

    private AccountingType accountingType;

    private String code;

    private String cbsAccountNumber;

    private CbsName cbsName;

    private Long cbsAccountType;

}
