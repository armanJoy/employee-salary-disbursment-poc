package com.ibcs.salaryapp.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionLog {

    private String errorCode;

    private LocalDateTime time = LocalDateTime.now();

    private String api;

    private String severityLevel;

    private Boolean resolved = false;

    private String exceptionClass;

    private Integer lineNo;

    private Map<String, Object> param = new HashMap<>();

    private String stackTrace;

    private String exceptionMsg;

    public ExceptionLog() {
        super();
    }

    public ExceptionLog(String errorCode, String api, String severityLevel, String exceptionClass, Integer lineNo, Map<String, Object> param, String stackTrace, String exceptionMsg) {
        this.errorCode = errorCode;
        this.api = api;
        this.severityLevel = severityLevel;
        this.exceptionClass = exceptionClass;
        this.lineNo = lineNo;
        this.param = param;
        this.stackTrace = stackTrace;
        this.exceptionMsg = exceptionMsg;
    }

//    public ExceptionLog(String errorCode, String api, String severityLevel, Boolean resolved, String exceptionClass, Integer lineNo, Map<String, Object> param, String stackTrace, String exceptionMsg) {
//        this.errorCode = errorCode;
//        this.api = api;
//        this.severityLevel = severityLevel;
//        this.resolved = resolved;
//        this.exceptionClass = exceptionClass;
//        this.lineNo = lineNo;
//        this.param = param;
//        this.stackTrace = stackTrace;
//        this.exceptionMsg = exceptionMsg;
//    }
}
