package org.example.datn.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.LoaiYeuCau;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author hoangKhong
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yeu_cau_doi_tra")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YeuCauDoiTra extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_hoa_don")
    private Long idHoaDon;

    @Column(name = "id_nguoi_dung")
    private Long idNguoiDung;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_yeu_cau", length = 100)
    private LoaiYeuCau loai;

    @Column(name = "ly_do", length = 500)
    private String lyDo;

    @Column(name = "ghi_chu", length = 500)
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_yeu_cau")
    private LocalDateTime ngayYeuCau;

    @Column(name = "ngay_hoan_tat")
    private LocalDateTime ngayHoanTat;
}
