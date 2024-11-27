package org.example.datn.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.*;
import org.example.datn.model.CommonModel;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YeuCauDoiTraModel extends CommonModel {

     Long id;
     Long idHoaDon;
     Long idNguoiDung;
     String loai;
     String lyDo;
     String ghiChu;
     Integer trangThai;
     LocalDateTime ngayYeuCau;
     LocalDateTime ngayHoanTat;

     HoaDon hoaDon;
     UserModel user;

}
