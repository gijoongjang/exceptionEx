package com.exception.exception.advice;

import com.exception.exception.common.STATUSCODE;
import com.exception.exception.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponseDTO> defaultException(HttpServletRequest request, Exception e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(STATUSCODE.INTERNAL_ERROR)
                .errorMessage(e.getMessage())
                .path(request.getRequestURI()).build();

        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ErrorResponseDTO> methodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(STATUSCODE.BAD_REQUEST)
                .errorMessage(e.getMessage())
                .path(request.getRequestURI()).build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponseDTO> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder();
        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("]은(는)");
            sb.append(fieldError.getDefaultMessage());
            sb.append(" 입력된 값: [");
            sb.append(fieldError.getRejectedValue());
            sb.append("]");
        }

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(STATUSCODE.BAD_REQUEST)
                .errorMessage(sb.toString())
                .path(request.getRequestURI()).build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {BindException.class})
    protected ResponseEntity<ErrorResponseDTO> bindException(HttpServletRequest request, BindException  e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder();
        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("]은(는)");
            sb.append(fieldError.getDefaultMessage());
            sb.append(" 입력된 값: [");
            sb.append(fieldError.getRejectedValue());
            sb.append("]");
        }

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(STATUSCODE.BAD_REQUEST)
                .errorMessage(sb.toString())
                .path(request.getRequestURI()).build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<ErrorResponseDTO> authenticationException(HttpServletRequest request, AuthenticationException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(STATUSCODE.UNAUTHORIZED)
                .errorMessage(e.getMessage())
                .path(request.getRequestURI()).build();

        return ResponseEntity.status(STATUSCODE.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<ErrorResponseDTO> accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(STATUSCODE.FORBIDDEN)
                .errorMessage(e.getMessage())
                .path(request.getRequestURI()).build();

        return ResponseEntity.status(STATUSCODE.FORBIDDEN).body(response);
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    protected ResponseEntity<ErrorResponseDTO> noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .errorCode(STATUSCODE.NOT_FOUND)
                .errorMessage(e.getMessage())
                .path(request.getRequestURI()).build();

        return ResponseEntity.status(STATUSCODE.NOT_FOUND).body(response);
    }
}
