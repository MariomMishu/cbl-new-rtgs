package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.services.soap.SoapConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReferenceNoGenerateService extends SoapConfig {

    @Autowired
    @PersistenceContext
    EntityManager entityManager;

    public ReferenceGenerateResponse getReferenceNo(String type) {
        ReferenceGenerateResponse response = new ReferenceGenerateResponse();
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("{call " + dbName + ".TRX_SEQUENCE_GENERATE");
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter(7, String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter(8, String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter(9, String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter(10, String.class, ParameterMode.OUT);

            query.setParameter(1, type);

            query.execute();


            int outCode = (Integer) query.getOutputParameterValue(2);
            String message = (String) query.getOutputParameterValue(3);
            String batchRefNo = (String) query.getOutputParameterValue(4);
            String txnRefNo = (String) query.getOutputParameterValue(5);
            String messageId = (String) query.getOutputParameterValue(6);
            String instrId = (String) query.getOutputParameterValue(7);
            String businessMessageId = (String) query.getOutputParameterValue(8);
            String msgNetMir = (String) query.getOutputParameterValue(9);
            String ablRefNo = (String) query.getOutputParameterValue(10);

            response.setBatchRefNo(batchRefNo);
            response.setTxnRefNo(txnRefNo);
            response.setMessageId(messageId);
            response.setInstrId(instrId);
            response.setBusinessMessageId(businessMessageId);
            response.setMsgNetMir(msgNetMir);
            response.setAbabilRefNo(ablRefNo);
            response.setResponseCode(String.valueOf(outCode));
            response.setResponseMessage(message);
        } catch (Throwable ex) {
            response.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            response.setResponseMessage(ex.getMessage());
        }


        return response;
    }

//    public ReferenceGenerateResponse newSequenceCreate() {
//        ReferenceGenerateResponse response = new ReferenceGenerateResponse();
//        try {
//            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("{call " + dbName + ".dpr_sq_reset");
//
//            query.execute();
//
//            response.setResponseCode(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode());
//            response.setResponseMessage(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getValue());
//        } catch (Throwable ex) {
//            response.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
//            response.setResponseMessage(ex.getMessage());
//        }
//
//        return response;
//    }

}
