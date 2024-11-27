package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.DanhMuc;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.model.response.DanhMucModel;
import org.example.datn.service.DanhMucService;
import org.example.datn.transformer.DanhMucTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class DanhMucProcessor {

    @Autowired
    private DanhMucService service;

    @Autowired
    private DanhMucTransformer transformer;

    public ServiceResult save(DanhMuc request, UserAuthentication ua) {
        request.setNguoiTao(ua.getPrincipal());
        request.setNgayTao(LocalDateTime.now());
        request.setNguoiCapNhat(ua.getPrincipal());
        request.setNgayCapNhat(LocalDateTime.now());
        service.save(request);
        return new ServiceResult();
    }

    public ServiceResult update(Long id, DanhMucRequest request, UserAuthentication ua) {
        DanhMuc danhMuc = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại"));
        BeanUtils.copyProperties(request, danhMuc);
        danhMuc.setNguoiCapNhat(ua.getPrincipal());
        danhMuc.setNgayCapNhat(LocalDateTime.now());
        service.save(danhMuc);
        return new ServiceResult();
    }

    public ServiceResult delete(Long id) {
        DanhMuc danhMuc = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại"));
        service.delete(danhMuc);
        return new ServiceResult();
    }

    public ServiceResult findAll() {
        var list = service.getAll();
        var models = list.stream().map(transformer::toModel).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getById(Long id) {
        DanhMuc danhMuc = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại"));
        var model = transformer.toModel(danhMuc);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public DanhMucModel findById(Long id) {
        return service.findById(id)
                .map(transformer::toModel)
                .orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại"));
    }
}
