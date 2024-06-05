package com.cbl.cityrtgs.models.dto.transaction;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionResponse {

    private boolean error;
    private String message;
    private Object body;
}
