package org.example.datn.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.exception.ActionNotAllowAttemptException;
import org.example.datn.exception.InputInvalidException;
import org.example.datn.exception.NotFoundEntityException;
import org.example.datn.model.request.ActiveByEmailModel;
import org.example.datn.model.request.ResetPasswordModel;
import org.example.datn.processor.OtpProcessor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("OtpApi")
@RequestMapping("/otp")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtpController {

    OtpProcessor processor;

    @GetMapping("/reset-password")
    @ApiOperation("Request send otp to reset password via email. Does not required Bearer token")
    public void getOtpResetPassword(@RequestParam String email) throws NotFoundEntityException, ActionNotAllowAttemptException {
        processor.getOtpResetPassword(email);
    }

    @PostMapping("/verify-reset-password")
    @ApiOperation("Check otp to reset user's password. Does not required Bearer token")
    public String verifyResetPassword(@Valid @RequestBody ActiveByEmailModel model)
            throws ActionNotAllowAttemptException, NotFoundEntityException {
        return processor.verifyResetPassword(model);
    }

    @PostMapping("/reset-password")
    @ApiOperation("Do reset user's password via activeToken. Does not required Bearer token")
    public void resetPassword(@Valid @RequestBody ResetPasswordModel model)
            throws NotFoundEntityException, InputInvalidException {
        processor.resetPassword(model);
    }
}
