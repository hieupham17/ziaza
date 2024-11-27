package org.example.datn.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.CommonEntity;
import org.example.datn.model.CommonModel;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThuongHieuModel extends CommonModel {
    private Long id;
    private String ten;
    private String moTa;
    private Integer trangThai;

}
