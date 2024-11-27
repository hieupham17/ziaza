package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.YeuCauDoiTra;
import org.example.datn.entity.YeuCauDoiTraChiTiet;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.response.YeuCauDoiTraChiTietModel;
import org.example.datn.model.response.YeuCauDoiTraModel;
import org.example.datn.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class YeuCauDoiTraChiTietProcessor {
    @Autowired
    private YeuCauDoiTraService yeuCauDoiTraService;

    @Autowired
    private YeuCauDoiTraChiTietService service;
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private SanPhamChiTietProcessor sanPhamChiTietProcessor;
    public ServiceResult getById(Long id) {
        var sp = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin sản phẩm"));
        var spct = sanPhamChiTietProcessor.findById(sp.getIdSPCT());
        var yeuCau = yeuCauDoiTraService.findById(sp.getIdYeuCauDoiTra()).orElse(null);
        YeuCauDoiTraChiTietModel model = new YeuCauDoiTraChiTietModel();
        BeanUtils.copyProperties(sp, model);
        model.setYeuCauDoiTra(yeuCau);
        model.setSanPhamChiTiet(spct);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getAll() {
        List<YeuCauDoiTraChiTietModel> models = service.findAll().stream().map(sp -> {
            YeuCauDoiTraChiTietModel model = new YeuCauDoiTraChiTietModel();
            BeanUtils.copyProperties(sp, model);
            model.setYeuCauDoiTra(yeuCauDoiTraService.findById(sp.getIdYeuCauDoiTra()).orElse(null));
            model.setSanPhamChiTiet(sanPhamChiTietProcessor.findById(sp.getIdSPCT()));
            return model;
        }).collect(Collectors.toList());

        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult save(YeuCauDoiTraChiTiet yeuCauDoiTraChiTiet) {
        service.save(yeuCauDoiTraChiTiet);
        return new ServiceResult(yeuCauDoiTraChiTiet, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult update(Long id, YeuCauDoiTraChiTiet request){
        var p = service.findById(id).orElseThrow(() -> new EntityNotFoundException("danhMuc.not.found"));
        BeanUtils.copyProperties(request, p);
        service.save(p);
        return new ServiceResult();
    }

    // Lưu ảnh vào thư mục và trả về tên file
    public ServiceResult delete(Long id) {
        service.delete(id);
        return new ServiceResult("Sản phẩm đã được xóa thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getByYeuCauDoiTra(Long idDoiTra) {
        List<YeuCauDoiTraChiTietModel> models = service.findByIdYeuCauDoiTra(idDoiTra).stream().map(sp -> {
            YeuCauDoiTraChiTietModel model = new YeuCauDoiTraChiTietModel();
            BeanUtils.copyProperties(sp, model);
            model.setYeuCauDoiTra(yeuCauDoiTraService.findById(sp.getIdYeuCauDoiTra()).orElse(null));
            model.setSanPhamChiTiet(sanPhamChiTietProcessor.findById(sp.getIdSPCT()));
            return model;
        }).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

}
