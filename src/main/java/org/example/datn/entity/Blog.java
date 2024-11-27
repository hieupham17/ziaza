package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "blog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Blog extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "tac_gia")
    private String tacGia;
    @Column(name = "tieu_de")
    private String tieuDe;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "hinh_anh")
    private String hinhAnh;
    @Column(name = "trang_thai")
    private Integer trangThai;
}