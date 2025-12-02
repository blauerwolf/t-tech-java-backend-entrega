package com.techlab.ecommerce.handlerException;

import com.techlab.ecommerce.dto.response.ExceptionResponseDTO;
import com.techlab.ecommerce.exception.TechlabException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(TechlabException.class)
    public ResponseEntity<ExceptionResponseDTO> techlabException(TechlabException e) {
        log.error("Techlab exception: {}", e.getMessage(), e);

        ExceptionResponseDTO dto = new ExceptionResponseDTO();
        dto.setTitle(e.getTitle());
        dto.setMessage(e.getMessage());

        return ResponseEntity.status(e.getStatus()).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> defaultHandler(Exception e) {
        log.error("Internal server error: {}", e.getMessage(), e);

        ExceptionResponseDTO dto = new ExceptionResponseDTO();
        dto.setTitle("There was a problem with the server");
        dto.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }
}
