package org.example.datn.exception;

import org.example.datn.model.ErrorModel;

import java.util.function.Supplier;

public class AccessDeniedException extends CommonException {

    private AccessDeniedException(String message) {
        super(message);
    }

    private AccessDeniedException(ErrorModel errorModel) {
        super(errorModel);
    }

    public static AccessDeniedException of(String message) {
        return new AccessDeniedException(message);
    }

    public static AccessDeniedException of(ErrorModel errorModel) {
        return new AccessDeniedException(errorModel);
    }

    public static Supplier<AccessDeniedException> ofSupplier(String message) {
        return () -> of(message);
    }

    public static Supplier<AccessDeniedException> ofSupplier(ErrorModel errorModel) {
        return () -> of(errorModel);
    }
}
