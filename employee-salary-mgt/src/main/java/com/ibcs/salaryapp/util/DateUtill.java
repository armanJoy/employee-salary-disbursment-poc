package com.ibcs.salaryapp.util;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public interface DateUtill {

    public String dateToString(LocalDateTime localDateTime);

    public LocalDateTime convertISODateTimeWithZoneToLocaldateTime(String timeWithZone);

    public LocalDateTime convertWithoutZoneDateTimeToLocalDateTime(String timestamp);

    public  boolean isValidZoneId(String zoneIdStr);

    public LocalTime convertLocalTimeToUtcTime(String time, String zoneId);

    public LocalDate convertLocalDateToUtcDate(LocalDate date,String zoneId);

//    int calculateAge(LocalDateTime birthDate);

    LocalDateTime offsetDateTimeToLocalDateTime(String offsetDateTime);

    public  LocalDateTime getLocalTimeFromDate(Date date);

    public String toLocalDateTimeString(String dateString);

    public  Date getDateFromLocalDateTime(LocalDateTime localDateTime);

    public Timestamp convertBeijingTimeToInidanTime(String deviceTime);
}
