package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "phuong_thuc_van_chuyen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhuongThucVanChuyen extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ten", length = 200)
    private String ten;

    @Column(name = "mo_ta", length = 500)
    private String moTa;

    @Column(name = "phi_van_chuyen", precision = 10, scale = 2)
    private BigDecimal phiVanChuyen;

    @Column(name = "loai")
    private Integer loai;

    @Column(name = "ghi_chu", length = 250)
    private String ghiChu;

    @Column(name = "thoi_gian_giao_hang", length = 100)
    private String thoiGianGiaoHang;

    @Column(name = "trang_thai")
    private Integer trangThai;

}