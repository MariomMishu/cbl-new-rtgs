package com.cbl.cityrtgs.models.dto.configuration.uiappconfiguration;

import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UiAppConfigurationResponse extends BaseEntity {

    private Long id;

    private int accNumberMaxLength;

    private int accNumberMinLength;

    private int csvFileMaxItem;

    private Boolean batchOutEnabled;

    private Boolean disableUi;

    private Boolean validateBalance;

    private Boolean crossCurrencySupport;

    private Boolean rtgsBalanceValidate;

}
