package com.cbl.cityrtgs.models.dto.configuration.settlementaccount;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SettlementAccountFilter {
    String code;
    String name;

}
