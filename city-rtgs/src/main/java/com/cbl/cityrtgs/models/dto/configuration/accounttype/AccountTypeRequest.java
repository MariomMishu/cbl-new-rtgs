package com.cbl.cityrtgs.models.dto.configuration.accounttype;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AccountTypeRequest {

    @NotNull(message = "Account type can't be empty")
    private AccountingType accountingType;

    @NotBlank(message = "Account type code can't be empty")
    private String code;

    @NotBlank(message = "Cbs account number can't be empty")
    private String cbsAccountNumber;

    private String contraAccNumber;

    @NotNull(message = "CBS can't be empty")
    private CbsName cbsName;

    @NotNull(message = "CBS account type can't be empty")
    private Long cbsAccountType;

    @NotNull(message = "Settlement account can't be empty")
    private Long settlementAccountId;

    private Boolean cbsManaged;
}
