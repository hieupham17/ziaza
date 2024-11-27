package org.example.datn.transformer;

import org.example.datn.entity.SanPhamChiTiet;
import org.example.datn.model.response.SanPhamChiTietModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface SanPhamChiTietTransformer {
    SanPhamChiTietModel toModel(SanPhamChiTiet sanPhamChiTiet);
}
