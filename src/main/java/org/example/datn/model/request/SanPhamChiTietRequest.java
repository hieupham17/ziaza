package org.example.datn.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.datn.entity.SanPhamChiTiet;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SanPhamChiTietRequest {
    private Integer id;
    private String tenSanPham;
    private String tenKichThuoc;
    private Integer tenMauSac;
    private String soLuong;
    private Float gia;
    private String tenChatLieu;
    private SanPhamChiTiet sanPhamChiTiet;
}
