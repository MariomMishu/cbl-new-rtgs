package com.cbl.cityrtgs.common.response.apiresponse;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class APIResponse {

    private HttpStatus status;
    private int statusCode;
    private String message;
    private Object body;
}
