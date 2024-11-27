package org.example.datn.transformer;

import org.example.datn.entity.PhuongThucVanChuyen;
import org.example.datn.model.enums.UserStatus;
import org.example.datn.model.request.PhuongThucVanChuyenRequest;
import org.example.datn.model.response.PhuongThucVanChuyenModel;
import org.example.datn.utils.CalendarUtil;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PhuongThucVanChuyenTransformer {
    PhuongThucVanChuyen toEntity(PhuongThucVanChuyenRequest request);
    PhuongThucVanChuyenModel toModel(PhuongThucVanChuyen phuongThucVanChuyen);
}
