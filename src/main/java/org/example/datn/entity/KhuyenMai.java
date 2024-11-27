package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "khuyen_mai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhuyenMai extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "mo_ta")
    private String moTa;

    // 1 = Khuyến mãi cho sản phẩm (giảm giá trực tiếp cho sản phẩm)
    // 2 = Khuyến mãi cho người dùng (mã giảm giá)
    @Column(name = "loai")
    private Integer loai;

    @Column(name = "gia_tri")
    private BigDecimal giaTri;

    @Column(name = "ngay_bat_dau")
    private LocalDateTime ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "trang_thai")
    private Integer trangThai;
/*
0 = Chưa kích hoạt (khuyến mãi chưa bắt đầu).
1 = Đang áp dụng (khuyến mãi đang trong khoảng thời gian áp dụng).
2 = Hết hạn (khuyến mãi đã kết thúc).
3 = Bị hủy (khuyến mãi đã bị hủy bỏ hoặc không áp dụng nữa).
 */
}