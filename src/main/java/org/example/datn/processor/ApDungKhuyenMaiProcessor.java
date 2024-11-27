package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.ApDungKhuyenMaiRequest;
import org.example.datn.service.ApDungKhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Component
public class ApDungKhuyenMaiProcessor {
    @Autowired
    private ApDungKhuyenMaiService service;

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult change(Long idKhuyenMai, UserAuthentication ua) {
        var a = service.findByIdKhuyenMaiAndIdNguoiDung(idKhuyenMai, ua.getPrincipal()).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy áp dụng khuyến mãi!"));
        a.setDaSuDung(true);
        service.save(a);
        return new ServiceResult();
    }
    public ServiceResult getList() {
        return new ServiceResult(service.findAll(), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }
}
