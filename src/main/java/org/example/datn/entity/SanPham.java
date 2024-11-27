package org.example.datn.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hoangKhong
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "san_pham")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPham extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_danh_muc")
    private Long idDanhMuc;

    @Column(name = "id_thuong_hieu")
    private Long idThuongHieu;

    @Column(name = "id_chat_lieu")
    private Long idChatLieu;

    @Column(name = "ten", length = 200)
    private String ten;

    @Column(name = "ma", length = 200)
    private String ma;

    @Column(name = "xuat_xu", length = 200)
    private String xuatXu;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "gia", precision = 10, scale = 2)
    private BigDecimal gia;

    @Column(name = "anh", columnDefinition = "TEXT")
    private String anh;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
