package com.cbl.cityrtgs.common.enums;

public enum OrderBy {
    ASC(1),
    DESC(2);


    private final int code;

    OrderBy(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
