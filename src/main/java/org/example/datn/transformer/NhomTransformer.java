package org.example.datn.transformer;

import org.example.datn.entity.Nhom;
import org.example.datn.model.response.GroupModel;
import org.example.datn.model.response.RegisterUpdateGroupModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface NhomTransformer {
    Nhom toEntity(RegisterUpdateGroupModel model);
    GroupModel toModel(Nhom nhom);
}
