package org.example.datn.exception;

import org.example.datn.model.ErrorModel;

import java.util.function.Supplier;

public class NotSupportedException extends CommonException{
    private NotSupportedException(String message) {
        super(message);
    }

    private NotSupportedException(ErrorModel errorModel) {
        super(errorModel);
    }

    public static NotSupportedException of(String message) {
        return new NotSupportedException(message);
    }

    public static NotSupportedException of(ErrorModel errorModel) {
        return new NotSupportedException(errorModel);
    }

    public static Supplier<NotSupportedException> ofSupplier(String message) {
        return () -> of(message);
    }

    public static Supplier<NotSupportedException> ofSupplier(ErrorModel errorModel) {
        return () -> of(errorModel);
    }
}
