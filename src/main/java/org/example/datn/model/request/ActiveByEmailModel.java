package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActiveByEmailModel extends ActiveModel {

    @NotBlank(message = "email.required")
    @Email(message = "email.format.invalid")
    String email;
}
