package com.techlab.ecommerce.handlerException;

import com.techlab.ecommerce.dto.response.ExceptionResponseDTO;
import com.techlab.ecommerce.exception.TechlabException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    // =============================================================
    //        1) EXCEPCIONES PROPIAS DEL SISTEMA (TechlabException)
    // =============================================================
    @ExceptionHandler(TechlabException.class)
    public ResponseEntity<ExceptionResponseDTO> techlabException(TechlabException e) {

        log.error("Techlab exception: {}", e.getMessage(), e);

        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                LocalDateTime.now(),
                e.getStatus().value(),
                e.getTitle(),
                e.getMessage(),
                null
        );

        return ResponseEntity.status(e.getStatus()).body(dto);
    }


    // =============================================================
    //        2) VALIDACIONES DE SPRING (@Valid en DTOs)
    // =============================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> validationError(MethodArgumentNotValidException ex) {

        log.warn("Validation error: {}", ex.getMessage());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Some fields are invalid.",
                errors
        );

        return ResponseEntity.badRequest().body(dto);
    }


    // =============================================================
    //           3) VALIDACIONES DE JPA (@NotNull, @Min, etc.)
    // =============================================================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDTO> constraintViolation(ConstraintViolationException ex) {

        log.warn("JPA constraint violation: {}", ex.getMessage());

        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Constraint Violation",
                "Some fields violate model constraints.",
                errors
        );

        return ResponseEntity.badRequest().body(dto);
    }


    // =============================================================
    //       4) ERRORES SQL (NULL, UNIQUE, FK, DEFAULT, etc.)
    // =============================================================
    @ExceptionHandler({
            DataIntegrityViolationException.class,
            SQLIntegrityConstraintViolationException.class,
            PersistenceException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleSqlErrors(Exception ex) {

        log.error("Data integrity error: {}", ex.getMessage(), ex);

        String cleaned = extractReadableSqlMessage(ex);

        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid or Missing Data",
                "Some fields violate database constraints.",
                List.of(cleaned)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }


    // =============================================================
    //               5) ENTIDAD NO ENCONTRADA (404)
    // =============================================================
    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ExceptionResponseDTO> notFound(Exception ex) {

        log.warn("Entity not found: {}", ex.getMessage());

        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }


    // =============================================================
    //               6) JSON MAL FORMADO
    // =============================================================
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public ResponseEntity<ExceptionResponseDTO> invalidJson(Exception ex) {

        log.warn("Invalid JSON: {}", ex.getMessage());

        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid JSON",
                "The provided JSON has incorrect types or format.",
                List.of(ex.getMessage())
        );

        return ResponseEntity.badRequest().body(dto);
    }


    // =============================================================
    //               7) RESTO DE ERRORES (500)
    // =============================================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> defaultHandler(Exception e) {

        log.error("Internal server error: {}", e.getMessage(), e);

        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }


    // =============================================================
    //         EXTRACTOR PARA MENSAJES SQL (MY,PG,H2,MSSQL)
    // =============================================================
    private String extractReadableSqlMessage(Throwable ex) {

        Throwable cause = ex;

        while (cause.getCause() != null) {
            cause = cause.getCause();
        }

        String message = cause.getMessage();
        if (message == null) return "Database constraint violation";


        // ---- MySQL / MariaDB / H2 "NULL not allowed" ----
        if (message.contains("NULL not allowed") || message.contains("cannot be null")) {
            int start = message.indexOf("\"");
            int end = message.indexOf("\"", start + 1);
            if (start > -1 && end > -1) {
                String col = message.substring(start + 1, end);
                return "Field '" + col.toLowerCase() + "' cannot be null.";
            }
        }

        // ---- MySQL "doesn't have a default value" ----
        if (message.contains("doesn't have a default value")) {
            return message;
        }

        // ---- Unique violation ----
        if (message.toLowerCase().contains("duplicate") ||
                message.toLowerCase().contains("unique")) {
            return "A unique field already exists.";
        }

        return message;
    }
}
