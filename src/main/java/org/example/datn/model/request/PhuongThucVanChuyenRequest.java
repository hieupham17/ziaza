package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhuongThucVanChuyenRequest {

    private String ten;

    private String moTa;

    private BigDecimal phiVanChuyen;

    private Integer loai;

    private String ghiChu;

    private String thoiGianGiaoHang;

}
