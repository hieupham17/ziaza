package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActiveModel {
    @NotBlank(message = "active-code.invalid")
    @Length(min = 6, max = 6, message = "active-code.invalid")
    String activeCode;
}
