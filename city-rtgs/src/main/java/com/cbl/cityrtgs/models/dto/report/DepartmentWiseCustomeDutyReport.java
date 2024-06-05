package com.cbl.cityrtgs.models.dto.report;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DepartmentWiseCustomeDutyReport {

    String department;
    List<CustomDutyReport> customDutyReports = new ArrayList<>();

}
