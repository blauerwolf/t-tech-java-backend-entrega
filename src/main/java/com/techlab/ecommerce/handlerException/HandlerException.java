package com.techlab.ecommerce.handlerException;

import com.techlab.ecommerce.dto.response.ExceptionResponseDTO;
import com.techlab.ecommerce.exception.TechlabException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@Hidden
@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(TechlabException.class)
    public ResponseEntity<ExceptionResponseDTO> techlabException(TechlabException e) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO();
        responseDTO.setTitle(e.getTitle());
        responseDTO.setMessage(e.getMessage());
        responseDTO.setErrorCode(e.getErrorCode());

        return ResponseEntity.status(e.getStatus()).body(responseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> defaultHandler(TechlabException e) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO();
        responseDTO.setTitle("There was a problem with the server");
        responseDTO.setMessage(e.getMessage());
        responseDTO.setErrorCode(e.getErrorCode());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }
}
