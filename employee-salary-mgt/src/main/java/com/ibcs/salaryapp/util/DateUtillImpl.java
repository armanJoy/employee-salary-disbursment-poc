package com.ibcs.salaryapp.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class DateUtillImpl implements DateUtill {

    public static LocalTime convertTimeBetweenZone(LocalTime time, String sourceZone, String targetZone) {

        ZonedDateTime startTimeInUserZone = LocalDateTime.of(LocalDate.now(), time).atZone(ZoneId.of(sourceZone));
        LocalDateTime startTimeInGmtZone = startTimeInUserZone.withZoneSameInstant(ZoneId.of(targetZone)).toLocalDateTime();

        return startTimeInGmtZone.toLocalTime();
    }

    public static LocalDateTime convertDateTimeBetweenZone(LocalDateTime dateTime, String sourceZone, String targetZone) {

        ZonedDateTime startTimeInUserZone = dateTime.atZone(ZoneId.of(sourceZone));
        LocalDateTime startTimeInGmtZone = startTimeInUserZone.withZoneSameInstant(ZoneId.of(targetZone)).toLocalDateTime();

        return startTimeInGmtZone;
    }

    @Override
    public String dateToString(LocalDateTime localDateTime) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = localDateTime.format(myFormatObj);
        return formattedDate;
    }

    @Override
    public LocalDateTime convertISODateTimeWithZoneToLocaldateTime(String timeWithZone) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(timeWithZone, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        return localDateTime;
    }

    @Override
    public LocalDateTime convertWithoutZoneDateTimeToLocalDateTime(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeConstant.ISO_DATE_FORMAT_WITHOUT_TIMEZONE);
        return LocalDateTime.parse(timestamp, formatter);
    }

    public static int calculateAge(LocalDateTime birthDate) {
        int age = 0;
        LocalDateTime currentDate = LocalDateTime.now();
        try {
            age = currentDate.getYear() - birthDate.getYear();

            if (currentDate.getMonthValue() < birthDate.getMonthValue()
                    || (currentDate.getMonthValue() == birthDate.getMonthValue()
                    && currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
                age--;
            }

        } catch (Exception e) {

        }
        return age;
    }

    public static int calculateAge(LocalDate birthDate) {
        int age = 0;
        LocalDateTime currentDate = LocalDateTime.now();
        try {
            age = currentDate.getYear() - birthDate.getYear();

            if (currentDate.getMonthValue() < birthDate.getMonthValue()
                    || (currentDate.getMonthValue() == birthDate.getMonthValue()
                    && currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
                age--;
            }

        } catch (Exception e) {

        }
        return age;
    }

    public static LocalDateTime dateStringToLocalDateTime(String dateString, String dateFormat) {
        LocalDateTime convertedLocalDateTime = null;
        if (dateString != null && DateTimeConstant.DATE_TIME_FORMATERS.containsKey(dateFormat)) {
            dateString = (dateString.length() > 10) ? dateString : dateString.concat(AppConstant.TIME_SUFFIX);
            try {
                DateTimeFormatterBuilder builder = DateTimeConstant.DATE_TIME_FORMATERS.get(dateFormat);
                DateTimeFormatter formatter = builder.toFormatter(Locale.UK);
                dateString = (dateString.length() > 10) ? dateString : dateString.concat(AppConstant.TIME_SUFFIX);
                TemporalAccessor accessor = formatter.parse(dateString);
                convertedLocalDateTime = LocalDateTime.from(accessor);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return convertedLocalDateTime;
    }

    public static LocalDate dateStringToLocalDate(String dateString, String dateFormat) {
        LocalDateTime convertedLocalDateTime = dateStringToLocalDateTime(dateString, dateFormat);

        return (convertedLocalDateTime != null) ? convertedLocalDateTime.toLocalDate() : null;
    }

    public LocalDateTime convertISODateTimeToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString, formatter);
        return offsetDateTime.toLocalDateTime();
    }

    @Override
    public boolean isValidZoneId(String zoneIdStr) {
        try {
            ZoneId.of(zoneIdStr);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public LocalTime convertLocalTimeToUtcTime(String time, String zoneId) {
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
        ZoneId localZone = ZoneId.of(zoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localTime.atDate(LocalDate.now()), localZone);
        ZonedDateTime utcZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime.toLocalTime();
    }

    @Override
    public LocalDate convertLocalDateToUtcDate(LocalDate date, String zoneId) {
        ZoneId localZone = ZoneId.of(zoneId);
        ZonedDateTime zonedDateTime = date.atStartOfDay(localZone);
        ZonedDateTime utcZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime.toLocalDate();
    }

    public static String sqlDateToFrontendDateString(Date date, String format) {
        return LocalDateTime.of(date.toLocalDate(), LocalTime.MIN).format(DateTimeConstant.DATE_TIME_FORMATERS.get(format).toFormatter());
    }

    @Override
    public LocalDateTime offsetDateTimeToLocalDateTime(String offsetDateTime) {
        OffsetDateTime timestamp = OffsetDateTime.parse(offsetDateTime);
        Instant instant = Instant.from(timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, timestamp.getOffset());
        return dateTime;
    }

    @Override
    public LocalDateTime getLocalTimeFromDate(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }

    @Override
    public String toLocalDateTimeString(String dateString) {

        if (dateString.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z")) {
            // Remove the 'Z' character
            if (dateString.endsWith("Z")) {
                dateString = dateString.substring(0, dateString.length() - 1);
            }
            System.out.println("Formatted date: " + dateString);
        } else {
            return dateString;
        }
        return dateString;
    }

    public Date getDateFromLocalDateTime(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return Date.valueOf(localDate);
    }

    @Override
    public Timestamp convertBeijingTimeToInidanTime(String deviceTime) {
        // Convert to Beijing time zone (CST - China Standard Time)
        LocalDateTime beijingDateTime = LocalDateTime.parse(deviceTime, DateTimeFormatter.ISO_DATE_TIME);

        ZonedDateTime beijingZonedDateTime = beijingDateTime.atZone(ZoneId.of("Asia/Shanghai"));

        // Convert to Indian time zone (IST - Indian Standard Time)
        ZonedDateTime indiaZonedDateTime = beijingZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

        Timestamp timestamp = Timestamp.valueOf(indiaZonedDateTime.toLocalDateTime());

        return timestamp;
    }

    public static List<String> getTimeZonesForSpecificLocalHourAndMinute(int pivotLocalHour, int pivotLocalMinute) {
        List<String> pivotTimeZones = new ArrayList<>();

        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();

        // Get the current time
        ZonedDateTime currentTime = ZonedDateTime.now();

        // Define the target time (10 PM or 22:00)
        LocalTime targetTime = LocalTime.of(pivotLocalHour, pivotLocalMinute);

        // Iterate through all time zones and check if the current time matches the target time
        for (String zoneIdStr : availableZoneIds) {
            ZoneId zoneId = ZoneId.of(zoneIdStr);
            ZonedDateTime zoneTime = currentTime.withZoneSameInstant(zoneId);

            if (zoneTime.toLocalTime().toString().startsWith(targetTime.toString())) {
                pivotTimeZones.add(zoneIdStr);
            }
        }

        return pivotTimeZones;
    }

}
