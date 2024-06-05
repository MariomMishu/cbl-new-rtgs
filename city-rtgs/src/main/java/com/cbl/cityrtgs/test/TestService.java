package com.cbl.cityrtgs.test;

import com.cbl.cityrtgs.common.enums.FileReadWriteEnum;
import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.filereadwrite.read.FileReadService;
import com.cbl.cityrtgs.filereadwrite.write.FileWriteService;
import com.cbl.cityrtgs.mapper.message.InwardTransactionHandlerService;
import com.cbl.cityrtgs.models.dto.response.SentSmsResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatusResponse;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.services.soap.DoAbabilSoapService;
import com.cbl.cityrtgs.services.soap.SentSmsSoapService;
import com.cbl.cityrtgs.services.transaction.CbsTransactionLogService;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestService {
    private final MxMessageLogRepository msgLogRepository;
    private final InwardDataProcessor dataProcessor;
    private final LogDataRepository logDataRepository;
    private final InwardTransactionHandlerService inwardHandle;
    private final MsgLogService msgLogService;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final CbsTransactionLogService cbsTransactionLogService;
    private final FileReadService fileReadService;
    private final FileWriteService fileWriteService;
    private final SentSmsSoapService sentSmsSoapService;
    private final DoAbabilSoapService doAbabilSoapService;


    public void processInward() throws InterruptedException {

        List<MxMessageLog> messageLogs = msgLogRepository.findAllByProcessStatusQueued();
        // List<Data> dataList = new ArrayList<>();
        // Add your data to the list...

        // Define chunk size
        //  int chunkSize = 20; // Example chunk size

        // Create an instance of DataProcessor and process data in chunks
        //dataProcessor.processDataInChunks(messageLogs, chunkSize);


        for (MxMessageLog data : messageLogs) {
            if (!data.getMxMessage().isEmpty()) {
                LogDataEntity entity = new LogDataEntity();
                entity.setMsgid(data.getBusinessMessageId());
                entity.setDatalog(data.getBusinessMessageId());
                entity.setLogtype("DATA");
                dataLogSave(entity);
                //  TimeUnit.SECONDS.sleep(10);

                String block4 = data.getMxMessage();
                inwardHandle.handleInwardBlock4Message(block4);
            }
            System.out.println("Processing data: " + data);
        }


    }

    public void processInwardFile() {

        List<MxMessageLog> messageLogs = msgLogRepository.findAllByProcessStatusQueued();

        for (MxMessageLog data : messageLogs) {
            if (!data.getMxMessage().isEmpty()) {
                String status = fileWriteService.fileWriteTest(data.getMxMessage(), data.getBusinessMessageId());
                if (status.equals("OK")) {
                    System.out.println("Processing data: " + data);
                }
            }
        }
        //Manual process call if any file read missing read file
        fileReadService.fileReadStatement(FileReadWriteEnum.MANUAL.name());

    }

    public void processInwardData(String data) {
        System.out.println("processInwardData = " + data);
        inwardHandle.handleInwardBlock4Message(data);
    }

    public void processOutward() {
        System.out.println("processInwardData = ");
    }

    public SentSmsResponse sentSms(String mobileNo) {
        String smsData = "RTGS Destination Account Credit Successful.";

        return sentSmsSoapService.sentSms(mobileNo, smsData);
    }

    public TransactionStatusResponse cbsTrxStatusCheck(String accountNo, String refNo) {

        return doAbabilSoapService.cbsTrxStatusCheck(accountNo, refNo);
    }


    public void processInwardTxnRecheck() {

        List<MxMessageLog> messageLogs = msgLogRepository.findAllByProcessingStatusQueued();

        for (MxMessageLog data : messageLogs) {
            var cbsTxnExist = cbsTransactionLogService.cbsTransactionExists(data.getMessageUserReference());
            if (cbsTxnExist) {
                if (customerFndTransferRepository.existsByReferenceNumber(data.getMessageUserReference())) {

                } else {
                    throw new RuntimeException("No Customer Transaction Found.");
                }
            } else {
                TransactionStatusResponse transactionStatusResponse = doAbabilSoapService.cbsTrxStatusCheck(data.getMxMessage(), data.getMessageUserReference());
                if (transactionStatusResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {

                } else {

                }
            }


        }


    }


    public void dataLogSave(LogDataEntity data) {
        var entity = LogDataEntity.builder()
                .id(data.getId())
                .createDate(LocalDateTime.now())
                .msgid(data.getMsgid())
                .datalog(data.getDatalog())
                .logtype(data.getLogtype())
                .build();

        logDataRepository.save(entity);
    }
}
