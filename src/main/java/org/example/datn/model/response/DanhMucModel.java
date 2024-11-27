package org.example.datn.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.CommonEntity;
import org.example.datn.model.CommonModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhMucModel extends CommonModel {
    private Long id;
    private Long idCha;
    private String ten;
    private String moTa;
    private Integer trangThai;
}
