package com.cbl.cityrtgs.services.soap;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.transaction.CbsResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardTransactionService extends SoapConfig {

    public CbsResponse doCardTransaction(TransactionRequest transactionRequest, String remarks) {
        CbsResponse outModel = new CbsResponse();

        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <creditCardPaymentFromCASA xmlns=\"http://ws.apache.org/axis2\">\n" +
                "      <request>\n" +
                "        <accountCurrency>" + transactionRequest.getCurrencyCode() + "</accountCurrency>\n" +
                "        <amount>" + transactionRequest.getAmount() + "</amount>\n" +
                "        <cardNoActual>" + transactionRequest.getCrAccount() + "</cardNoActual>\n" +
                "        <customerAccount>" + transactionRequest.getDrAccount() + "</customerAccount>\n" +
                "        <originalAmount>" + transactionRequest.getAmount() + "</originalAmount>\n" +
                "        <originalCurrency>" + transactionRequest.getCurrencyCode() + "</originalCurrency>\n" +
                "        <password>" + apiPassword + "</password>\n" +
                "        <remarks>" + remarks + "</remarks>\n" +
                "        <username>" + apiUsername + "</username>\n" +
                "      </request>\n" +
                "    </creditCardPaymentFromCASA>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>\n";

        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "creditCardPaymentFromCASA");

            log.info("creditCardPaymentFromCASA request body is {}", input);
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
                log.trace("creditCardPaymentFromCASA response body is {}", output);

                if (responseCode.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    outModel.setTransactionRefNumber(doc.getElementsByTagName("approvalCode").item(0).getTextContent());
                    outModel.setTransactionDateTime(new Date().toString());
                    outModel.setResponseCode(responseCode);
                    outModel.setResponseMessage(responseMessage);
                    log.trace("creditCardPaymentFromCASA response body is {}", output);
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(responseMessage);
                }

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
}
