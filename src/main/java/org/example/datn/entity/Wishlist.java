package org.example.datn.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "san_pham_yeu_thich")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_san_pham")
    private Long productId;

    @Column(name = "id_nguoi_dung")
    private Long userId;

    @Column(name = "trang_thai", nullable = false, columnDefinition = "int default 1")
    private Integer status = 1;

    @Column(name = "ngay_tao", columnDefinition = "datetime default getdate()")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat", columnDefinition = "datetime default getdate()")
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Column(name = "nguoi_tao")
    private Long createdBy;

    @Column(name = "nguoi_cap_nhat")
    private Long updatedBy;
}
