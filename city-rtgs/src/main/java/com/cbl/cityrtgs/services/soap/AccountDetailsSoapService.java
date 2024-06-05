package com.cbl.cityrtgs.services.soap;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.common.logger.RtgsLogger;
import com.cbl.cityrtgs.common.logger.StpLogger;
import com.cbl.cityrtgs.models.dto.transaction.c2c.PayerDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;


@RequiredArgsConstructor
@Service
public class AccountDetailsSoapService extends SoapConfig {
    private final RtgsLogger rtgsLogger;

    public PayerDetailsResponse getAccountDetailsFromSoap(String accountNumber) {
        PayerDetailsResponse outModel = new PayerDetailsResponse();

        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <getAccountDetails xmlns=\"http://ws.apache.org/axis2\">\n" +
                "      <request>\n" +
                "        <accountNumber>" + accountNumber + "</accountNumber>\n" +
                "        <password>" + apiPassword + "</password>\n" +
                "        <username>" + apiUsername + "</username>\n" +
                "      </request>\n" +
                "    </getAccountDetails>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>\n";

        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "getAccountDetails");

            rtgsLogger.trace("getAccountDetails request body: " + input);

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

                outModel.setResponseCode(responseCode);
                outModel.setResponseMessage(responseMessage);
                if (responseCode.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    NodeList bodyNodes = doc.getElementsByTagName("responseData");

                    Element bodyNode = (Element) bodyNodes.item(0);
                    outModel.setPayerAccId(bodyNode.getElementsByTagName("account").item(0).getTextContent());
                    outModel.setPayerAccNo(bodyNode.getElementsByTagName("account").item(0).getTextContent());
                    outModel.setPayerName(bodyNode.getElementsByTagName("accountName").item(0).getTextContent());
                    outModel.setAcctType(bodyNode.getElementsByTagName("acctType").item(0).getTextContent());
                    outModel.setAvailBalance(bodyNode.getElementsByTagName("availBalance").item(0).getTextContent());
                    outModel.setBalance(bodyNode.getElementsByTagName("balance").item(0).getTextContent());
                    outModel.setPermanentAddress(bodyNode.getElementsByTagName("permanentAddress").item(0).getTextContent());
                    outModel.setStatus(bodyNode.getElementsByTagName("status").item(0).getTextContent());
                    outModel.setSchemeCode(bodyNode.getElementsByTagName("schemeCode").item(0).getTextContent());
                    outModel.setSchemeType(bodyNode.getElementsByTagName("schemeType").item(0).getTextContent());
                    outModel.setSolId(bodyNode.getElementsByTagName("solId").item(0).getTextContent());
                    outModel.setCustId(bodyNode.getElementsByTagName("custId").item(0).getTextContent());
                    outModel.setCurrencyCode(bodyNode.getElementsByTagName("currencyCode").item(0).getTextContent());
                    outModel.setPayerBranchName(bodyNode.getElementsByTagName("branchName").item(0).getTextContent());
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage(responseMessage);
                }
                rtgsLogger.trace("getAccountDetails response body: " + output);

            }
            br.close();
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
            outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            outModel.setResponseMessage(e.getMessage());
            rtgsLogger.error(e.getMessage());
        }
        return outModel;
    }
}
