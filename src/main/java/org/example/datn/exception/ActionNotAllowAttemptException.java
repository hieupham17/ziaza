package org.example.datn.exception;

import org.example.datn.model.ErrorModel;

import java.util.function.Supplier;

public class ActionNotAllowAttemptException extends CommonException {

    private ActionNotAllowAttemptException(String message) {
        super(message);
    }

    private ActionNotAllowAttemptException(ErrorModel errorModel) {
        super(errorModel);
    }

    public static ActionNotAllowAttemptException of(String message) {
        return new ActionNotAllowAttemptException(message);
    }

    public static ActionNotAllowAttemptException of(ErrorModel errorModel) {
        return new ActionNotAllowAttemptException(errorModel);
    }

    public static Supplier<ActionNotAllowAttemptException> ofSupplier(String message) {
        return () -> of(message);
    }

    public static Supplier<ActionNotAllowAttemptException> ofSupplier(ErrorModel errorModel) {
        return () -> of(errorModel);
    }
}
