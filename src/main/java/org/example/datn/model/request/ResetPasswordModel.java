package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordModel {
    @NotBlank(message = "active-token.invalid")
    String activeToken;

    @NotBlank(message = "password.required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*([0-9]|[^0-9dA-Za-z])).{8,100}$", message = "password.weak.security")
    String password;

    @NotBlank(message = "password.not-matched")
    String retypePassword;
}
