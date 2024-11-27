package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.response.FunctionModel;
import org.example.datn.service.ChucNangService;
import org.example.datn.transformer.ChucNangTransformer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ChucNangProcessor {
    private ChucNangService service;
    private ChucNangTransformer chucNangTransformer;

    public ChucNangProcessor(ChucNangService service, ChucNangTransformer chucNangTransformer) {
        this.service = service;
        this.chucNangTransformer = chucNangTransformer;
    }

    public ServiceResult get() {
        var allFunction = service.findAllByOrderByNgayTaoDesc()
                .stream().map(chucNangTransformer::toModel).collect(Collectors.toList());
        return new ServiceResult(rangerFunction(allFunction), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    private List<FunctionModel> rangerFunction(List<FunctionModel> allFunction) {

        var childIds = chucNangTransformer.getParentIdHashset(allFunction);

        if (childIds.size() == 0) return allFunction;


        for (FunctionModel func : allFunction) {

            var nonParentId = childIds
                    .stream()
                    .filter(item -> func.getId().equals(item))
                    .findFirst().orElse(null);

            if (Objects.isNull(nonParentId)) {

                var parent = allFunction.stream()
                        .filter(item -> item.getId().equals(func.getIdCha()))
                        .findFirst().orElse(null);

                if (Objects.nonNull(parent)) {

                    Integer index = allFunction.indexOf(parent);

                    allFunction.get(index).getFunction().add(func);
                    allFunction.remove(func);
                    break;
                }
            }
        }
        ;

        return rangerFunction(allFunction);
    }
}
