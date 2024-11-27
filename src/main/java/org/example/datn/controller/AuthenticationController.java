package org.example.datn.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.exception.AccessDeniedException;
import org.example.datn.exception.ActionNotAllowAttemptException;
import org.example.datn.exception.InputInvalidException;
import org.example.datn.exception.NotFoundEntityException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.AuthModel;
import org.example.datn.model.request.ChangePasswordModel;
import org.example.datn.model.response.AuthInfoModel;
import org.example.datn.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController("AuthApi")
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    @Autowired
    UserProcessor userProcessor;

    @GetMapping("/facebook")
    public AuthInfoModel authByFacebook(@RequestParam("access_token") String token) throws Exception {
        return userProcessor.authByFacebook(token);
    }

    @PostMapping
    public AuthInfoModel auth(@Valid @RequestBody AuthModel model) throws Exception {
        return userProcessor.auth(model);
    }

    @PostMapping("/change-password")
    @ApiOperation("Change current user password. Required Bearer token")
    public ServiceResult changePassword(@RequestBody @Valid ChangePasswordModel model,UserAuthentication ua)
            throws NotFoundEntityException, AccessDeniedException, InputInvalidException, ActionNotAllowAttemptException {
        return userProcessor.changePassword(model, ua);
    }
}
