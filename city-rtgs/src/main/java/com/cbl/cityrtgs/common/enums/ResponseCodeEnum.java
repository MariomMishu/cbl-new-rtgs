package com.cbl.cityrtgs.common.enums;

import lombok.Getter;

@Getter
public enum ResponseCodeEnum {
    ERROR_RESPONSE_CODE("000", "Response Code Error"),
    ERROR_TIMEOUT_RESPONSE_CODE("801", "Response Code Error"),

    SUCCESS_RESPONSE_CODE("100", "Response Code Success"),
    REVERSE_SUCCESS_RESPONSE_CODE("301", "Response Code Reverse Success"),
    REVERSE_FAIL_RESPONSE_CODE("302", "Response Code Reverse Fail");

    private String code;
    private String value;

    private ResponseCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ResponseCodeEnum getResponseByCode(String code) {
        ResponseCodeEnum[] relationList = values();
        ResponseCodeEnum[] var2 = relationList;
        int var3 = relationList.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            ResponseCodeEnum nowType = var2[var4];
            if (nowType.getCode() == code) {
                return nowType;
            }
        }

        return null;
    }

    public static ResponseCodeEnum find(String value) {
        ResponseCodeEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResponseCodeEnum type = var1[var3];
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        return null;
    }

    public static String[] getArray() {
        ResponseCodeEnum[] values = values();
        String[] array = new String[values.length];

        for (int i = 0; i < values.length; ++i) {
            array[i] = values[i].getValue();
        }

        return array;
    }
}
