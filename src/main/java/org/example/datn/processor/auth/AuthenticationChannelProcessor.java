package org.example.datn.processor.auth;

import org.example.datn.exception.CommonException;
import org.example.datn.model.response.UserModel;

import java.util.function.Supplier;

public interface AuthenticationChannelProcessor {

    UserModel auth(Supplier<?> input) throws CommonException;
}
