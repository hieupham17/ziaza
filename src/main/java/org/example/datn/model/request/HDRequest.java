package org.example.datn.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class HDRequest {
    private Long idCustomer;
    private String ma;
    private BigDecimal totalBill;
    private Integer trangThai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayThanhToan;
    private Long nguoiTao;
    private Integer diemSuDung;
    private List<HDCTRequest> items;
    private Long idDiaChiGiaoHang;
    private Long idPhuongThucVanChuyen;
    private Long nguoiCapNhat;
}
