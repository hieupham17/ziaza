package org.example.datn.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.CommonModel;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogModel extends CommonModel {

    private Long id;
    private String tacGia;
    private String tieuDe;
    private String moTa;
    private String hinhAnh;
    private Integer trangThai;

    private UserModel userModel;



}