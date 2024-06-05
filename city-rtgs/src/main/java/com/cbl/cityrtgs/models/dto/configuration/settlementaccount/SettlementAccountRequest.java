package com.cbl.cityrtgs.models.dto.configuration.settlementaccount;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SettlementAccountRequest {

    @NotBlank(message = "Account code can't be empty")
    private String code;

    @NotBlank(message = "Account name can't be empty")
    private String name;

    private String accountStatus;

    private BigDecimal balanceCCY;

    private BigDecimal balanceLCY;


    private Long accountType;

    @NotNull(message = "Bank can't be empty")
    private Long bankId;

    private Long branchId;

    @NotNull(message = "Currency can't be empty")
    private Long currencyId;
}
