package org.example.datn.transformer;

import org.example.datn.entity.SanPham;
import org.example.datn.model.response.SanPhamModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface SanPhamTransformer {
    SanPhamModel toModel(SanPham sanPham);
}
