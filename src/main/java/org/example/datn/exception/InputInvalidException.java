package org.example.datn.exception;

import org.example.datn.model.ErrorModel;

import java.util.function.Supplier;

public class InputInvalidException extends CommonException {

    private InputInvalidException(String message) {
        super(message);
    }

    private InputInvalidException(ErrorModel errorModel) {
        super(errorModel);
    }

    public static InputInvalidException of(String message) {
        return new InputInvalidException(message);
    }

    public static InputInvalidException of(ErrorModel errorModel) {
        return new InputInvalidException(errorModel);
    }

    public static Supplier<InputInvalidException> ofSupplier(String message) {
        return () -> of(message);
    }

    public static Supplier<InputInvalidException> ofSupplier(ErrorModel errorModel) {
        return () -> of(errorModel);
    }
}
