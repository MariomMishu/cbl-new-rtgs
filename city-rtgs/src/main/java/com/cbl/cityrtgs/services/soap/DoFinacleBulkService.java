package com.cbl.cityrtgs.services.soap;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.transaction.CbsResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class DoFinacleBulkService extends SoapConfig {
    public CbsResponse doFinacleTransactionFI(List<TransactionRequest> transactionRequest) {
        CbsResponse outModel = new CbsResponse();
        String txn = "";

        for (TransactionRequest request : transactionRequest) {
            String txnRemarks = StringUtils.isNotBlank(request.getRemarks()) ? request.getRemarks() : request.getNarration();
            String chargeRemarks = StringUtils.isNotBlank(request.getChargeRemarks()) ? request.getChargeRemarks() : request.getNarration();
            String trnParticulars = request.getNarration();
            String trnParticulars2 = StringUtils.isNotBlank(request.getParticular2()) ? request.getParticular2() : request.getNarration();

            if (txnRemarks.length() > 30) {
                txnRemarks = txnRemarks.substring(0, 29);
            }
            if (chargeRemarks.length() > 30) {
                chargeRemarks = chargeRemarks.substring(0, 29);
            }
            if (trnParticulars.length() > 50) {
                trnParticulars = trnParticulars.substring(0, 49);
            }
            if (trnParticulars2.length() > 50) {
                trnParticulars2 = trnParticulars2.substring(0, 49);
            }

            if (request.getChargeEnabled()) {
                BigDecimal charge = request.getCharge();
                BigDecimal vat = request.getVat();
                BigDecimal chargeVat = request.getCharge().add(request.getVat());
                txn = txn + "<xsd:partTrnRecList>\n" +
                        "                   <xsd:acctId>" + request.getDrAccount() + "</xsd:acctId>\n" +
                        "                   <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                        "                   <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                        "                   <xsd:trnAmtAmountValue>" + request.getAmount() + "</xsd:trnAmtAmountValue>\n" +
                        "                   <xsd:trnAmtCurrencyCode>" + request.getCurrencyCode() + "</xsd:trnAmtCurrencyCode>\n" +
                        "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                        "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                        "                   <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                        "           </xsd:partTrnRecList>\n" +
                        "           <xsd:partTrnRecList>\n" +
                        "                   <xsd:acctId>" + request.getCrAccount() + "</xsd:acctId>\n" +
                        "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                        "                   <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                        "                   <xsd:trnAmtAmountValue>" + request.getAmount() + "</xsd:trnAmtAmountValue>\n" +
                        "                   <xsd:trnAmtCurrencyCode>" + request.getCurrencyCode() + "</xsd:trnAmtCurrencyCode>\n" +
                        "                   <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                        "                   <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                        "                   <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                        "                </xsd:partTrnRecList>\n" +
                        "                <xsd:partTrnRecList>\n" +
                        "                    <xsd:acctId>" + request.getDrAccount() + "</xsd:acctId>\n" +
                        "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                        "                    <xsd:partTrnRmks>" + chargeRemarks + "</xsd:partTrnRmks>\n" +
                        "                    <xsd:trnAmtAmountValue>" + chargeVat + "</xsd:trnAmtAmountValue>\n" +
                        "                    <xsd:trnAmtCurrencyCode>" + request.getCurrencyCode() + "</xsd:trnAmtCurrencyCode>\n" +
                        "                    <xsd:trnParticulars>RTGS Service Charge and VAT</xsd:trnParticulars>\n" +
                        "                    <xsd:trnParticulars2>RTGS Service Charge and VAT</xsd:trnParticulars2>\n" +
                        "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                        "                </xsd:partTrnRecList>\n" +
                        "                <xsd:partTrnRecList>\n" +
                        "                    <xsd:acctId>" + request.getChargeAccount() + "</xsd:acctId>\n" +
                        "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                        "                    <xsd:partTrnRmks>" + chargeRemarks + "</xsd:partTrnRmks>\n" +
                        "                    <xsd:trnAmtAmountValue>" + charge + "</xsd:trnAmtAmountValue>\n" +
                        "                    <xsd:trnAmtCurrencyCode>" + request.getCurrencyCode() + "</xsd:trnAmtCurrencyCode>\n" +
                        "                    <xsd:trnParticulars>RTGS Service Charge</xsd:trnParticulars>\n" +
                        "                    <xsd:trnParticulars2>RTGS Service Charge</xsd:trnParticulars2>\n" +
                        "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                        "                </xsd:partTrnRecList>\n" +
                        "                <xsd:partTrnRecList>\n" +
                        "                    <xsd:acctId>" + request.getVatAccount() + "</xsd:acctId>\n" +
                        "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                        "                    <xsd:partTrnRmks>" + chargeRemarks + "</xsd:partTrnRmks>\n" +
                        "                    <xsd:trnAmtAmountValue>" + vat + "</xsd:trnAmtAmountValue>\n" +
                        "                    <xsd:trnAmtCurrencyCode>" + request.getCurrencyCode() + "</xsd:trnAmtCurrencyCode>\n" +
                        "                    <xsd:trnParticulars>VAT on RTGS Service Charge</xsd:trnParticulars>\n" +
                        "                    <xsd:trnParticulars2>VAT on RTGS Service Charge</xsd:trnParticulars2>\n" +
                        "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                        "                </xsd:partTrnRecList>\n";
            } else {
                txn = txn + "<xsd:partTrnRecList>\n" +
                        "<xsd:acctId>" + request.getDrAccount() + "</xsd:acctId>\n" +
                        "<xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                        "<xsd:partTrnRmks>" + txnRemarks.trim() + "</xsd:partTrnRmks>\n" +
                        "<xsd:trnAmtAmountValue>" + request.getAmount() + "</xsd:trnAmtAmountValue>\n" +
                        "<xsd:trnAmtCurrencyCode>" + request.getCurrencyCode() + "</xsd:trnAmtCurrencyCode>\n" +
                        "<xsd:trnParticulars>" + trnParticulars.trim() + "</xsd:trnParticulars>\n" +
                        "<xsd:trnParticulars2>" + trnParticulars2.trim() + "</xsd:trnParticulars2>\n" +
                        "<xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                        "</xsd:partTrnRecList>\n" +
                        "<xsd:partTrnRecList>\n" +
                        "<xsd:acctId>" + request.getCrAccount() + "</xsd:acctId>\n" +
                        "<xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                        "<xsd:partTrnRmks>" + txnRemarks.trim() + "</xsd:partTrnRmks>\n" +
                        "<xsd:trnAmtAmountValue>" + request.getAmount() + "</xsd:trnAmtAmountValue>\n" +
                        "<xsd:trnAmtCurrencyCode>" + request.getCurrencyCode() + "</xsd:trnAmtCurrencyCode>\n" +
                        "<xsd:trnParticulars>" + trnParticulars.trim() + "</xsd:trnParticulars>\n" +
                        "<xsd:trnParticulars2>" + trnParticulars2.trim() + "</xsd:trnParticulars2>\n" +
                        "<xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                        "</xsd:partTrnRecList>\n";
            }

        }

        String input;
        input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://fi/xsd\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <axis:doFinacleTransactionFI>\n" +
                "            <axis:request>" + txn + "\n" +
                "                <xsd:password>" + apiPassword + "</xsd:password>\n" +
                "                <xsd:trnSubType>CI</xsd:trnSubType>\n" +
                "                <xsd:trnType>T</xsd:trnType>\n" +
                "                <xsd:username>" + apiUsername + "</xsd:username>\n" +
                "            </axis:request>\n" +
                "        </axis:doFinacleTransactionFI>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>\n";
        try {

            //URL url = new URL(apiUrl);
            URL url = new URL(fiApiUrl);
            //  URL url = new URL("http://192.168.220.53:8080/axis2/services/CBLFIWebServices?wsdl");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "doFinacleTransaction");

            log.info("doFinacleTransactionFI request body is {}", input);
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

                DocumentBuilder builder = DocumentBuilderFactory
                        .newInstance().newDocumentBuilder();
                InputSource src = new InputSource();
                src.setCharacterStream(new StringReader(output));

                Document doc = builder.parse(src);

                responseCode = doc.getElementsByTagName("responseCode").item(0).getTextContent();
                responseMessage = doc.getElementsByTagName("responseMessage").item(0).getTextContent();

                if (responseCode.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    outModel.setResponseCode(responseCode);
                    outModel.setResponseMessage(responseMessage);
                    outModel.setTransactionDate(doc.getElementsByTagName("transactionDate").item(0).getTextContent());
                    outModel.setTransactionId(doc.getElementsByTagName("transactionId").item(0).getTextContent());
                    outModel.setTransactionRefNumber(doc.getElementsByTagName("transactionId").item(0).getTextContent());
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(responseMessage);
                }


                log.info("doFinacleTransactionFI response body is {}", doc);
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

    public java.lang.String getTransactionDateTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = new Date();

        return formatter.format(date);
    }
}
