package com.cbl.cityrtgs.models.dto.configuration.department;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DepartmentFilter {

    private String name;

}
