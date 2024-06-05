package com.cbl.cityrtgs.models.dto.message;

public enum MessageDefinitionIdentifier {
    PACS002("pacs.002.001.05"),
    PACS003("pacs.003.001.04"),
    PACS004("pacs.004.001.04"),
    PACS008("pacs.008.001.08"),
    PACS009("pacs.009.001.08"),
    CAMT054("camt.054.001.08"),
    CAMT052("camt.052.001.04"),
    CAMT053("camt.053.001.04"),
    CAMT998("camt.998.001.02"),
    CAMT060("camt.060.001.03"),
    CAMT018("camt.018.001.03"),
    CAMT019("camt.019.001.03"),
    CAMT025("camt.025.001.03");
    private String _value;

    private MessageDefinitionIdentifier(String value) {
        this._value = value;
    }

    public static MessageDefinitionIdentifier fromString(String value) {
        if (PACS002.value().equals(value)) {
            return PACS002;
        } else if (PACS003.value().equals(value)) {
            return PACS003;
        } else if (PACS004.value().equals(value)) {
            return PACS004;
        } else if (PACS008.value().equals(value)) {
            return PACS008;
        } else if (PACS009.value().equals(value)) {
            return PACS009;
        } else if (CAMT054.value().equals(value)) {
            return CAMT054;
        } else if (CAMT052.value().equals(value)) {
            return CAMT052;
        } else if (CAMT053.value().equals(value)) {
            return CAMT053;
        } else if (CAMT060.value().equals(value)) {
            return CAMT060;
        } else if (CAMT018.value().equals(value)) {
            return CAMT018;
        } else if (CAMT019.value().equals(value)) {
            return CAMT019;
        } else if (CAMT025.value().equals(value)) {
            return CAMT025;
        } else {
            return CAMT998.value().equals(value) ? CAMT998 : null;
        }
    }

    public String value() {
        return this._value;
    }

    public String toString() {
        return this.value();
    }
}
