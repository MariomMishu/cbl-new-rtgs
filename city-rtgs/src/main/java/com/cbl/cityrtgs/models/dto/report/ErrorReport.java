package com.cbl.cityrtgs.models.dto.report;

public interface ErrorReport {
    String getErrorDate();

    String getMsgType();

    String getOriginalMsgId();

    String getPacs004MsgId();

    String getErrorCause();

}
