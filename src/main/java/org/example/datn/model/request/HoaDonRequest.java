package org.example.datn.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HoaDonRequest {
    private List<Long> idGioHangChiTiet;
    private Long idDiaChiGiaoHang;
    private Long idPhuongThucVanChuyen;
    private Long idPhuongThucThanhToan;
    private Integer diemSuDung;
    private Integer trangThai;

    private BigDecimal giaTriVoucher;
}
