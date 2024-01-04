package com.ibcs.salaryapp.util;

public class AppConstant {

    private AppConstant() {
    }

    public static final double HOUSE_RENT_PERCENTAGE = 20.0;
    public static final double MEDICAL_ALLOWANCE_PERCENTAGE = 15.0;

    public static final String COMPANY_BANK_AC_ID = "com-salary-ac-0001";
    public static final String BASIC_SALARY_INFO_ID = "basic-salary-info-0001";

    public static final String EMPLOYEE_ROLE = "employee";
    public static final String SYSADMIN_ROLE = "sysadmin";
    public static final String ROLE_NOT_MATCHED = "ROLE  NOT MATCHED";
    public static final String LOGOUT_URL = "logout";

    public static final String SUCCESSFUL = "SUCCESSFUL";
    public static final String UNSUCCESSFUL = "UNSUCCESSFUL";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String CONTENT_TYPE_HEADER = "application/jwt";
    public static final String ALLOW_ORIGINS = "*";
    public static final String ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    public static final String RX_PHONE_WITH_COUNTRY_CODE = "^\\+[1-9]\\d{1,14}$";
    public static final String RX_COUNTRY_CODE_SPACE_NUMBER = "^\\+\\d{2,3}\\s\\d{10}$";
    public static final String REGEX_ALPHA_NUMERIC_PUNCTUATION_TEXT = "[a-zA-Z0-9\\p{Punct}]+";
    public static final String RX_YEAR_MONTH_DAY = "^((\\d{4})-(0[1-9]|[1][0-2])-(0[1-9]|[1-2][0-9]|3[0-1])).*";
    public static final String RX_DAY_MONTH_YEAR = "^((0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|[1][0-2])-(\\d{4})).*";
    public static final String RX_DAY_MONTH_YEAR_OF_THIS_CENTURY = "^((0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|[1][0-2])-(20\\d{2})).*";
    public static final String RX_24_HOUR_TIME_WITH_OR_WITHOUT_SECOND = "^(?:[01]\\d|2[0-3]):[0-5]\\d(:[0-5]\\d)?$";
    public static final String RX_24_HOUR_TIME_WITHOUT_SECOND = "^([01][0-9]|2[0-3]):[0-5][0-9]$";
    public static final String RX_ALPHA_NUMERIC_PUNCTUATION_TEXT = "[a-zA-Z0-9\\p{Punct}]+";

    public static final String TIME_SUFFIX = " 00:00:00";

}
