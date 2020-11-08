package com.isa.epharm.controller.exception;

import com.isa.epharm.controller.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        log.error("CustomException handled. Message - {}", ex.getLocalizedMessage());
        return handleException(ZonedDateTime.now(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Collections.emptyList());
    }

    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Exception handled. Message - {}", ex.getLocalizedMessage());
        return handleException(ZonedDateTime.now(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<String> additionalInfo = ex.getBindingResult().getAllErrors().stream()
                .map(e -> ((FieldError) e).getField() + " " + e.getDefaultMessage())
                .collect(Collectors.toList());
        return handleException(ZonedDateTime.now(), ex.getMessage(), status, additionalInfo);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        return handleException(ZonedDateTime.now(), ex.getLocalizedMessage(), status, Collections.emptyList());
    }

    private ResponseEntity<Object> handleException(ZonedDateTime time, String message, HttpStatus status, List<String> additionalInfo) {
        log.info("Handling error - {}", message);
        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.builder()
                        .statusCode(status.value())
                        .dateTime(time)
                        .message(message)
                        .data(additionalInfo)
                        .build()
                );
    }

}
