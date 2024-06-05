package com.cbl.cityrtgs.engine.service;

import com.cbl.cityrtgs.common.logger.StpLogger;
import com.cbl.cityrtgs.common.constant.AppConstant;
import com.cbl.cityrtgs.models.dto.message.MessageLogDTO;
import com.cbl.cityrtgs.engine.constant.Constant;
import com.cbl.cityrtgs.engine.dto.response.STPResponse;
import com.cbl.cityrtgs.engine.dto.stpa.*;
import com.cbl.cityrtgs.engine.utility.DateUtility;
import com.cbl.cityrtgs.engine.utility.XMLUtility;
import com.cbl.cityrtgs.mapper.message.InwardTransactionHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import javax.xml.soap.*;
import java.io.IOException;
import java.net.*;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class STPServiceImpl implements STPService {

    @Value("${app.core.stp.url}")
    private String stpUrl;

    private final InwardTransactionHandlerService inwardTransactionHandlerService;
    private final StpLogger stpLogger;

    public STPResponse invokeSTPService(MessageLogDTO messageLogDTO) {

        SOAPMessage response = callSoapWebService(messageLogDTO);

        if(response != null){
            try {

                SOAPBody body = response.getSOAPPart().getEnvelope().getBody();

                log.info("TYPE: {}", body.getChildNodes().item(0).getFirstChild().getChildNodes().item(0).getTextContent());
                log.info("datetime: {}", body.getChildNodes().item(0).getFirstChild().getChildNodes().item(1).getTextContent());
                log.info("mir: {}", body.getChildNodes().item(0).getFirstChild().getChildNodes().item(2).getTextContent());
                log.info("signature: {}", body.getChildNodes().item(0).getFirstChild().getChildNodes().item(3).getTextContent());

                STPResponse stpResponse = STPResponse.builder()
                        .type(body.getChildNodes().item(0).getFirstChild().getChildNodes().item(0).getTextContent())
                        .datetime(body.getChildNodes().item(0).getFirstChild().getChildNodes().item(1).getTextContent())
                        .mir(body.getChildNodes().item(0).getFirstChild().getChildNodes().item(2).getTextContent())
                        .signature(body.getChildNodes().item(0).getFirstChild().getChildNodes().item(3).getTextContent())
                        .responseCode(AppConstant.CONNECTION_AVAILABLE)
                        .build();

                log.info("stp response : {}", stpResponse);

                return stpResponse;

            } catch (SOAPException e) {
                log.error("Error: {}", e.getMessage());
                stpLogger.error(e);

            }
        }

        return STPResponse.builder()
                .responseCode(AppConstant.CONNECTION_UNAVAILABLE)
                .build();
    }

    private SOAPMessage callSoapWebService(MessageLogDTO messageLogDTO) {

        SOAPMessage response = null;

        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage soapMessage = createSOAPRequest(messageLogDTO);
            StreamHandler streamHandler = new StreamHandler();

            URL endpoint = new URL(null, stpUrl, streamHandler);


            response = soapConnection.call(soapMessage, endpoint);
            soapConnection.close();

        } catch (SOAPException | MalformedURLException e) {
            log.error("Error: {}", e.getMessage());
            stpLogger.error(e);
        }

        return response;
    }

    private SOAPMessage createSOAPRequest(MessageLogDTO messageLogDTO) {

        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            createSoapEnvelope(soapMessage, messageLogDTO);
            soapMessage.saveChanges();
            System.out.println("Request SOAP Message:");
            soapMessage.writeTo(System.out);
            System.out.println("");

            return soapMessage;
        } catch (SOAPException | IOException e) {
            log.error("{}", e.getMessage());
            stpLogger.error(e.getMessage());
        }

        return null;
    }

    private void createSoapEnvelope(SOAPMessage soapMessage, MessageLogDTO messageLogDTO) {

        SOAPPart soapPart = soapMessage.getSOAPPart();

        String namespace = "int";
        String namespaceURI = Constant.NAMESPACE_URI;

        try {
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration(namespace, namespaceURI);

            SOAPBody body = envelope.getBody();

            SOAPElement send = body.addChildElement("send", namespace);

            SOAPElement message = send.addChildElement("message");

            SOAPElement block4 = message.addChildElement("block4");
            block4.addTextNode(messageLogDTO.getBlock4());

            SOAPElement msgPriority = message.addChildElement("msgPriority");
            msgPriority.addTextNode(messageLogDTO.getMsgPriority());

            SOAPElement msgReceiver = message.addChildElement("msgReceiver");
            msgReceiver.addTextNode(messageLogDTO.getMessageReceiver());

            SOAPElement msgSender = message.addChildElement("msgSender");
            msgSender.addTextNode(messageLogDTO.getMessageSender());

            SOAPElement msgType = message.addChildElement("msgType");
            msgType.addTextNode(messageLogDTO.getMessageType());

            SOAPElement msgUserReference = message.addChildElement("msgUserReference");
            msgUserReference.addTextNode(messageLogDTO.getMessageUserReference());

            SOAPElement format = message.addChildElement("format");
            format.addTextNode(messageLogDTO.getMessageFormat());

            SOAPElement msgNetMir = message.addChildElement("msgNetMir");
            msgNetMir.addTextNode(messageLogDTO.getMessageNetMir());

        } catch (SOAPException e) {
            log.error("SOAP Request Error: {}", e.getMessage());
        }
    }

    @Override
    public SendResponse createResponse(SendT request) {

        String message = request.getMessage().getBlock4();
        log.info("Request: {}", message);

        inwardTransactionHandlerService.handleInwardBlock4Message(message);

        Document document = XMLUtility.stringToXMLDocument(message);
        Map<String, String> XMLmap = XMLUtility.getXMLvalues(document);

        ObjectFactory factory = new ObjectFactory();
        SendResponse response = factory.createSendResponse();
        ResultT resultT = new ResultT();
        resultT.setType(AckNakType.ACK);
        resultT.setMir(XMLmap.get("mir"));
        response.setData(resultT);
        resultT.setDatetime(DateUtility.toDate());

        return response;
    }
}
