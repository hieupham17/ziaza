package org.example.datn.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonChiTietModel {

    private Long id;
    private Long idHoaDon;
    private Long idSanPhamChiTiet;
    private Integer soLuong;
    private BigDecimal gia;
    private Integer trangThai;
    private Integer trangThaiDoiTra;

    private HoaDonModel hoaDonModel;
    private SanPhamChiTietModel sanPhamChiTietModel;
}
