package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.CommonModel;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhMucRequest extends CommomRequest{

    private Long idCha;
    private String ten;
    private String moTa;
    private Integer trangThai;

}
