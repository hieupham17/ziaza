package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.TypeThanhToan;

import javax.persistence.*;

@Entity
@Table(name = "phuong_thuc_thanh_toan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhuongThucThanhToan extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten")
    private String ten;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai")
    private TypeThanhToan loai; // COD hoặc VNPAY

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
