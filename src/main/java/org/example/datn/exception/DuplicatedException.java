package org.example.datn.exception;

import org.example.datn.model.ErrorModel;

import java.util.function.Supplier;

public class DuplicatedException extends CommonException {
    private DuplicatedException(String message) {
        super(message);
    }

    private DuplicatedException(ErrorModel errorModel) {
        super(errorModel);
    }

    public static DuplicatedException of(String message) {
        return new DuplicatedException(message);
    }

    public static DuplicatedException of(ErrorModel errorModel) {
        return new DuplicatedException(errorModel);
    }

    public static Supplier<DuplicatedException> ofSupplier(String message) {
        return () -> of(message);
    }

    public static Supplier<DuplicatedException> ofSupplier(ErrorModel errorModel) {
        return () -> of(errorModel);
    }
}