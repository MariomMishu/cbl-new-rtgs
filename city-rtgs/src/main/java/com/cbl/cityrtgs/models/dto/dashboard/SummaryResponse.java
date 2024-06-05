package com.cbl.cityrtgs.models.dto.dashboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SummaryResponse {

    private long b2bPendingIncoming;
    private long c2cPendingIncoming;
    private long b2bPendingOutgoing;
    private long c2cPendingOutgoing;
    private long submitted;
    private long verified;
    private long confirmed;
    private long failed;
    private long reversed;
    private long rejected;
    private long unprocessedMsg;
    private long unverifiedUser;
}
