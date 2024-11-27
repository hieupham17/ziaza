package org.example.datn.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.CommonModel;
import org.example.datn.model.enums.BlogType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogChiTietModel extends CommonModel {

    private Long id;
    private Long idBlog;
    private Integer thuTu;
    private String tieuDe;
    private String noiDung;
    @Enumerated(EnumType.STRING)
    private BlogType loai;
    private String hinhAnh;
    private Integer trangThai;

    private BlogModel blogModel;

}