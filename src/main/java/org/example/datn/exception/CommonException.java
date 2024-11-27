package org.example.datn.exception;

import lombok.Getter;
import org.example.datn.model.ErrorModel;

public abstract class CommonException extends Exception{
    @Getter
    private final ErrorModel errorModel;

    protected CommonException(String message) {
        super(message);
        errorModel = null;
    }

    protected CommonException(ErrorModel errorModel) {
        super(errorModel.getMessage());
        this.errorModel = errorModel;
    }
}
