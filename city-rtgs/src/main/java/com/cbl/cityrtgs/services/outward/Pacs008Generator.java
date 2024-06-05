package com.cbl.cityrtgs.services.outward;


import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document;


public interface Pacs008Generator extends MessageGenerator<Document, CustomerFndTransferEntity>{
}
