package org.example.datn.transformer;

import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.Thuonghieu;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.model.request.ThuongHieuRequest;
import org.example.datn.model.response.DanhMucModel;
import org.example.datn.model.response.ThuongHieuModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ThuongHieuTransformer {

    ThuongHieuModel toModel(Thuonghieu thuonghieu);

     Thuonghieu toEntity(ThuongHieuRequest request);

}
