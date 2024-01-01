package com.ibcs.salaryapp.util;

import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;

public class DateTimeConstant {

//    public static final String DDMMYYYY_HYPHEN_DATE = "dd-MM-YYYY";
    public static final String YYYYMDD_HYPHEN = "yyyy-M-dd HH:mm:ss";
    public static final String YYYYMMDD_HYPHEN = "yyyy-MM-dd HH:mm:ss";
    public static final String DDMMYYYY_HYPHEN = "dd-MM-YYYY HH:mm:ss";
    public static final String HOUR_MINUTE = "HH:mm";
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";
    public static final Map<String, DateTimeFormatterBuilder> DATE_TIME_FORMATERS = new HashMap<String, DateTimeFormatterBuilder>() {
        {
            put(YYYYMDD_HYPHEN, new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendPattern("yyyy")
                    .appendLiteral("-")
                    .appendPattern("M")
                    .appendLiteral("-")
                    .appendPattern("dd").appendLiteral(" ").appendPattern("HH").appendLiteral(":").appendPattern("mm").appendLiteral(":").appendPattern("ss"));

            put(YYYYMMDD_HYPHEN, new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendPattern("yyyy")
                    .appendLiteral("-")
                    .appendPattern("MM")
                    .appendLiteral("-")
                    .appendPattern("dd").appendLiteral(" ").appendPattern("HH").appendLiteral(":").appendPattern("mm").appendLiteral(":").appendPattern("ss"));

            put(DDMMYYYY_HYPHEN, new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendPattern("dd")
                    .appendLiteral("-")
                    .appendPattern("MM")
                    .appendLiteral("-")
                    .appendPattern("yyyy").appendLiteral(" ").appendPattern("HH").appendLiteral(":").appendPattern("mm").appendLiteral(":").appendPattern("ss"));

        }

    };

    public static final Map<String, DateTimeFormatterBuilder> DATE_FORMATERS = new HashMap<String, DateTimeFormatterBuilder>() {
        {
            put(YYYYMDD_HYPHEN, new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendPattern("yyyy")
                    .appendLiteral("-")
                    .appendPattern("M")
                    .appendLiteral("-")
                    .appendPattern("dd"));

            put(YYYYMMDD_HYPHEN, new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendPattern("yyyy")
                    .appendLiteral("-")
                    .appendPattern("MM")
                    .appendLiteral("-")
                    .appendPattern("dd"));

            put(DDMMYYYY_HYPHEN, new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendPattern("dd")
                    .appendLiteral("-")
                    .appendPattern("MM")
                    .appendLiteral("-")
                    .appendPattern("yyyy"));

        }

    };

    public static final String ISO_DATE_FORMAT_WITHOUT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss";
//    public static final String ISO_DATE_FORMAT_WITHZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSxxx";
}
