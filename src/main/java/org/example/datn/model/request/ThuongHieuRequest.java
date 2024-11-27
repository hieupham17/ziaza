package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThuongHieuRequest extends CommomRequest{

    private String ten;
    private String moTa;
    private Integer trangThai;

}
