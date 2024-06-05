package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.Document;


public interface Pacs009Generator extends MessageGenerator<Document, BankFndTransferEntity>{
}
