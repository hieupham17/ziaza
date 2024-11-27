package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.MauSac;
import org.example.datn.entity.Size;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.service.MauSacService;
import org.example.datn.service.SizeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author hoangKhong
 */
@Component
public class MauSacProcessor {

    @Autowired
    MauSacService service;

    public ServiceResult getList() {
        return new ServiceResult(service.findAll(), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getById(Long id) {
        Optional<MauSac> size = service.findById(id);
        return size.map(value -> new ServiceResult(value, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200))
                .orElseGet(() -> new ServiceResult(null, SystemConstant.STATUS_FAIL, SystemConstant.CODE_200));
    }

    public ServiceResult save(MauSac chatLieu, UserAuthentication ua) {
        chatLieu.setNguoiTao(ua.getPrincipal());
        chatLieu.setNgayTao(LocalDateTime.now());
        chatLieu.setNguoiCapNhat(ua.getPrincipal());
        chatLieu.setNgayCapNhat(LocalDateTime.now());
        service.save(chatLieu);
        return new ServiceResult(chatLieu, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult update(Long id, MauSac chatLieu, UserAuthentication ua) {
        MauSac chatLieu1 = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại"));
        BeanUtils.copyProperties(chatLieu, chatLieu1);
        chatLieu.setNguoiCapNhat(ua.getPrincipal());
        chatLieu.setNgayCapNhat(LocalDateTime.now());
        service.save(chatLieu);
        return new ServiceResult(chatLieu, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult delete(Long id) {
        service.deleteById(id);
        return new ServiceResult(null, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }
}
