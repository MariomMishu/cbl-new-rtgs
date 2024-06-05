package com.cbl.cityrtgs.services.outward;


import com.cbl.cityrtgs.models.dto.message.ReturnReason;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document;

public interface Pacs004Generator extends MessageGenerator<Document, CustomerFndTransferEntity>{
    Document createMessage(InterCustomerFundTransferEntity fundTransfer, CustomerFndTransferEntity fundTransferTxn, ReturnReason errorReason, String msgId, String rtrId);
}
