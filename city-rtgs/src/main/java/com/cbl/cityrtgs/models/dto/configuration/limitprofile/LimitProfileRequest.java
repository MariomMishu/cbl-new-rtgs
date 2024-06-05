package com.cbl.cityrtgs.models.dto.configuration.limitprofile;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LimitProfileRequest {

    @NotNull(message = "Credit Limit can't be empty")
    private BigDecimal creditLimit;

    @NotNull(message = "Debit Limit can't be empty")
    private BigDecimal debitLimit;

    @NotNull(message = "Txn Verification Limit can't be empty")
    private BigDecimal txnVerificationLimit;

    @NotNull(message = "Profile can't be empty")
    private Long profileId;

    @NotNull(message = "Currency can't be empty")
    private Long currencyId;
}
