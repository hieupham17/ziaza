package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dia_chi_giao_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiaChiGiaoHang extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_nguoi_dung")
    private Long idNguoiDung;
    @Column(name = "ho_va_ten")
    private String hoTen;
    @Column(name = "sdt")
    private String sdt;
    @Column(name = "dia_chi")
    private String diaChi;
    @Column(name = "thanh_pho")
    private String thanhPho;
    @Column(name = "quoc_gia")
    private String quocGia;
    @Column(name = "trang_thai")
    private Integer trangThai;
}