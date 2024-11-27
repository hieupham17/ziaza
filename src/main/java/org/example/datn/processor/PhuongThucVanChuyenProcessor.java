package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.PhuongThucVanChuyen;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.request.PhuongThucVanChuyenRequest;
import org.example.datn.model.response.DiaChiGiaoHangModel;
import org.example.datn.model.response.PhuongThucVanChuyenModel;
import org.example.datn.service.PhuongThucVanChuyenService;
import org.example.datn.transformer.PhuongThucVanChuyenTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PhuongThucVanChuyenProcessor {

    @Autowired
    private PhuongThucVanChuyenService service;

    //    @Qualifier("phuongThucVanChuyenTransformer")
    @Autowired
    private PhuongThucVanChuyenTransformer phuongThucVanChuyenTransformer;

    public ServiceResult save(PhuongThucVanChuyenRequest request) {
        var p = phuongThucVanChuyenTransformer.toEntity(request);
        service.save(p);
        return new ServiceResult();
    }

    public ServiceResult update(Long id, PhuongThucVanChuyenRequest request) {
        var p = service.findById(id).orElseThrow(() -> new EntityNotFoundException("phuongThucVanChuyen.not.found"));
        BeanUtils.copyProperties(request, p);
        service.save(p);
        return new ServiceResult();
    }

    public ServiceResult delete(Long id) {
        var p = service.findById(id).orElseThrow(() -> new EntityNotFoundException("phuongThucVanChuyen.not.found"));
        service.delete(p);
        return new ServiceResult();
    }

    public ServiceResult findAll() {
        var list = service.getActive();
        var models = list.stream().map(phuongThucVanChuyenTransformer::toModel).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getById(Long id) {
        var p = service.findById(id).orElseThrow(() -> new EntityNotFoundException("phuongThucVanChuyen.not.found"));
        var model = phuongThucVanChuyenTransformer.toModel(p);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }
    public Optional<PhuongThucVanChuyen> get(Long id){
        return service.findById(id);
    }

    public PhuongThucVanChuyenModel findById(Long id) {
        return service.findById(id)
                .map(entity -> {
                    PhuongThucVanChuyenModel model = new PhuongThucVanChuyenModel();
                    BeanUtils.copyProperties(entity, model);
                    return model;
                })
                .orElseThrow(() -> new EntityNotFoundException("phuongThucVanChuyen.not.found"));
    }

    public ServiceResult getActive() {
        return new ServiceResult(service.getActive(), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }
}
