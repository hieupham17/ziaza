package org.example.datn.transformer;

import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.KhuyenMai;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.model.request.KhuyenMaiCreateUpdateRequest;
import org.example.datn.model.request.KhuyenMaiRequest;
import org.example.datn.model.response.DanhMucModel;
import org.example.datn.model.response.KhuyenMaiModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface KhuyenMaiTransformer {

    KhuyenMaiModel toModel(KhuyenMai khuyenMai);

    KhuyenMai toEntity(KhuyenMaiRequest request);

    KhuyenMai toEntity(KhuyenMaiCreateUpdateRequest request);
}
