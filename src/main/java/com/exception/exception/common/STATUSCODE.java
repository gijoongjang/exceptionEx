package com.exception.exception.common;

import org.springframework.http.HttpStatus;

public interface STATUSCODE {
    public static final int BAD_REQUEST    = HttpStatus.BAD_REQUEST.value();
    public static final int INTERNAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final int UNAUTHORIZED   = HttpStatus.UNAUTHORIZED.value();
    public static final int FORBIDDEN      = HttpStatus.FORBIDDEN.value();
    public static final int NOT_FOUND      = HttpStatus.NOT_FOUND.value();
}
