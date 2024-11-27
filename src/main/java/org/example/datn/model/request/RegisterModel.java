package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.UserRoles;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterModel {
    @Length(max = 50, message = "name.length.invalid")
    String name;

    @NotBlank(message = "email.required")
    @Email(message = "email.format.invalid")
    String email;

    @Pattern(regexp = "^[0-9]{10,20}$", message = "phone.format.invalid")
    String phone;

    @NotBlank(message = "password.required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*([0-9]|[^0-9dA-Za-z])).{8,100}$", message = "password.weak.security")
    String password;

    @NotBlank(message = "password.not-matched")
    String retypePassword;

    LocalDate ngaySinh;

    UserRoles role;

}
