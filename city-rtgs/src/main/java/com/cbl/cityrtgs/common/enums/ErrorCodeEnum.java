package com.cbl.cityrtgs.common.enums;

public enum ErrorCodeEnum {
    ERROR_AUTH("API108", "Invalid Username or Password"),
    ERROR_CURRENCY("API107", "Invalid Currency Code"),
    ERROR_AMOUNT("API504", "Invalid Amount"),
    ERROR_INSUFFICIENT("API100", "Insufficient Balance"),
    ERROR_TRANSACTION_TIME("API112", "Internet Banking Transaction Inactive!"),
    ERROR_BUSINESS_HOUR("API105", "Invalid Business Hour"),
    ERROR_DELIVERY_CHANNEL("API110", "Invalid Delivery Channel!"),
    ERROR_PAYER_ACC("API100", "Account number not found"),
    ERROR_TXN("API100", "RTGS Transaction Failed"),
    ERROR_INPUT("API104", "Input Validation Failed");

    private String code;
    private String value;

    private ErrorCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ErrorCodeEnum getResponseByCode(String code) {
        ErrorCodeEnum[] relationList = values();
        ErrorCodeEnum[] var2 = relationList;
        int var3 = relationList.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            ErrorCodeEnum nowType = var2[var4];
            if (nowType.getCode() == code) {
                return nowType;
            }
        }

        return null;
    }

    public static ErrorCodeEnum find(String value) {
        ErrorCodeEnum[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ErrorCodeEnum type = var1[var3];
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        return null;
    }

    public static String[] getArray() {
        ErrorCodeEnum[] values = values();
        String[] array = new String[values.length];

        for (int i = 0; i < values.length; ++i) {
            array[i] = values[i].getValue();
        }

        return array;
    }
}
