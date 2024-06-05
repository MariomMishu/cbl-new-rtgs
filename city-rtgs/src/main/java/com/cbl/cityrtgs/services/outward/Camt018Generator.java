package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.models.dto.businessinfo.BusinessDayInfoRequest;
import iso20022.swift.xsd.camt_018_001.Document;

public interface Camt018Generator extends MessageGenerator<Document, BusinessDayInfoRequest>{
}
