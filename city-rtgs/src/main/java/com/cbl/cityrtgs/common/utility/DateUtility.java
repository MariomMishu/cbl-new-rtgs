package com.cbl.cityrtgs.common.utility;

import com.cbl.cityrtgs.repositories.configuration.STPSettlementDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Slf4j
@RequiredArgsConstructor
@Component
public class DateUtility {
    private final STPSettlementDateRepository settlementDateRepository;

    public static LocalDate toDate(String date) {

        return LocalDate.parse(date);
    }

    public static String localDateToStringDate() {
        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public static String folderCreateDate() {
        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
        return localDate.format(formatter);
    }

    public static XMLGregorianCalendar creDt() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        XMLGregorianCalendar xmlGregorianCalendar = null;
        try {
            TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
            LocalDateTime localDateTime = LocalDateTime.now();
            localDateTime.format(dateFormat);
            GregorianCalendar gc = GregorianCalendar.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            xmlGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlGregorianCalendar;
    }

    public static XMLGregorianCalendar creDtTm() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        XMLGregorianCalendar xmlGregorianCalendar = null;
        try {

            LocalDateTime localDateTime = LocalDateTime.now();
            localDateTime.format(dateFormat);
            GregorianCalendar gc = GregorianCalendar.from(LocalDateTime.now().atZone(ZoneId.of("Asia/Dhaka")));
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            xmlGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

            xmlGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlGregorianCalendar;
    }

    public static XMLGregorianCalendar sttlmDt() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        XMLGregorianCalendar xmlGregorianCalendar = null;
        try {

            Date date = format.parse("2023-08-17");

            TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);

            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            xmlGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            xmlGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
            xmlGregorianCalendar.setHour(DatatypeConstants.FIELD_UNDEFINED);
            xmlGregorianCalendar.setMinute(DatatypeConstants.FIELD_UNDEFINED);
            xmlGregorianCalendar.setSecond(DatatypeConstants.FIELD_UNDEFINED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlGregorianCalendar;
    }

    public XMLGregorianCalendar getXMLdate() {
        var sstps = settlementDateRepository.findAll().stream().findFirst();

        XMLGregorianCalendar xmlDate = null;
        try {
            if (!sstps.isEmpty() && sstps.get().getFlag()) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                Date date = format.parse(sstps.get().getSttmlDt());

                TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(date);
                xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);


            } else {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate localDate = LocalDate.now();
                localDate.format(format);
                GregorianCalendar gc = GregorianCalendar.from(LocalDateTime.now().atZone(ZoneId.of("Asia/Dhaka")));
                xmlDate = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(gc);

            }

            xmlDate.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            xmlDate.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
            xmlDate.setHour(DatatypeConstants.FIELD_UNDEFINED);
            xmlDate.setMinute(DatatypeConstants.FIELD_UNDEFINED);
            xmlDate.setSecond(DatatypeConstants.FIELD_UNDEFINED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlDate;
    }


}
