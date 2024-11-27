package org.example.datn.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.datn.entity.SanPham;
import org.example.datn.entity.SanPhamChiTiet;
import org.example.datn.model.CommonModel;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GioHangChiTietModel extends CommonModel {

    private Long id;
    private Long idGioHang;
    private Long idSanPhamChiTiet;
    private Integer soLuong;
    private BigDecimal gia;
    private Integer trangThai;

    private SanPhamModel sanPham;
    private SanPhamChiTietModel sanPhamChiTiet;
}
