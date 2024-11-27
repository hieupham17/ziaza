package org.example.datn.exception;

import org.example.datn.model.ErrorModel;

import java.util.function.Supplier;

public class NotFoundEntityException extends CommonException {

    private NotFoundEntityException(String message) {
        super(message);
    }

    private NotFoundEntityException(ErrorModel errorModel) {
        super(errorModel);
    }

    public static NotFoundEntityException of(String message) {
        return new NotFoundEntityException(message);
    }

    public static NotFoundEntityException of(ErrorModel errorModel) {
        return new NotFoundEntityException(errorModel);
    }

    public static Supplier<NotFoundEntityException> ofSupplier(String message) {
        return () -> of(message);
    }

    public static Supplier<NotFoundEntityException> ofSupplier(ErrorModel errorModel) {
        return () -> of(errorModel);
    }
}