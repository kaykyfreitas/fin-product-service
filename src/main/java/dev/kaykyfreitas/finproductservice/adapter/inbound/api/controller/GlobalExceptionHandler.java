package dev.kaykyfreitas.finproductservice.adapter.inbound.api.controller;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.DefaultErrorResponse;
import dev.kaykyfreitas.finproductservice.domain.exception.DomainException;
import dev.kaykyfreitas.finproductservice.domain.exception.NotFoundException;
import dev.kaykyfreitas.finproductservice.domain.utils.InstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleNotFoundException(final NotFoundException ex) {
        log.error("NotFoundException: ", ex);
        final var error = new DefaultErrorResponse(
                InstantUtils.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getErrors()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<DefaultErrorResponse> handleDomainException(final DomainException ex) {
        log.error("DomainException: ", ex);
        final var error = new DefaultErrorResponse(
                InstantUtils.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getErrors()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleException(final Exception ex) {
        log.error("Exception: ", ex);
        final var error = new DefaultErrorResponse(
                InstantUtils.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<DefaultErrorResponse> handleRuntimeException(final RuntimeException ex) {
        log.error("RunTimeException: ", ex);
        final var error = new DefaultErrorResponse(
                InstantUtils.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
