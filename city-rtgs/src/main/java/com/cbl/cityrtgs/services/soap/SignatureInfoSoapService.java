package com.cbl.cityrtgs.services.soap;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.transaction.c2c.SignatureInfo;
import com.cbl.cityrtgs.models.dto.transaction.c2c.SignatureInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignatureInfoSoapService extends SoapConfig {
    public SignatureInfoResponse getFinacleSignatureInfo(String accountId, String customerId) {

        SignatureInfoResponse response = new SignatureInfoResponse();
        List<SignatureInfo> signatureList = new ArrayList<>();
        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetCbsSignatureInfo xmlns=\"http://ws.apache.org/axis2\">\n" +
                "      <request>\n" +
                "        <accountId>" + accountId + "</accountId>\n" +
                "        <customerId>" + customerId + "</customerId>\n" +
                "        <password>" + apiPassword + "</password>\n" +
                "        <username>" + apiUsername + "</username>\n" +
                "      </request>\n" +
                "    </GetCbsSignatureInfo>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>\n";

        try {

            // URL url = new URL("http://192.168.220.53:8080/axis2/services/CBLFIWebServices?wsdl");
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "GetCbsSignatureInfo");

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


                //  if (bodyNodes.getLength() > 0) {
                if (responseCode.equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    response.setResponseCode(responseCode);
                    response.setResponseMessage(responseMessage);

                    NodeList bodyNodes = doc.getElementsByTagName("signatureInfoList");

                    for (int i = 0; i < bodyNodes.getLength(); i++) {
                        Node nNode = bodyNodes.item(i);
                        Element bodyNode = (Element) nNode;
                        SignatureInfo signature = new SignatureInfo();

//                        if (bodyNode.getElementsByTagName("acctid").item(0) != null) {
//                            result.put("acctid", bodyNode.getElementsByTagName("acctid").item(0).getTextContent());
//                        }
//
//                        if (bodyNode.getElementsByTagName("customername").item(0) != null) {
//                            result.put("customername", bodyNode.getElementsByTagName("customername").item(0).getTextContent());
//                        }
//
//                        if (bodyNode.getElementsByTagName("signatureid").item(0) != null) {
//                            result.put("signatureid", bodyNode.getElementsByTagName("signatureid").item(0).getTextContent());
//                        }
//
//                        if (bodyNode.getElementsByTagName("signrequestid").item(0) != null) {
//                            result.put("signrequestid", bodyNode.getElementsByTagName("signrequestid").item(0).getTextContent());
//                        }
//
//                        if (bodyNode.getElementsByTagName("returnedsignature").item(0) != null) {
//                            result.put("returnedsignature", bodyNode.getElementsByTagName("returnedsignature").item(0).getTextContent());
//                        }

                        if (bodyNode.getElementsByTagName("returnedsignature").item(0) != null) {
                            signature.setReturnedsignature(bodyNode.getElementsByTagName("returnedsignature").item(0).getTextContent());
                        }

                        if (bodyNode.getElementsByTagName("signatureid").item(0) != null) {
                            signature.setSignatureid(bodyNode.getElementsByTagName("signatureid").item(0).getTextContent());
                        }

                        if (bodyNode.getElementsByTagName("signrequestid").item(0) != null) {
                            signature.setSignrequestid(bodyNode.getElementsByTagName("signrequestid").item(0).getTextContent());
                        }

                        signatureList.add(signature);
                    }
                    response.setSignatureList(signatureList);

                } else {
                    response.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    response.setResponseMessage(responseMessage);
                }
            }
            br.close();
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
            response.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            response.setResponseMessage(e.getMessage());
        }

        return response;
    }

    public SignatureInfoResponse getAbabilSignatureInfo(String accountId) {
        SignatureInfoResponse response = new SignatureInfoResponse();
        //  Map<String, String> result = new HashMap<>();
        List<SignatureInfo> signatureList = new ArrayList<>();
        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <getAbabilNGAccountImage xmlns=\"http://ws.apache.org/axis2\">\n" +
                "      <request>\n" +
                "        <accountNumber>" + accountId + "</accountNumber>\n" +
                "        <password>" + apiPassword + "</password>\n" +
                "        <username>" + apiUsername + "</username>\n" +
                "      </request>\n" +
                "    </getAbabilNGAccountImage>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>\n";

        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "test/xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "getAbabilNGAccountImageList");

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
                    response.setResponseCode(responseCode);
                    response.setResponseMessage(responseMessage);
                    var bodyNodes = doc.getElementsByTagName("responseData");
                    for (int i = 0; i < bodyNodes.getLength(); i++) {
                        Node nNode = bodyNodes.item(i);
                        Element bodyNode = (Element) nNode;
                        SignatureInfo signature = new SignatureInfo();
                        if (bodyNode.getElementsByTagName("accountSignature").item(0) != null) {
                            signature.setReturnedsignature(bodyNode.getElementsByTagName("accountSignature").item(0).getTextContent());
                        }
                        if (bodyNode.getElementsByTagName("accountSignature").item(0) != null) {
                            signature.setSignatureid("signature serial: " + i);
                        }
                        signatureList.add(signature);

                    }
                    response.setSignatureList(signatureList);
                } else {
                    response.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
                    response.setResponseMessage(responseMessage);
                }

            }
            br.close();
            conn.disconnect();
        } catch (Throwable e) {
            e.printStackTrace();
            response.setResponseCode(ResponseCodeEnum.ERROR_RESPONSE_CODE.getCode());
            response.setResponseMessage(e.getMessage());
        }

        return response;
    }
}
