package org.example.datn.entity;


import io.swagger.models.auth.In;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hoangKhong
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yeu_cau_doi_tra_chi_tiet")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YeuCauDoiTraChiTiet extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_yeu_cau_doi_tra")
    private Long idYeuCauDoiTra;

    @Column(name = "id_san_pham_chi_tiet")
    private Long idSPCT;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
