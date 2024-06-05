package com.cbl.cityrtgs.models.dto.si;

public enum Balance {

    FIXEDAMOUNT, FULLBALANCE, RESTAMOUNT;

    public String getValue(int type){

        if(type == 1){
            return FULLBALANCE.name();
        }
        else if(type == 2){
            return FULLBALANCE.name();
        }
        else if(type == 3){
            return RESTAMOUNT.name();
        }

        return null;
    }
}
