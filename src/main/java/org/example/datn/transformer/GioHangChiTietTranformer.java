package org.example.datn.transformer;

import org.example.datn.entity.GioHangChiTiet;
import org.example.datn.model.enums.UserStatus;
import org.example.datn.model.request.GioHangChiTietRequest;
import org.example.datn.model.response.GioHangChiTietModel;
import org.example.datn.utils.CalendarUtil;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface GioHangChiTietTranformer {
    GioHangChiTiet toEntity(GioHangChiTietRequest request);
    GioHangChiTietModel toModel(GioHangChiTiet gioHangChiTiet);
}
