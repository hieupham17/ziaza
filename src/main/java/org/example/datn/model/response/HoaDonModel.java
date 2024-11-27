package org.example.datn.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.CommonModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonModel extends CommonModel {

    private Long id;

    private Long idNguoiDung;
    private Long idDiaChiGiaoHang;
    private Long idPhuongThucVanChuyen;
    private String ma;
    private LocalDateTime ngayDatHang;
    private LocalDateTime ngayThanhToan;
    private BigDecimal tongTien;
    private Integer diemSuDung;
    private Integer trangThai;
    private Integer trangThaiDoiTra;
    private String lyDoHuy;

    private UserModel userModel;
    private DiaChiGiaoHangModel diaChiGiaoHangModel;
    private PhuongThucVanChuyenModel phuongThucVanChuyenModel;
    private List<HoaDonChiTietModel> hoaDonChiTietModels;


}