package com.cbl.cityrtgs.models.dto.configuration.accounttype;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class AccountTypeResponse {
    private Long id;

    private AccountingType accountingType;

    private String code;

    private String cbsAccountNumber;

    private String contraAccNumber;

    private CbsName cbsName;

    private Long cbsAccountType;

    private String cbsAccTypeName;

    private Long settlementAccountId;

    private Boolean cbsManaged;
}
