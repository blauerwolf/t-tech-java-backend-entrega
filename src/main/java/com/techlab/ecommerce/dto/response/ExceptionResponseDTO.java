package com.techlab.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponseDTO {

    private LocalDateTime timestamp;
    private int status;
    private String title;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors;
}
