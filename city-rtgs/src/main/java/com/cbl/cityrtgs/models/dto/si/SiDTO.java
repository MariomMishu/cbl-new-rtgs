package com.cbl.cityrtgs.models.dto.si;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SiDTO {

    private boolean error;
    private String message;
    private Object body;
}
