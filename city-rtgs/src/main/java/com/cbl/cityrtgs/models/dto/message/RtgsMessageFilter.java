package com.cbl.cityrtgs.models.dto.message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RtgsMessageFilter {

    private String messageDirections;

    private String processStatus;

    private String messageType;

    private String messageUserReference;

    private String anyString;

    private String msgDate;

}
