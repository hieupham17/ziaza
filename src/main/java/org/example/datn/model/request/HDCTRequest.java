package org.example.datn.model.request;

import lombok.Data;
import org.example.datn.entity.SanPhamChiTiet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class HDCTRequest {
    private Long id;
    private Integer soLuong;
    private BigDecimal gia;
    private Integer trangThai;
    private LocalDateTime ngayTao;
}
