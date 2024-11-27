package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.BlogType;
import org.example.datn.model.enums.UserType;

import javax.persistence.*;

@Entity
@Table(name = "blog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogChiTiet extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_blog")
    private Long idBlog;
    @Column(name = "thu_tu")
    private Integer thuTu;
    @Column(name = "tieu_de")
    private String tieuDe;
    @Column(name = "noi_dung")
    private String noiDung;
    @Column(name = "loai")
    @Enumerated(EnumType.STRING)
    private BlogType loai;
    @Column(name = "hinh_anh")
    private String hinhAnh;
    @Column(name = "trang_thai")
    private Integer trangThai;
}