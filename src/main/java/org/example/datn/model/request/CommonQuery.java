package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonQuery {
    @Valid
    @Min(value = 1, message = "page.min")
    Integer page = 1;

    @Valid
    @Min(value = 1, message = "page.size.min")
    Integer size = 20;
    String keyword;
}
