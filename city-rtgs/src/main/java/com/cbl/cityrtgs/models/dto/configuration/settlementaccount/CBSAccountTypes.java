package com.cbl.cityrtgs.models.dto.configuration.settlementaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CBSAccountTypes {
    private Long id;

    private String name;

    private String description;
}
