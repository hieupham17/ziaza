package org.example.datn.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUpdateGroupModel {
    @NotBlank(message = "name.required")
    private String ten;

    //    @NotBlank(message = "describe.required")
    private String moTa;

    @NotNull(message = "list.function.required")
    List<Long> chucNangId;

    @NotNull(message = "user.function.required")
    List<Long> userId;
}
