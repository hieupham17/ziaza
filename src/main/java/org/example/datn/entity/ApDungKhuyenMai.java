package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ap_dung_khuyen_mai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApDungKhuyenMai extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idKhuyenMai;
    private Long idSanPham;
    private Long idNguoiDung;
    private BigDecimal giaTriGiam;
    private LocalDateTime ngayApDung;
    private Integer trangThai; //0 = không áp dụng, 1 = đã áp dụng
    private Boolean daSuDung = false;
}
