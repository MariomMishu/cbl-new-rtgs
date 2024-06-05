package com.cbl.cityrtgs.models.dto.configuration.bank;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BankFilter {
    private String address;
    private String bankCode;
    private String name;
    private String bic;

}
