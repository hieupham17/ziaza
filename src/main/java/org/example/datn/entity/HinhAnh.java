package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hinh_anh")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HinhAnh extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_san_pham")
    private Long idSanPham;

    @Column(name = "id_yeu_cau_doi_tra")
    private Long idYeuCauDoiTra;

    @Column(name = "anh")
    private String anh;

    @Column(name = "trang_thai")
    private Integer trangThai;


}
