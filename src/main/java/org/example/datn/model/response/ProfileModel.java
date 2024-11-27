package org.example.datn.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.CommonModel;
import org.example.datn.model.enums.GioiTinh;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author hoangKhong
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileModel extends CommonModel {

    private Long id;
    private Long idNguoiDung;
    private String hoVaTen;
    private String phone;
    private String avatar;
    private String diaChi;
    private GioiTinh gioiTinh;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngaySinh;
    private String cccd;
    private String email;
}
