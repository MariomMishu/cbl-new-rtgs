package com.cbl.cityrtgs.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentSmsResponse {
    String responseCode = "";
    String responseMessage = "";
}
