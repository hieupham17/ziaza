package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.GioiTinh;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileRequest {

    private String hoVaTen;
    @NotBlank(message = "email.required")
    @Email(message = "email.format.invalid")
    private String email;
    @NotBlank(message = "password.required")
    @Pattern(regexp = "^[0-9]{10,20}$", message = "phone.format.invalid")
    private String phone;
    private String cccd;
    private GioiTinh gioiTinh;
    private String ngaySinh;
}
