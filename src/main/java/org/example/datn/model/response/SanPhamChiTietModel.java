package org.example.datn.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.ChatLieu;
import org.example.datn.entity.MauSac;
import org.example.datn.entity.SanPham;
import org.example.datn.entity.Size;
import org.example.datn.model.CommonModel;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamChiTietModel extends CommonModel {
    Long id;
    Long idSanPham;
    Long idSize;
    Long idMauSac;
    Integer soLuong;
    BigDecimal gia;
    String ghiChu;
    Integer trangThai;

    Size size;
    MauSac mauSac;
    ChatLieu chatLieu;
    SanPham sanPham;

    SanPhamModel sanPhamModel;

}
