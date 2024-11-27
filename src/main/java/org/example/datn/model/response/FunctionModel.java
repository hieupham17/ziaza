package org.example.datn.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FunctionModel {

    Long id;
    String ten;
    Long idCha;
    Boolean isChecked;
    @JsonProperty("listChucNang")
    List<FunctionModel> function = new ArrayList<>();
}
