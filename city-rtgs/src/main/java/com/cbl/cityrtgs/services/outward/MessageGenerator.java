package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.models.dto.message.RtgsDocument;

public interface MessageGenerator<Document extends RtgsDocument, EntityModel> {
    Document createMessage(EntityModel entityModel);
}