package com.cbl.cityrtgs.models.dto.configuration.limitprofile;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class LimitProfileResponse {
    private Long id;

    private BigDecimal creditLimit;

    private BigDecimal debitLimit;

    private BigDecimal txnVerificationLimit;

    private Long profileId;

    private String profileName;

    private Long currencyId;

    private String currencyDescription;
    private String currencyCode;
}
