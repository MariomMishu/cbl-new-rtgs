package com.cbl.cityrtgs.services.soap;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.response.SentSmsResponse;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class SentSmsSoapService extends SoapConfig {

    public SentSmsResponse sentSms(String mobileNumber, String smsData) {
        SentSmsResponse outModel = new SentSmsResponse();

        String input = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://ws.apache.org/axis2\" xmlns:xsd=\"http://city/xsd\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <axis:sendSMS>\n" +
                "         <!--Optional:-->\n" +
                "         <axis:request>\n" +
                "            <!--Optional:-->\n" +
                "            <xsd:mobileNumber>" + mobileNumber + "</xsd:mobileNumber>\n" +
                "            <!--Optional:-->\n" +
                "            <xsd:password>" + apiPassword + "</xsd:password>\n" +
                "            <!--Optional:-->\n" +
                "            <xsd:smsText>" + smsData + "</xsd:smsText>\n" +
                "            <!--Optional:-->\n" +
                "            <xsd:username>" + apiUsername + "</xsd:username>\n" +
                "         </axis:request>\n" +
                "      </axis:sendSMS>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";


        try {

            URL url = new URL(smsApiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "sendSMSuu");

            log.info("sendSMSuu request body is {}", input);

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();

            while ((output = br.readLine()) != null) {
                output = removeXmlStringNamespaceAndPreamble(output);
                sb.append(output);

                DocumentBuilder builder = DocumentBuilderFactory
                        .newInstance().newDocumentBuilder();
                InputSource src = new InputSource();
                src.setCharacterStream(new StringReader(output));

                Document doc = builder.parse(src);

                outModel.setResponseCode(doc.getElementsByTagName("responseCode").item(0).getTextContent());
                outModel.setResponseMessage(doc.getElementsByTagName("responseMessage").item(0).getTextContent());

                log.info("sendSMSuu response body is {}", output);
            }
            br.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            outModel.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            outModel.setResponseMessage(e.getMessage());

        }

        return outModel;
    }

}
