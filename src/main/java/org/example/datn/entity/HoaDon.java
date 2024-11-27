package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDon extends CommonEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_nguoi_dung")
    private Long idNguoiDung;

    @Column(name = "id_dia_chi_giao_hang")
    private Long idDiaChiGiaoHang;

    @Column(name = "id_phuong_thuc_van_chuyen")
    private Long idPhuongThucVanChuyen;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_dat_hang")
    private LocalDateTime ngayDatHang;

    @Column(name = "ngay_thanh_toan")
    private LocalDateTime ngayThanhToan;

    @Column(name = "tong_tien", precision = 10, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "diem_su_dung")
    private Integer diemSuDung;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ly_do_huy")
    private String lyDoHuy;

    @Column(name = "trang_thai_doi_tra")
    private Integer trangThaiDoiTra;
}