package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.exception.DuplicatedException;
import org.example.datn.exception.InputInvalidException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.request.RegisterModel;
import org.example.datn.processor.UserProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("RegisterPublicApi")
@RequestMapping("/register")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterController {

    UserProcessor processor;

    @PostMapping
    public ServiceResult register(@Valid @RequestBody RegisterModel model) throws InputInvalidException, DuplicatedException {
        return processor.register(model);
    }

}
