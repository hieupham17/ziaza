package org.example.datn.exception;

import org.example.datn.model.ErrorModel;

import java.util.function.Supplier;

public class LockedException extends CommonException {

    private LockedException(String message) {
        super(message);
    }

    private LockedException(ErrorModel errorModel) {
        super(errorModel);
    }

    public static LockedException of(String message) {
        return new LockedException(message);
    }

    public static LockedException of(ErrorModel errorModel) {
        return new LockedException(errorModel);
    }

    public static LockedException of() {
        return of((String) null);
    }

    public static Supplier<LockedException> ofSupplier(String message) {
        return () -> of(message);
    }

    public static Supplier<LockedException> ofSupplier(ErrorModel errorModel) {
        return () -> of(errorModel);
    }

}
