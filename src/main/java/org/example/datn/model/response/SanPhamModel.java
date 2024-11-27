package org.example.datn.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.*;
import org.example.datn.model.CommonModel;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPhamModel extends CommonModel {

    Long id;

    Long idDanhMuc;

    Long idThuongHieu;

    Long idChatLieu;

    String ten;

    String ma;

    String xuatXu;

    String moTa;

    BigDecimal gia;

    String anh;

    Integer trangThai;



    DanhMuc danhMuc;
    Thuonghieu thuonghieu;
    ChatLieu chatLieu;


    private List<MultipartFile> images;
    private List<Size> listSize;
    private List<MauSac> listMauSac;
    private List<SanPhamChiTiet> listSanPhamChiTiet;

    public void setListSanPhamChiTiet(List<SanPhamChiTiet> spctList) {
        this.listSanPhamChiTiet = spctList;
    }

    private List<HinhAnh> hinhAnhList;
}
