package org.example.datn.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.datn.model.CommonModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaChiGiaoHangModel extends CommonModel {

    private Long id;
    private Long idNguoiDung;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String thanhPho;
    private String quocGia;
    private Integer trangThai;

    private UserModel userModel;
}
