package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.MauSac;
import org.example.datn.entity.Thuonghieu;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.model.request.ThuongHieuRequest;
import org.example.datn.model.response.DanhMucModel;
import org.example.datn.model.response.ThuongHieuModel;
import org.example.datn.service.DanhMucService;
import org.example.datn.service.ThanhToanService;
import org.example.datn.service.ThuongHieuService;
import org.example.datn.transformer.DanhMucTransformer;
import org.example.datn.transformer.ThuongHieuTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class ThuongHieuProcessor {
    @Autowired
    private ThuongHieuService service;
    @Autowired
    private ThuongHieuTransformer transformer;

    public ServiceResult save(Thuonghieu chatLieu, UserAuthentication ua) {
        chatLieu.setNguoiTao(ua.getPrincipal());
        chatLieu.setNgayTao(LocalDateTime.now());
        chatLieu.setNguoiCapNhat(ua.getPrincipal());
        chatLieu.setNgayCapNhat(LocalDateTime.now());
        service.save(chatLieu);
        return new ServiceResult(chatLieu, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult update(Long id, Thuonghieu chatLieu, UserAuthentication ua) {
        Thuonghieu chatLieu1 = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại"));
        BeanUtils.copyProperties(chatLieu, chatLieu1);
        chatLieu.setNguoiCapNhat(ua.getPrincipal());
        chatLieu.setNgayCapNhat(LocalDateTime.now());
        service.save(chatLieu);
        return new ServiceResult(chatLieu, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult delete(Long id){
        var p = service.findById(id).orElseThrow(() -> new EntityNotFoundException("phuongThucVanChuyen.not.found"));
        service.delete(p);
        return new ServiceResult();
    }

    public ServiceResult findAll(){
        var list = service.getAll();
        var models = list.stream().map(transformer::toModel).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getById(Long id){
        var p = service.findById(id).orElseThrow(() -> new EntityNotFoundException("phuongThucVanChuyen.not.found"));
        var model = transformer.toModel(p);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ThuongHieuModel findById(Long id) {
        return service.findById(id)
                .map(transformer::toModel)
                .orElseThrow(() -> new EntityNotFoundException("phuongThucVanChuyen.not.found"));
    }
}
