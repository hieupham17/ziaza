package org.example.datn.entity;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author hoangKhong
 */
@Entity
@Table(name = "chuc_nang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChucNang extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_cha")
    private Long idCha;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ma")
    private String ma;

    @Column(name = "loai")
    private Integer loai;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
