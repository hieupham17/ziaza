package org.example.datn.entity;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author hoangKhong
 */
@Entity
@Table(name = "nhom_chuc_nang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhomChucNang extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_nhom")
    private Long idNhom;

    @Column(name = "id_chuc_nang")
    private Long idChucNang;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
