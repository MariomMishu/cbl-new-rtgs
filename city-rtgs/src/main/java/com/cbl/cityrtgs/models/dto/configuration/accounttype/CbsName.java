package com.cbl.cityrtgs.models.dto.configuration.accounttype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CbsName {
    ABABIL,
    FINACLE,
    CARD;

    public static CbsName getType(String cbsName){

        if(cbsName.equals(ABABIL.toString())){
            return ABABIL;
        }
        else if(cbsName.equals(FINACLE.toString())) {
            return FINACLE;
        }
        else{
            return CARD;
        }
    }
}
