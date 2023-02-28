package com.testapp.xmlparser.configuration;

import com.testapp.xmlparser.exception.WrongUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String FILE_NAME = "fileName";
    private static final String HOST_HEADER_NAME = "origin";


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({WrongUploadException.class})
    @ResponseBody
    public ResponseEntity<Object> handleWrongUploadException(WrongUploadException ex, WebRequest request) {

        ProblemDetail detail = buildProblemDetail(HttpStatus.BAD_REQUEST, request, ex);
        detail.setProperty(FILE_NAME, ex.getCause().getMessage());
        return ResponseEntity.of(detail).build();
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, WebRequest request, Exception ex) {
        return buildProblemDetail(status, request, ex.getMessage());
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, WebRequest request, String errorMessage) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, errorMessage);
        if (Optional.ofNullable(request.getHeader(HOST_HEADER_NAME)).isPresent()) {
            detail.setType(URI.create(request.getHeader(HOST_HEADER_NAME)));
        }
        detail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        return detail;
    }
}
