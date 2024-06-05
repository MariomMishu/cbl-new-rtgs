package com.cbl.cityrtgs.common.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum TransactionTypeCodeEnum {
    ORDINARY_TRANSFER("001", "Ordinary transfer"),
    GOVERNMENT_PAYMENTS("031", "Government Payments"),
    EXCISE_AND_VAT("040", "Excise and VAT"),
    CUSTOMS_OPERATIONS("041", "Customs Operations");

    private String code;
    private String value;

    private TransactionTypeCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static TransactionTypeCodeEnum getResponseByCode(String code) {
        TransactionTypeCodeEnum[] relationList = values();
        int var3 = relationList.length;

        for (TransactionTypeCodeEnum nowType : relationList) {
            if (Objects.equals(nowType.getCode(), code)) {
                return nowType;
            }
        }

        return null;
    }

    public static TransactionTypeCodeEnum find(String value) {
        TransactionTypeCodeEnum[] var1 = values();
        int var2 = var1.length;

        for (TransactionTypeCodeEnum type : var1) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        return null;
    }

    public static String[] getArray() {
        TransactionTypeCodeEnum[] values = values();
        String[] array = new String[values.length];

        for (int i = 0; i < values.length; ++i) {
            array[i] = values[i].getValue();
        }

        return array;
    }
}
