package org.example.datn.transformer;

import org.example.datn.entity.DiaChiGiaoHang;
import org.example.datn.model.enums.UserStatus;
import org.example.datn.model.request.DiaChiGiaoHangRequest;
import org.example.datn.model.response.DiaChiGiaoHangModel;
import org.example.datn.utils.CalendarUtil;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DiaChiGiaoHangTransformer {
    DiaChiGiaoHangModel toModel(DiaChiGiaoHang diaChiGiaoHang);
    DiaChiGiaoHang toEntity(DiaChiGiaoHangRequest request);
}
