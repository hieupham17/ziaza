package org.example.datn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


    @Entity
    @Table(name = "diem_tich_luy")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class DiemTichLuy {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "id_nguoi_dung")
        private Long idNguoiDung;

        @Column(name = "diem")
        private Integer diem;

        @Column(name = "ly_do")
        private String lyDo;

        @Column(name = "trang_thai")
        private int trangThai;

        @Column(name = "ngay_tao")
        private LocalDateTime ngayTao = LocalDateTime.now();

        @Column(name = "ngay_cap_nhat")
        private LocalDateTime ngayCapNhat = LocalDateTime.now();

        @Column(name = "nguoi_tao")
        private Long nguoiTao;

        @Column(name = "nguoi_cap_nhat")
        private Long nguoiCapNhat;

    }

