package com.cbl.cityrtgs.services.soap;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.transaction.CbsResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionRequest;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoAbabilSoapService extends SoapConfig {

    public CbsResponse doAbabilNGTransaction(TransactionRequest transactionRequest, String crAcc) {
        CbsResponse outModel = new CbsResponse();
//        var requestDateTime= "2023-02-27T10:55:56.699";
        // String requestId = "CITYRTGS" + System.currentTimeMillis()/60/10;
        //  String requestId = "CITYRTGS" + System.currentTimeMillis()/60/10;
        String requestId = transactionRequest.getAbabilRequestId();
        String input = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://ababil.ababilNG/xsd\">\n"
                + " <soapenv:Header/>\n"
                + " <soapenv:Body>\n"
                + " <axis:doAbabilNGTransaction>\n"
                + " <!--Optional:-->\n"
                + " <axis:request>\n"
                + " <xsd:chargeAccountNumber>" + transactionRequest.getChargeAccount() + "</xsd:chargeAccountNumber>\n"
                + " <xsd:chargeAmount>" + transactionRequest.getCharge().doubleValue() + "</xsd:chargeAmount>\n"
                + " <xsd:chargeDebitNarration>RTGS Service Charge</xsd:chargeDebitNarration>\n"
                + " <xsd:chargeName>RTGS Service Charge</xsd:chargeName>\n"
                + " <xsd:currencyCode>" + transactionRequest.getCurrencyCode() + "</xsd:currencyCode>\n"
                + " <xsd:fromAccountNumber>" + transactionRequest.getDrAccount() + "</xsd:fromAccountNumber>\n"
                + " <xsd:narration>" + transactionRequest.getNarration() + "</xsd:narration>\n"
                + " <xsd:password>" + apiUsername + "</xsd:password>\n"
                + " <xsd:referenceNumber>" + transactionRequest.getRtgsRefNo() + "</xsd:referenceNumber>\n"
                + " <xsd:requestDateTime>" + getTransactionDateTime() + "</xsd:requestDateTime>\n"
                + " <xsd:requestId>" + requestId + "</xsd:requestId>\n"
                + " <xsd:toAccountNumber>" + crAcc + "</xsd:toAccountNumber>\n"
                + " <xsd:transactionAmount>" + transactionRequest.getAmount() + "</xsd:transactionAmount>\n"
                + " <xsd:username>" + apiUsername + "</xsd:username>\n"
                + " <xsd:vatAccountNumber>" + transactionRequest.getVatAccount() + "</xsd:vatAccountNumber>\n"
                + " <xsd:vatAmount>" + transactionRequest.getVat().doubleValue() + "</xsd:vatAmount>\n"
                + " </axis:request>\n"
                + " </axis:doAbabilNGTransaction>\n"
                + " </soapenv:Body>\n"
                + " </soapenv:Envelope>";
        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "doAbabilNGTransaction");
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            log.info("ABABIL Request: {}", input);

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);

                String responseXML = sb.toString();

                String XMLResponseCode = tagXmlData(responseXML, "responseCode");
                String XMLResponseMessage = tagXmlData(responseXML, "responseMessage");
                String transactionDateTime;
                String transactionRefNumber;
                String voucherNumber;

                if (XMLResponseCode.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    transactionDateTime = tagXmlData(responseXML, "transactionDateTime");
                    transactionRefNumber = tagXmlData(responseXML, "transactionRefNumber");
                    voucherNumber = tagXmlData(responseXML, "voucherNumber");

                    outModel.setResponseCode(XMLResponseCode);
                    outModel.setResponseMessage(XMLResponseMessage);
                    outModel.setAbabilVoucher(voucherNumber);
                    outModel.setTransactionRefNumber(transactionRefNumber);
                    outModel.setTransactionDateTime(transactionDateTime);
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    if (StringUtils.isNotBlank(responseXML)) {
                        outModel.setResponseMessage(XMLResponseMessage);
                    } else {
                        outModel.setResponseMessage("No response found from Ababil.");
                    }
                }
                log.info("doAbabilNGTransaction response body is {}", XMLResponseMessage);

            }
            br.close();
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
            outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            outModel.setResponseMessage(e.getMessage());

        }
        return outModel;
    }

    private String tagXmlData(String xml, String fieldName) {
        String value = "";
        String regexExpression = String.format("<ax\\d+:%s>(.*?)<\\/ax\\d+:%s>", fieldName, fieldName);
        Pattern ptn = Pattern.compile(regexExpression);
        Matcher matcher = ptn.matcher(xml);
        if (matcher.find()) {
            value = matcher.group(1);
        }
        return value;
    }

    public CbsResponse doAbabilNGBatchChargeVatTxn(TransactionRequest transactionRequest, String crAcc) {
        //  Map<String, String> result = new HashMap<>();
        CbsResponse outModel = new CbsResponse();
        String input = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://ababil.ababilNG/xsd\">\n" + "   <soapenv:Header/>\n" + "   <soapenv:Body>\n" + "      <axis:doAbabilNGTransaction>\n" + "         <!--Optional:-->\n" + "         <axis:request>\n" + "               <xsd:chargeAccountNumber>" + transactionRequest.getChargeAccount() + "</xsd:chargeAccountNumber>\n" + "               <xsd:chargeAmount>" + transactionRequest.getCharge() + "</xsd:chargeAmount>\n" + "               <xsd:chargeDebitNarration>" + transactionRequest.getNarration() + "</xsd:chargeDebitNarration>\n" + "               <xsd:chargeName>RTGS Service Charge</xsd:chargeName>\n" + "               <xsd:currencyCode>" + transactionRequest.getCurrencyCode() + "</xsd:currencyCode>\n" + "               <xsd:fromAccountNumber>" + transactionRequest.getDrAccount() + "</xsd:fromAccountNumber>\n" + "               <xsd:narration>" + transactionRequest.getNarration() + "</xsd:narration>\n" + "               <xsd:password>" + apiPassword + "</xsd:password>\n" + "               <xsd:referenceNumber>" + transactionRequest.getRtgsRefNo() + "</xsd:referenceNumber>\n" + "               <xsd:requestDateTime>" + getTransactionDateTime() + "</xsd:requestDateTime>\n" + "               <xsd:requestId>" + transactionRequest.getRtgsRefNo() + "</xsd:requestId>\n" + "               <xsd:toAccountNumber>" + crAcc + "</xsd:toAccountNumber>\n" + "               <xsd:transactionAmount>0.00</xsd:transactionAmount>\n" + "               <xsd:username>" + apiUsername + "</xsd:username>\n" + "               <xsd:vatAccountNumber>" + transactionRequest.getVatAccount() + "</xsd:vatAccountNumber>\n" + "               <xsd:vatAmount>" + transactionRequest.getVat() + "</xsd:vatAmount>\n" + "         </axis:request>\n" + "      </axis:doAbabilNGTransaction>\n" + "   </soapenv:Body>\n" + "</soapenv:Envelope>\n";

        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "doAbabilNGTransaction");

            log.info("doAbabilNGTransaction request body is {}", input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
                String responseXML = sb.toString();

                String XMLResponseCode = tagXmlData(responseXML, "responseCode");
                String XMLResponseMessage = tagXmlData(responseXML, "responseMessage");
                String transactionDateTime;
                String transactionRefNumber;
                String voucherNumber;

                if (XMLResponseCode.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    transactionDateTime = tagXmlData(responseXML, "transactionDateTime");
                    transactionRefNumber = tagXmlData(responseXML, "transactionRefNumber");
                    voucherNumber = tagXmlData(responseXML, "voucherNumber");
                    outModel.setResponseCode(XMLResponseCode);
                    outModel.setResponseMessage(XMLResponseMessage);
                    outModel.setTransactionRefNumber(transactionRefNumber);
                    outModel.setTransactionDateTime(transactionDateTime);
                    outModel.setAbabilVoucher(voucherNumber);
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(XMLResponseMessage);
                }


                log.info("doAbabilNGTransaction response body is {}", responseXML);

            }
            br.close();
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
            outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            outModel.setResponseMessage(e.getMessage());

        }
        return outModel;
    }

    public CbsResponse doAbabilNGReverseTxn(String orgnlRef, String ababilRequestId) {
        CbsResponse outModel = new CbsResponse();
        String reverseRequest = "RR" + ababilRequestId;

        String input = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://ababil.ababilNG/xsd\">\n" + "<soapenv:Header/>\n" + "<soapenv:Body>\n" + "<axis:doAbabilNGTransactionReversal>\n" + "<!--Optional:-->\n" + "<axis:request>\n" + "<!--Optional:-->\n" + "<xsd:originalTransactionReferenceNo>" + orgnlRef + "</xsd:originalTransactionReferenceNo>\n" + "<!--Optional:-->\n" + "<xsd:originalTransactionRequestId>" + ababilRequestId + "</xsd:originalTransactionRequestId>\n" + "<!--Optional:-->\n" + "<xsd:password>" + apiPassword + "</xsd:password>\n" + "<!--Optional:-->\n" + "<xsd:referenceNumber>" + orgnlRef + "</xsd:referenceNumber>\n" + "<!--Optional:-->\n" + "<xsd:remark>?</xsd:remark>\n" + "<!--Optional:-->\n" + "<xsd:requestDateTime>" + getTransactionDateTime() + "</xsd:requestDateTime>\n" + "<!--Optional:-->\n" + "<xsd:requestId>" + reverseRequest + "</xsd:requestId>\n" + "<!--Optional:-->\n" + "<xsd:username>" + apiUsername + "</xsd:username>\n" + "</axis:request>\n" + "</axis:doAbabilNGTransactionReversal>\n" + "</soapenv:Body>\n" + "</soapenv:Envelope>\n";
        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "doAbabilNGTransactionReversal");

            log.info("doAbabilNGTransactionReversal request body is {}", input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            String responseCode;
            String responseMessage;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                output = removeXmlStringNamespaceAndPreamble(output);
                sb.append(output);

                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource src = new InputSource();
                src.setCharacterStream(new StringReader(output));

                Document doc = builder.parse(src);

                responseCode = doc.getElementsByTagName("responseCode").item(0).getTextContent();
                responseMessage = doc.getElementsByTagName("responseMessage").item(0).getTextContent();

                if (orgnlRef.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    outModel.setResponseCode(responseCode);
                    outModel.setResponseMessage(responseMessage);
                    outModel.setTransactionRefNumber(doc.getElementsByTagName("transactionRefNumber").item(0).getTextContent());
                    outModel.setTransactionId(doc.getElementsByTagName("transactionRefNumber").item(0).getTextContent());

                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(responseMessage);
                }
                log.info("doAbabilNGTransactionReversal response body is {}", doc);
            }
            br.close();
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
            outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            outModel.setResponseMessage(e.getMessage());
        }
        return outModel;
    }

    public CbsResponse doAbabilNGTransactionInward(TransactionRequest transactionRequest, String crAcc, String drAcc) {
        CbsResponse outModel = new CbsResponse();

        String requestId = transactionRequest.getAbabilRequestId();
        String input = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://ababil.ababilNG/xsd\">\n" + "   <soapenv:Header/>\n" + "   <soapenv:Body>\n" + "      <axis:doAbabilNGTransaction>\n" + "         <!--Optional:-->\n" + "         <axis:request>\n" + "           <xsd:chargeAccountNumber>" + drAcc + "</xsd:chargeAccountNumber>\n" + "                                                <xsd:chargeAmount>0.0</xsd:chargeAmount>\n" + "                                                <xsd:chargeDebitNarration>RTGS Service Charge</xsd:chargeDebitNarration>\n" + "                                                <xsd:chargeName>RTGS Service Charge</xsd:chargeName>\n" + "                                                <xsd:currencyCode>" + transactionRequest.getCurrencyCode() + "</xsd:currencyCode>\n" + "                                                <xsd:fromAccountNumber>" + drAcc + "</xsd:fromAccountNumber>\n" + "                                                <xsd:narration>" + transactionRequest.getNarration() + "</xsd:narration>\n" + "                                                <xsd:password>" + apiPassword + "</xsd:password>\n" +
                //  "                                                <xsd:referenceNumber>" + transactionRequest.getRtgsRefNo() + "</xsd:referenceNumber>\n" +
                "                                                <xsd:referenceNumber>" + requestId + "</xsd:referenceNumber>\n" + "                                                <xsd:requestDateTime>" + getTransactionDateTime() + "</xsd:requestDateTime>\n" + "                                                <xsd:requestId>" + requestId + "</xsd:requestId>\n" + "                                                <xsd:toAccountNumber>" + crAcc + "</xsd:toAccountNumber>\n" + "                                                <xsd:transactionAmount>" + transactionRequest.getAmount() + "</xsd:transactionAmount>\n" + "                                                <xsd:username>" + apiUsername + "</xsd:username>\n" + "                                                <xsd:vatAccountNumber>" + drAcc + "</xsd:vatAccountNumber>\n" + "                                                <xsd:vatAmount>0.0</xsd:vatAmount>\n" + "         </axis:request>\n" + "      </axis:doAbabilNGTransaction>\n" + "   </soapenv:Body>\n" + "</soapenv:Envelope>";
        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "doAbabilNGTransaction");
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            log.info("ABABIL Request: {}", input);

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {

                sb.append(output);

                String responseXML = sb.toString();

                String XMLResponseCode = tagXmlData(responseXML, "responseCode");
                String XMLResponseMessage = tagXmlData(responseXML, "responseMessage");
                String transactionDateTime;
                String transactionRefNumber;
                String voucherNumber;

                if (XMLResponseCode.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    transactionDateTime = tagXmlData(responseXML, "transactionDateTime");
                    transactionRefNumber = tagXmlData(responseXML, "transactionRefNumber");
                    voucherNumber = tagXmlData(responseXML, "voucherNumber");

                    outModel.setResponseCode(XMLResponseCode);
                    outModel.setResponseMessage(XMLResponseMessage);
                    outModel.setTransactionRefNumber(transactionRefNumber);
                    outModel.setTransactionDateTime(transactionDateTime);
                    outModel.setAbabilVoucher(voucherNumber);
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    if (StringUtils.isNotBlank(responseXML)) {
                        outModel.setResponseMessage(XMLResponseMessage);
                    } else {
                        outModel.setResponseMessage("No response found from Ababil.");
                    }
                }
                log.info("doAbabilNGTransaction response body is {}", responseXML);

            }
            br.close();
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
            outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            outModel.setResponseMessage(e.getMessage());
        }
        return outModel;
    }

    public String getTransactionDateTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = new Date();

        return formatter.format(date);
    }

    public TransactionStatusResponse cbsTrxStatusCheck(String accountNo, String refNo) {
        TransactionStatusResponse outModel = new TransactionStatusResponse();

        String input = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://city/xsd\">\n" + "    <soapenv:Header/>\n" + "    <soapenv:Body>\n" + "        <axis:CBSTransactionStatus>\n" + "            <!--Optional:-->\n" + "            <axis:request>\n" + "                <!--Optional:-->\n" + "                <xsd:accountNumber>" + accountNo + "</xsd:accountNumber>\n" + "                <!--Optional:-->\n" + "                <xsd:idNumber>" + refNo + "</xsd:idNumber>\n" + "                <!--Optional:-->\n" + "                <xsd:password>" + apiPassword + "</xsd:password>\n" + "                <!--Optional:-->\n" + "                <xsd:tranDate>N</xsd:tranDate>\n" + "                <!--Optional:-->\n" + "                <xsd:username>" + apiUsername + "</xsd:username>\n" + "            </axis:request>\n" + "        </axis:CBSTransactionStatus>\n" + "    </soapenv:Body>\n" + "</soapenv:Envelope>";
        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "CBSTransactionStatus");
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            log.info("CBSTransactionStatus Request: {}", input);

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {

                sb.append(output);
                String responseXML = sb.toString();

                log.trace("CBSTransactionStatus response body is {}", output);

                String XMLResponseCode = tagXmlData(responseXML, "responseCode");
                String XMLResponseMessage = tagXmlData(responseXML, "responseMessage");
                String tranId;
                String tranAmt;
                String tranParticular;
                String tranParticular2;
                String tranRemarks;
                String tranType;

                if (XMLResponseCode.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    tranAmt = tagXmlData(responseXML, "tranAmt");
                    tranId = tagXmlData(responseXML, "tranId");
                    tranParticular = tagXmlData(responseXML, "tranParticular");
                    tranParticular2 = tagXmlData(responseXML, "tranParticular2");
                    tranRemarks = tagXmlData(responseXML, "tranRemarks");
                    tranType = tagXmlData(responseXML, "tranType");

                    outModel.setResponseCode(XMLResponseCode);
                    outModel.setResponseMessage(XMLResponseMessage);
                    outModel.setTranId(tranId);
                    outModel.setTranAmt(tranAmt);
                    outModel.setTranParticular(tranParticular);
                    outModel.setTranParticular2(tranParticular2);
                    outModel.setTranRemarks(tranRemarks);
                    outModel.setTranType(tranType);
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(XMLResponseMessage);
                }

                log.info("CBSTransactionStatus response body is {}", output);

            }
            br.close();
            conn.disconnect();
        } catch (Exception e) {
            outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            outModel.setResponseMessage(e.getMessage());
        }
        return outModel;
    }

}
