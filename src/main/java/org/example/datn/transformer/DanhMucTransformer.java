package org.example.datn.transformer;

import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.HoaDon;
import org.example.datn.entity.PhuongThucVanChuyen;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.model.request.PhuongThucVanChuyenRequest;
import org.example.datn.model.response.DanhMucModel;
import org.example.datn.model.response.HoaDonModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
@Mapper(componentModel = "spring")
public interface DanhMucTransformer {

    DanhMucModel toModel(DanhMuc danhMuc);

    static DanhMuc toEntity(DanhMucRequest request) {
        DanhMuc danhMuc = new DanhMuc();
        danhMuc.setTen(request.getTen()); // Đảm bảo rằng trường 'ten' được gán
        danhMuc.setMoTa(request.getMoTa());
        danhMuc.setTrangThai(request.getTrangThai());
        danhMuc.setIdCha(request.getIdCha());
        danhMuc.setNguoiTao(request.getNguoiTao());
        danhMuc.setNguoiCapNhat(request.getNguoiCapNhat());
        danhMuc.setNgayTao(request.getNgayTao());
        danhMuc.setNgayCapNhat(request.getNgayCapNhat());

        return danhMuc;
    }

}
