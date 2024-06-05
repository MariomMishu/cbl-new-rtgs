package com.cbl.cityrtgs.services.soap;


import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CardDetailsResponse;
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
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardDetailsSoapService extends SoapConfig {
    public CardDetailsResponse getCardDetailsFromSoap(String cardNumber) {
        CardDetailsResponse outModel = new CardDetailsResponse();
        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <getClientCardDetails xmlns=\"http://ws.apache.org/axis2\">\n" +
                "      <request>\n" +
                "        <cardNoActual>" + cardNumber + "</cardNoActual>\n" +
                "        <password>" + apiPassword + "</password>\n" +
                "        <username>" + apiUsername + "</username>\n" +
                "      </request>\n" +
                "    </getClientCardDetails>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>\n";

        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "getClientCardDetails");

            log.info("getClientCardDetails request body is {}", input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            String responseCode = "";
            String responseMessage = "";
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
                //  if (bodyNodes.getLength() > 0) {
                if (Objects.equals(responseCode, ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    NodeList bodyNodes = doc.getElementsByTagName("responseData");

                    Element bodyNode = (Element) bodyNodes.item(0);
                    outModel.setCASAScheme(bodyNode.getElementsByTagName("CASAScheme").item(0).getTextContent());
                    outModel.setAccountType(bodyNode.getElementsByTagName("accountType").item(0).getTextContent());
                    outModel.setAccountTypeBdt(bodyNode.getElementsByTagName("accountTypeBdt").item(0).getTextContent());
                    outModel.setAccountTypeUsd(bodyNode.getElementsByTagName("accountTypeUsd").item(0).getTextContent());
                    outModel.setBdtAccount(bodyNode.getElementsByTagName("bdtAccount").item(0).getTextContent());
                    outModel.setBranchCode(bodyNode.getElementsByTagName("branchCode").item(0).getTextContent());
                    outModel.setCardContract(bodyNode.getElementsByTagName("cardContract").item(0).getTextContent());
                    outModel.setCardHolderName(bodyNode.getElementsByTagName("cardHolderName").item(0).getTextContent());
                    outModel.setCardNoActual(bodyNode.getElementsByTagName("cardNoActual").item(0).getTextContent());
                    outModel.setCardStatus(bodyNode.getElementsByTagName("cardStatus").item(0).getTextContent());
                    outModel.setCardType(bodyNode.getElementsByTagName("cardType").item(0).getTextContent());
                    outModel.setCardUID(bodyNode.getElementsByTagName("cardUID").item(0).getTextContent());
                    outModel.setClientId(bodyNode.getElementsByTagName("clientId").item(0).getTextContent());
                    outModel.setExpiryDate(bodyNode.getElementsByTagName("expiryDate").item(0).getTextContent());
                    outModel.setUsdAccount(bodyNode.getElementsByTagName("usdAccount").item(0).getTextContent());

                    log.info("getClientCardDetails response body is {}", doc.toString());
                } else {
                    outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    outModel.setResponseMessage("Card Details Not Found. " + responseMessage);
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
