package com.cbl.cityrtgs.models.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ManualTxnReturn {
    @NotBlank(message = "Reference Number can't be empty")
    private String referenceNumber;
    @NotBlank(message = "Batch Number can't be empty")
    private String parentBatchNumber;
}
