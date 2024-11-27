package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.model.ServiceResult;
import org.example.datn.service.PhuongThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhuongThucThanhToanProcessor {
    @Autowired
    private PhuongThucThanhToanService service;

    public ServiceResult getActive() {
        return new ServiceResult(service.getActive().get(), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getList(){
        return new ServiceResult(service.findAll(), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }
}
