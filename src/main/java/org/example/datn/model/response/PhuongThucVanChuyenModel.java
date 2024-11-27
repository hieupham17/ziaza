package org.example.datn.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.datn.model.CommonModel;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhuongThucVanChuyenModel extends CommonModel {

    private Long id;

    private String ten;

    private String moTa;

    private BigDecimal phiVanChuyen;

    private Integer loai;

    private String ghiChu;

    private String thoiGianGiaoHang;

    private Integer trangThai;
}
