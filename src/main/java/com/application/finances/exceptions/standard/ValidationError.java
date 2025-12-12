package com.application.finances.exceptions.standard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidationError extends StandardError {
    private final List<FieldMessage> errors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}

// Classe auxiliar simples para erro de campo
@Data
@AllArgsConstructor
class FieldMessage implements Serializable {
    private String fieldName;
    private String message;
}