package com.cbl.cityrtgs.models.dto.configuration.bank;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class BankResponse {
    private Long id;

    private String name;

    private String bic;

    private String address1;

    private String address2;

    private String address3;

    private String bankCode;

    private boolean isOwnerBank;
    private boolean isSattlementBank;

    //private boolean active;
}
