package org.example.datn.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DiaChiGiaoHangRequest {

    @NotBlank(message = "Họ tên không được để trống.")
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự.")
    private String hoTen;

    @NotBlank(message = "Số điện thoại không được để trống.")
    @Size(min = 10, max = 15, message = "Số điện thoại phải có từ 10 đến 15 ký tự.")
    private String sdt;

    @NotBlank(message = "Địa chỉ không được để trống.")
    private String diaChi;

    @NotBlank(message = "Thành phố không được để trống.")
    private String thanhPho;

    @NotBlank(message = "Quốc gia không được để trống.")
    private String quocGia;

}
