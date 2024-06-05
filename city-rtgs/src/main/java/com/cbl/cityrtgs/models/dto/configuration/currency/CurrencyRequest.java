package com.cbl.cityrtgs.models.dto.configuration.currency;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CurrencyRequest {

    @NotBlank(message = "Currency code can't be empty")
    @Size(min = 3, max = 3, message = "Currency code must be {min} characters long")
    private String shortCode;

    @NotBlank(message = "Description can't be empty")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal b2bMinAmount;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal c2cMinAmount;

    private boolean b2bManualTxn;

    private boolean c2cManualTxn;

}
