package com.cbl.cityrtgs.models.dto.configuration.bank;

import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BankRequest {
    public boolean isSattlementBank = false;
    @NotBlank(message = "Bank name can't be empty")
    private String name;
    @NotBlank(message = "BIC can't be empty")
    @Size(min = 8, max = 8, message = "Bic must be {min} characters long")
    private String bic;
    @NotBlank(message = "Address can't be empty")
    private String address1;
    private String address2;
    private String address3;
    @NotBlank(message = "Bank code can't be empty")
    @Size(min = 3, max = 3, message = "Bank code must be {min} characters long")
    private String bankCode;
    private boolean isOwnerBank;

}
