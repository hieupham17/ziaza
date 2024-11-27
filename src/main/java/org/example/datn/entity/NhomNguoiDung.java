package org.example.datn.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * @author hoangKhong
 */
@Entity
@Table(name = "nhom_nguoi_dung")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhomNguoiDung extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_nhom")
    private Long idNhom;

    @Column(name = "id_nguoi_dung")
    private Long userId;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
