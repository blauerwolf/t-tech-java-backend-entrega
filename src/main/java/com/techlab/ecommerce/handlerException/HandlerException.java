package com.techlab.ecommerce.handlerException;

import com.techlab.ecommerce.dto.response.ExceptionResponseDTO;
import com.techlab.ecommerce.exception.TechlabException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    // -----------------------------
    // TU EXCEPCIÓN PERSONALIZADA
    // -----------------------------
    @ExceptionHandler(TechlabException.class)
    public ResponseEntity<ExceptionResponseDTO> techlabException(TechlabException e) {
        log.error("Techlab exception: {}", e.getMessage(), e);

        ExceptionResponseDTO dto = ExceptionResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(e.getStatus().value())
                .title(e.getTitle())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(e.getStatus()).body(dto);
    }


    // -----------------------------
    // ERRORES DE @Valid EN DTOs
    // -----------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> validationErrors(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> {
                    if (err instanceof FieldError fe) {
                        return fe.getField() + ": " + fe.getDefaultMessage();
                    }
                    return err.getDefaultMessage();
                })
                .collect(Collectors.toList());

        ExceptionResponseDTO dto = ExceptionResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Validation Error")
                .message("Some fields are invalid. Check the error list.")
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(dto);
    }


    // -----------------------------
    // ERRORES DE VALIDACIÓN A NIVEL ENTIDAD (JPA/Hibernate)
    // -----------------------------
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDTO> constraintViolation(ConstraintViolationException ex) {

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());

        ExceptionResponseDTO dto = ExceptionResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid Data")
                .message("Entity validation failed.")
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(dto);
    }


    // -----------------------------
    // ERRORES GENERALES
    // -----------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> defaultHandler(Exception e) {
        log.error("Internal server error: {}", e.getMessage(), e);

        ExceptionResponseDTO dto = ExceptionResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title("Internal Server Error")
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }
}
