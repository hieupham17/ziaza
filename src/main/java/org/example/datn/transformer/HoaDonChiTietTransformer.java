package org.example.datn.transformer;

import org.example.datn.entity.HoaDonChiTiet;
import org.example.datn.model.response.HoaDonChiTietModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface HoaDonChiTietTransformer {
    HoaDonChiTietModel toModel(HoaDonChiTiet hoaDonChiTiet);
}
