package org.example.datn.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AuthModel {

    @NotBlank(message = "username.required")
    String username;

    @NotBlank(message = "password.required")
    String password;

    String ipAddress;

    boolean rememberMe;

    public AuthModel() {
    }

    public AuthModel(String username, String password, String ipAddress, boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.ipAddress = ipAddress;
        this.rememberMe = rememberMe;
    }
}
