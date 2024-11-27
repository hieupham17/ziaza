package org.example.datn.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.HoaDon;
import org.example.datn.entity.SanPhamChiTiet;
import org.example.datn.entity.User;
import org.example.datn.entity.YeuCauDoiTra;
import org.example.datn.model.CommonModel;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YeuCauDoiTraChiTietModel extends CommonModel {

     Long id;
     Long idSPCT;
     Long idYeuCauDoiTra;
     Integer soLuong;
     Integer trangThai;

     YeuCauDoiTra yeuCauDoiTra;
     SanPhamChiTietModel sanPhamChiTiet;

}
