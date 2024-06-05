package com.cbl.cityrtgs.services.soap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class SoapConfig {
    @Value("${soap.config.api.url}")
    protected String apiUrl;

    @Value("${soap.config.fi.api.url}")
    protected String fiApiUrl;

    @Value("${soap.config.sms.api.url}")
    protected String smsApiUrl;

    @Value("${soap.service.username}")
    protected String apiUsername;

    @Value("${soap.service.password}")
    protected String apiPassword;

    @Value("${spring.datasource.username}")
    protected String dbName;

/*    @Value("${soap.config.ababil.api.username}")
    protected String apiAbabilUsername;

    @Value("${soap.config.ababil.api.password}")
    protected String apiAbabilPassword;

    @Value("${soap.config.ababil.reverse.api.username}")
    protected String apiAbabilReverseUsername;

    @Value("${soap.config.ababil.reverse.api.password}")
    protected String apiAbabilReversePassword;

    @Value("${soap.config.card.api.username}")
    protected String apiCardUsername;

    @Value("${soap.config.card.api.password}")
    protected String apiCardPassword;

    @Value("${soap.config.fi.username}")
    protected String apiFIUsername;

    @Value("${soap.config.fi.password}")
    protected String apiFIPassword;*/


    protected String removeXmlStringNamespaceAndPreamble(String xmlString) {
        return xmlString.replaceAll("(<\\?[^<]*\\?>)?", ""). /* remove preamble */
                replaceAll("xmlns.*?(\"|\').*?(\"|\')", "") /* remove xmlns declaration */
                .replaceAll("(<)(\\w+:)(.*?>)", "$1$3") /* remove opening tag prefix */
                .replaceAll("(</)(\\w+:)(.*?>)", "$1$3"); /* remove closing tags prefix */
    }
}
