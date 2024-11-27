package org.example.datn.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.ServiceResult;
import org.example.datn.processor.ChucNangProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ChucNangApi")
@RequestMapping("/function")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChucNangController {

    @Autowired
    ChucNangProcessor processor;

    @GetMapping("/list")
    @ApiOperation("Get all list tree view functions")
    public ServiceResult get() {
        return processor.get();
    }
}
