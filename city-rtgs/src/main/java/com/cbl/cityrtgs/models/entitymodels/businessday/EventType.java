package com.cbl.cityrtgs.models.entitymodels.businessday;

public enum EventType {
    SYSTEMSTART("System Start"),
    ADJUSTMENTWITHGL("Adjustment of balances with GL"),
    EXPERIODMORNING("Exchange period Morning"),
    EXPERIODAFTERNOON("Exchange period Afternoon"),
    STMTRPRTPARTICIPANTS("Statement Reports (Participants)"),
    RETURNINTERBANKFUND("Return and Interbannk Fund Transfer"),
    ILFFULLRETURN("ILF Full Return"),
    ILFPARTIALRETURN("ILF Partial Return"),
    PAYMENTCANCELLATION("Payment cancellation"),
    STATEMENTREPORTS("Statement Reports (Participants)"),
    STATEMENTREPORTSGL("Statement Reports (GL)"),
    ARCHIVING("Archiving"),
    SYSTEMSTOP("System Stop");
    private String _value;

    private EventType(String value) {
        this._value = value;
    }

    public String value() {
        return this._value;
    }

    public String toString() {
        return this.value();
    }
}
