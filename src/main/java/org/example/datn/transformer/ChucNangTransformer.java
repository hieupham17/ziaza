package org.example.datn.transformer;

import org.example.datn.entity.ChucNang;
import org.example.datn.model.response.FunctionModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Component
@Mapper(componentModel = "spring")
public interface ChucNangTransformer {
    FunctionModel toModel(ChucNang chucNang);

    default List<Long> getParentIdHashset(List<FunctionModel> func) {
        var hashSet = new HashSet<>();
        var list = new ArrayList<Long>();
        func.forEach( item -> {
            if (Objects.nonNull(item.getIdCha())) {
                if (hashSet.add(item.getIdCha())) {
                    list.add(item.getIdCha());
                }
            }
        });
        return list;
    }
}
