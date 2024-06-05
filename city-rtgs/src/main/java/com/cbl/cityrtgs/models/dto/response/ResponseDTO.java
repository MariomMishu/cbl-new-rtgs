package com.cbl.cityrtgs.models.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseDTO {

    private boolean error;
    private String message;
    private Object body;
}
