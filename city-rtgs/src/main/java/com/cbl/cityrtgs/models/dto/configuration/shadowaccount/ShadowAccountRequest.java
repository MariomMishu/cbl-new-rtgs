package com.cbl.cityrtgs.models.dto.configuration.shadowaccount;

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
public class ShadowAccountRequest {

    @NotBlank(message = "Account Number can't be empty")
    private String rtgsSettlementAccount;

    private String incomingGl;

    private String outgoingGl;

    @NotNull(message = "Bank can't be empty")
    private Long bankId;

    @NotNull(message = "Currency can't be empty")
    private Long currencyId;
}
