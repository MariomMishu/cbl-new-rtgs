package com.cbl.cityrtgs.services.si.utility;

import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
public class SiUtility {

    public static LocalDate calculateNextFireDate(LocalDate currentDate){

        return currentDate.plusDays(1);

    }

    public static LocalDate calculateNextMonthlyFireDate(LocalDate startDate, int monthlyFireDay){

        if(monthlyFireDay == startDate.getDayOfMonth()){
            return startDate;
        }
        else if(monthlyFireDay < startDate.getDayOfMonth()){
            startDate = YearMonth.from(startDate).plusMonths(1).atDay(monthlyFireDay);
        }
        else {

            int today = startDate.getDayOfMonth();
            int remainingDays = monthlyFireDay - today;
            startDate = startDate.plusDays(remainingDays);
        }

        return startDate;
    }

    public static Date getCurrentTime(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return calendar.getTime();
    }

    public static Date getCurrentDateTime(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.getTime();
    }

    public static LocalDate calculateNextWeeklyFireDay(LocalDate startDate, int fireDay){

        Map<Integer, DayOfWeek> day = new HashMap<>();
        day.put(1, DayOfWeek.SATURDAY);
        day.put(2, DayOfWeek.SUNDAY);
        day.put(3, DayOfWeek.MONDAY);
        day.put(4, DayOfWeek.TUESDAY);
        day.put(5, DayOfWeek.WEDNESDAY);
        day.put(6, DayOfWeek.THURSDAY);
        day.put(7, DayOfWeek.FRIDAY);

        return startDate.with(TemporalAdjusters.next(day.get(fireDay)));
    }

    public static Map<String, Integer> getExecutionTime(String startTime, String endTime, int timeDelay){

        Map<String, Integer> map = new HashMap<>();

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time1 = LocalTime.parse(startTime, formatter);
            time1 = time1.plusMinutes(timeDelay);

            LocalTime time2 = LocalTime.parse(endTime, formatter);

            map.put("startHour", time1.getHour());
            map.put("startMinute", time1.getMinute());
            map.put("endHour", time2.getHour());
            map.put("endMinute", time2.getMinute());
        }
        catch (Exception e){
            log.error("{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return map;
    }

    public static Date getExecutionDateTime(int hour, int minute){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    public static LocalDate deferDateByOneDay(LocalDate currentDate){

        return currentDate.plusDays(1);
    }

    public static LocalDate deferDateByOneWeek(LocalDate currentDate){

        return currentDate.plusWeeks(1);
    }

    public static LocalDate deferDateByOneMonth(LocalDate currentDate){

        return currentDate.plusMonths(1);
    }

    public static LocalDate toDate(String dateString){

        return LocalDate.parse(dateString);
    }

    public static Date toExpiryDate(String dateString){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try{
            return df.parse(dateString);
        }
        catch (Exception e){
            log.error("{}", e.getMessage());
        }

        return null;
    }
}
