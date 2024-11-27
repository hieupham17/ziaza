package org.example.datn.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author hoangKhong
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "san_pham_doi_tra")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamDoiTra extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_san_pham_chi_tiet")
    private Long idSPCT;

    @Column(name = "id_yeu_cau_doi_tra")
    private Long idYeuCauDoiTra;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "ly_do")
    private String lyDo;

    @Column(name = "loai_yeu_cau")
    private String loai;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
