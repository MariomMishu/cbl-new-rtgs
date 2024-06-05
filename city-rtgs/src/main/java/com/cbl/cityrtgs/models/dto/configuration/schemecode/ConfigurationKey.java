package com.cbl.cityrtgs.models.dto.configuration.schemecode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfigurationKey {
    VATPAYMENT_SHADOW_ACCOUNT,
    CHARGE_WAIVER_SCHEMES,
    CUSTOM_DUTY_BANK_BIC,
    CUSTOM_DUTY_BENEFICIARY_ACC,
    CUSTOM_DUTY_BENEFICIARY_NAME,
    CUSTOM_DUTY_BRANCH_ROUTING,
    SCHEDULER_INITIAL_DURATION,
    SCHEDULER_INTERVAL_DURATION,
    STANDING_INSTRUCTION_LAST_PROCESS_DATE
}
