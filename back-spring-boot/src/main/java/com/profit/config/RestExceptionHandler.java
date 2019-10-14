package com.profit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Collections;

@Slf4j
@ControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class RestExceptionHandler {

    private static final String PROTOCOL_ERROR_CODE = "protocol.Incorrect";

    private void overrideControllerResponseProduceType(WebRequest request) {
        request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,
                Collections.singleton(MediaType.APPLICATION_JSON_UTF8),
                RequestAttributes.SCOPE_REQUEST);
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
            MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleProtocolExceptions(WebRequest request) {
        overrideControllerResponseProduceType(request);
        return new Error(PROTOCOL_ERROR_CODE);
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleUnexpected(Throwable ex) {
        log.error("Unknown: ", ex);
    }

    @ExceptionHandler(value = {ExceptionInInitializerError.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleApplication(ExceptionInInitializerError ex) {
        log.error("Application exception: ", ex);
    }
}