package org.example.datn.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HDResponse {
    private Long id;
    private String maHoaDon;
    private Long idNguoiDung;
    private BigDecimal tongTien;
    private Integer diemSuDung;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayThanhToan;
}
