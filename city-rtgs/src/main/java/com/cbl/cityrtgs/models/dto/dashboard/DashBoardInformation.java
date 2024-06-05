package com.cbl.cityrtgs.models.dto.dashboard;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class DashBoardInformation {
    private Long numberOfUnprocessedMsgs ;
    private Long pendingIncomingB2BTxns;
    private Long pendingIncomingC2CTxns;
    private Long pendingOutgoingB2BTxns;
    private Long pendingOutgoingC2CTxns;
    private Long reversedTxns;
    private Long rejectedTxns;
    private Long failedTxns;
    private Long confirmedTxns;
    private Long totalSubmittedTxns;
    private Long totalVerifiedTxns;
    private Long numberOfProcessedMsgs;
    private Long numberOfNotVerifiedUser;
}
