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

@Slf4j
@RequiredArgsConstructor
@Service
public class DoFinacleFISoapService extends SoapConfig {

    public CbsResponse doFinacleBatchChargeVatTransaction(TransactionRequest transactionRequest) {
        CbsResponse outModel = new CbsResponse();
        String drAccount = transactionRequest.getDrAccount();
        String chargeAccount = transactionRequest.getChargeAccount();
        String vatAccount = transactionRequest.getVatAccount();
        BigDecimal amount = transactionRequest.getAmount();
        BigDecimal charge = transactionRequest.getCharge();
        BigDecimal vat = transactionRequest.getVat();
        String currency = transactionRequest.getCurrencyCode();

        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://fi/xsd\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <axis:doFinacleTransactionFI>\n" +
                "            <axis:request>\n" +
                "                <xsd:partTrnRecList>\n" +
                "                    <xsd:acctId>" + drAccount + "</xsd:acctId>\n" +
                "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                "                    <xsd:partTrnRmks>" + transactionRequest.getRemarks() + "</xsd:partTrnRmks>\n" +
                "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                "                    <xsd:trnParticulars>RTGS Service Charge and VAT</xsd:trnParticulars>\n" +
                "                    <xsd:trnParticulars2>C2C Bulk Transaction</xsd:trnParticulars2>\n" +
                "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                "                </xsd:partTrnRecList>\n" +
                "                <xsd:partTrnRecList>\n" +
                "                    <xsd:acctId>" + chargeAccount + "</xsd:acctId>\n" +
                "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                "                    <xsd:partTrnRmks>" + transactionRequest.getRemarks() + "</xsd:partTrnRmks>\n" +
                "                    <xsd:trnAmtAmountValue>" + charge + "</xsd:trnAmtAmountValue>\n" +
                "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                "                    <xsd:trnParticulars>RTGS Service Charge</xsd:trnParticulars>\n" +
                "                    <xsd:trnParticulars2>C2C Bulk Transaction</xsd:trnParticulars2>\n" +
                "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                "                </xsd:partTrnRecList>\n" +
                "                <xsd:partTrnRecList>\n" +
                "                    <xsd:acctId>" + vatAccount + "</xsd:acctId>\n" +
                "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                "                    <xsd:partTrnRmks>" + transactionRequest.getRemarks() + "</xsd:partTrnRmks>\n" +
                "                    <xsd:trnAmtAmountValue>" + vat + "</xsd:trnAmtAmountValue>\n" +
                "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                "                    <xsd:trnParticulars>VAT on RTGS Service Charge</xsd:trnParticulars>\n" +
                "                    <xsd:trnParticulars2>C2C Bulk Transaction</xsd:trnParticulars2>\n" +
                "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                "                </xsd:partTrnRecList>\n" +
                "                <xsd:password>" + apiPassword + "</xsd:password>\n" +
                "                <xsd:trnSubType>CI</xsd:trnSubType>\n" +
                "                <xsd:trnType>T</xsd:trnType>\n" +
                "                <xsd:username>" + apiUsername + "</xsd:username>\n" +
                "            </axis:request>\n" +
                "        </axis:doFinacleTransactionFI>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>\n";

        try {

            URL url = new URL(fiApiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            //conn.setRequestProperty("SOAPAction", "doFinacleTransactionFI");
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
                    outModel.setResponseCode(responseMessage);
                    outModel.setTransactionDate(doc.getElementsByTagName("transactionDate").item(0).getTextContent());
                    outModel.setTransactionId(doc.getElementsByTagName("transactionId").item(0).getTextContent());
                    outModel.setTransactionRefNumber(doc.getElementsByTagName("transactionId").item(0).getTextContent());

                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(responseMessage);
                }
                log.info("doFinacleTransactionFI response body is {}", output);
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

    public CbsResponse doFinacleReverseTransactionFI(TransactionRequest transactionRequest) {
        CbsResponse outModel = new CbsResponse();
        Boolean chargeEnabled = transactionRequest.getChargeEnabled();
        String drAccount = transactionRequest.getDrAccount();
        String crAccount = transactionRequest.getCrAccount();
        String chargeAccount = transactionRequest.getChargeAccount();
        String vatAccount = transactionRequest.getVatAccount();
        BigDecimal amount = transactionRequest.getAmount();
        String input;
        String currency = transactionRequest.getCurrencyCode();

        String txnRemarks = StringUtils.isNotBlank(transactionRequest.getRemarks()) ? transactionRequest.getRemarks() : transactionRequest.getNarration();
        String trnParticulars = transactionRequest.getNarration();
        String trnParticulars2 = StringUtils.isNotBlank(transactionRequest.getParticular2()) ? transactionRequest.getParticular2() : transactionRequest.getNarration();

        if (txnRemarks.length() > 30) {
            txnRemarks = txnRemarks.substring(0, 29);
        }
        if (trnParticulars.length() > 50) {
            trnParticulars = trnParticulars.substring(0, 49);
        }
        if (trnParticulars2.length() > 50) {
            trnParticulars2 = trnParticulars2.substring(0, 49);
        }
        if (chargeEnabled) {
            BigDecimal charge = transactionRequest.getCharge();
            BigDecimal vat = transactionRequest.getVat();
            BigDecimal chargeVat = transactionRequest.getCharge().add(transactionRequest.getVat());
            input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://fi/xsd\">\n" +
                    "    <soapenv:Header/>\n" +
                    "    <soapenv:Body>\n" +
                    "        <axis:doFinacleTransactionFI>\n" +
                    "            <axis:request>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + drAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + crAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + crAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + chargeVat + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>Reverse RTGS Service Charge and VAT</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + chargeAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + charge + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>Reverse RTGS Service Charge</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + vatAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + vat + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>Reverse VAT on RTGS Service Charge</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:password>" + apiPassword + "</xsd:password>\n" +
                    "                <xsd:trnSubType>CI</xsd:trnSubType>\n" +
                    "                <xsd:trnType>T</xsd:trnType>\n" +
                    "                <xsd:username>" + apiUsername + "</xsd:username>\n" +
                    "            </axis:request>\n" +
                    "        </axis:doFinacleTransactionFI>\n" +
                    "    </soapenv:Body>\n" +
                    "</soapenv:Envelope>\n";
        } else {
            input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://fi/xsd\">\n" +
                    "    <soapenv:Header/>\n" +
                    "    <soapenv:Body>\n" +
                    "        <axis:doFinacleTransactionFI>\n" +
                    "            <axis:request>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + drAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + crAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:password>" + apiPassword + "</xsd:password>\n" +
                    "                <xsd:trnSubType>CI</xsd:trnSubType>\n" +
                    "                <xsd:trnType>T</xsd:trnType>\n" +
                    "                <xsd:username>" + apiUsername + "</xsd:username>\n" +
                    "            </axis:request>\n" +
                    "        </axis:doFinacleTransactionFI>\n" +
                    "    </soapenv:Body>\n" +
                    "</soapenv:Envelope>\n";
        }

        try {

            URL url = new URL(fiApiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "doFinacleTransaction");
            // conn.setRequestProperty("SOAPAction", "doFinacleTransactionFI");

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
                    outModel.setTransactionRefNumber(doc.getElementsByTagName("transactionId").item(0).getTextContent());
                    outModel.setTransactionId(doc.getElementsByTagName("transactionId").item(0).getTextContent());
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(responseMessage);
                }


                log.info("doFinacleTransactionFI response body is {}", output);
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

    public CbsResponse doFinacleTransactionFI(TransactionRequest transactionRequest) {
        CbsResponse outModel = new CbsResponse();
        Boolean chargeEnabled = transactionRequest.getChargeEnabled();
        String drAccount = transactionRequest.getDrAccount();
        String crAccount = transactionRequest.getCrAccount();
        String chargeAccount = transactionRequest.getChargeAccount();
        String vatAccount = transactionRequest.getVatAccount();
        BigDecimal amount = transactionRequest.getAmount();
        String input;
        String currency = transactionRequest.getCurrencyCode();

        String txnRemarks = StringUtils.isNotBlank(transactionRequest.getRemarks()) ? transactionRequest.getRemarks() : transactionRequest.getNarration();
        String chargeRemarks = StringUtils.isNotBlank(transactionRequest.getChargeRemarks()) ? transactionRequest.getChargeRemarks() : transactionRequest.getNarration();
        String trnParticulars = transactionRequest.getNarration();
        String trnParticulars2 = StringUtils.isNotBlank(transactionRequest.getParticular2()) ? transactionRequest.getParticular2() : transactionRequest.getNarration();

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
        if (chargeEnabled) {
            BigDecimal charge = transactionRequest.getCharge();
            BigDecimal vat = transactionRequest.getVat();
            BigDecimal chargeVat = transactionRequest.getCharge().add(transactionRequest.getVat());
            input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://fi/xsd\">\n" +
                    "    <soapenv:Header/>\n" +
                    "    <soapenv:Body>\n" +
                    "        <axis:doFinacleTransactionFI>\n" +
                    "            <axis:request>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + drAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + crAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + drAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + chargeRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + chargeVat + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>RTGS Service Charge and VAT</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + transactionRequest.getRtgsRefNo() + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + chargeAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + chargeRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + charge + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>RTGS Service Charge</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + transactionRequest.getRtgsRefNo() + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + vatAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + chargeRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + vat + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>VAT on RTGS Service Charge</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + transactionRequest.getRtgsRefNo() + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:password>" + apiPassword + "</xsd:password>\n" +
                    "                <xsd:trnSubType>CI</xsd:trnSubType>\n" +
                    "                <xsd:trnType>T</xsd:trnType>\n" +
                    "                <xsd:username>" + apiUsername + "</xsd:username>\n" +
                    "            </axis:request>\n" +
                    "        </axis:doFinacleTransactionFI>\n" +
                    "    </soapenv:Body>\n" +
                    "</soapenv:Envelope>\n";
        } else {
            input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://fi/xsd\">\n" +
                    "    <soapenv:Header/>\n" +
                    "    <soapenv:Body>\n" +
                    "        <axis:doFinacleTransactionFI>\n" +
                    "            <axis:request>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + drAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:partTrnRecList>\n" +
                    "                    <xsd:acctId>" + crAccount + "</xsd:acctId>\n" +
                    "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                    "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                    "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                    "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                    "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                    "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                    "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                    "                </xsd:partTrnRecList>\n" +
                    "                <xsd:password>" + apiPassword + "</xsd:password>\n" +
                    "                <xsd:trnSubType>CI</xsd:trnSubType>\n" +
                    "                <xsd:trnType>T</xsd:trnType>\n" +
                    "                <xsd:username>" + apiUsername + "</xsd:username>\n" +
                    "            </axis:request>\n" +
                    "        </axis:doFinacleTransactionFI>\n" +
                    "    </soapenv:Body>\n" +
                    "</soapenv:Envelope>\n";
        }

        try {

            URL url = new URL(fiApiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            //  conn.setRequestProperty("SOAPAction", "doFinacleTransactionFI");
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
                    outModel.setResponseMessage(responseMessage+drAccount);
                }


                log.info("doFinacleTransactionFI response body is {}", output);
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

    public CbsResponse doFinacleInwardTransactionFI(TransactionRequest transactionRequest) {
        CbsResponse outModel = new CbsResponse();

        String drAccount = transactionRequest.getDrAccount();
        String crAccount = transactionRequest.getCrAccount();
        BigDecimal amount = transactionRequest.getAmount();
        String currency = transactionRequest.getCurrencyCode();
        String txnRemarks = StringUtils.isNotBlank(transactionRequest.getRemarks()) ? transactionRequest.getRemarks() : transactionRequest.getNarration();
        String trnParticulars = transactionRequest.getNarration();
        String trnParticulars2 = StringUtils.isNotBlank(transactionRequest.getParticular2()) ? transactionRequest.getParticular2() : transactionRequest.getNarration();

        if (txnRemarks.length() > 30) {
            txnRemarks = txnRemarks.substring(0, 29);
        }
        if (trnParticulars.length() > 50) {
            trnParticulars = trnParticulars.substring(0, 49);
        }
        if (trnParticulars2.length() > 50) {
            trnParticulars2 = trnParticulars2.substring(0, 49);
        }

        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://fi/xsd\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <axis:doFinacleTransactionFI>\n" +
                "            <axis:request>\n" +
                "                <xsd:partTrnRecList>\n" +
                "                    <xsd:acctId>" + drAccount + "</xsd:acctId>\n" +
                "                    <xsd:creditDebitFlg>D</xsd:creditDebitFlg>\n" +
                "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                "                </xsd:partTrnRecList>\n" +
                "                <xsd:partTrnRecList>\n" +
                "                    <xsd:acctId>" + crAccount + "</xsd:acctId>\n" +
                "                    <xsd:creditDebitFlg>C</xsd:creditDebitFlg>\n" +
                "                    <xsd:partTrnRmks>" + txnRemarks + "</xsd:partTrnRmks>\n" +
                "                    <xsd:trnAmtAmountValue>" + amount + "</xsd:trnAmtAmountValue>\n" +
                "                    <xsd:trnAmtCurrencyCode>" + currency + "</xsd:trnAmtCurrencyCode>\n" +
                "                    <xsd:trnParticulars>" + trnParticulars + "</xsd:trnParticulars>\n" +
                "                    <xsd:trnParticulars2>" + trnParticulars2 + "</xsd:trnParticulars2>\n" +
                "                    <xsd:valueDt>" + getTransactionDateTime() + "</xsd:valueDt>\n" +
                "                </xsd:partTrnRecList>\n" +
                "                <xsd:password>" + apiPassword + "</xsd:password>\n" +
                "                <xsd:trnSubType>CI</xsd:trnSubType>\n" +
                "                <xsd:trnType>T</xsd:trnType>\n" +
                "                <xsd:username>" + apiUsername + "</xsd:username>\n" +
                "            </axis:request>\n" +
                "        </axis:doFinacleTransactionFI>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>\n";


        try {

            // URL url = new URL(apiUrl);
            URL url = new URL(fiApiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "doFinacleTransaction");
            // conn.setRequestProperty("SOAPAction", "doFinacleTransactionFI");

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
                    outModel.setResponseCode(doc.getElementsByTagName("responseCode").item(0).getTextContent());
                    outModel.setResponseMessage(doc.getElementsByTagName("responseMessage").item(0).getTextContent());
                    outModel.setTransactionDate(doc.getElementsByTagName("transactionDate").item(0).getTextContent());
                    outModel.setTransactionId(doc.getElementsByTagName("transactionId").item(0).getTextContent());
                    outModel.setTransactionRefNumber(doc.getElementsByTagName("transactionId").item(0).getTextContent());

                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(responseMessage);
                }

                log.info("doFinacleTransactionFI response body is {}", output);
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
