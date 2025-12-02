package com.techlab.ecommerce.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ExceptionResponseDTO {
    private LocalDateTime timestamp;
    private int status;
    private String title;
    private String message;
    private List<String> errors;
}
