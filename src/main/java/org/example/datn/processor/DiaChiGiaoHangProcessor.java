package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.DiaChiGiaoHang;
import org.example.datn.exception.AccessDeniedException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.DiaChiGiaoHangRequest;
import org.example.datn.model.response.DiaChiGiaoHangModel;
import org.example.datn.model.response.UserModel;
import org.example.datn.service.DiaChiGiaoHangService;
import org.example.datn.service.UserService;
import org.example.datn.transformer.DiaChiGiaoHangTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DiaChiGiaoHangProcessor {
    @Autowired
    private DiaChiGiaoHangService service;

    @Autowired
    private DiaChiGiaoHangTransformer diaChiGiaoHangTransformer;

    @Autowired
    private UserProcessor userProcessor;

    public ServiceResult getById(Long id) {
        var entity = service.findById(id).orElseThrow(() -> new EntityNotFoundException("diaChiGiaoHang.not.found"));
        var model = diaChiGiaoHangTransformer.toModel(entity);
        var user = userProcessor.findById(entity.getIdNguoiDung());
        model.setUserModel(user);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult create(DiaChiGiaoHangRequest request, UserAuthentication ua) {
        var a = diaChiGiaoHangTransformer.toEntity(request);
        a.setIdNguoiDung(ua.getPrincipal());
        service.save(a);
        return new ServiceResult();
    }

    public ServiceResult update(Long id, DiaChiGiaoHangRequest request) {
        var entity = service.findById(id).orElseThrow(() -> new EntityNotFoundException("diaChiGiaoHang.not.found"));
        entity.setHoTen(request.getHoTen());
        entity.setSdt(request.getSdt());
        entity.setDiaChi(request.getDiaChi());
        entity.setThanhPho(request.getThanhPho());
        entity.setQuocGia(request.getQuocGia());
        service.save(entity);
        return new ServiceResult();
    }

    public ServiceResult deleteById(Long id) throws AccessDeniedException {
        var entity = service.findById(id).orElseThrow(() -> new EntityNotFoundException("diaChiGiaoHang.not.found"));
        if (entity.getTrangThai().equals(SystemConstant.DEFAULT)){
            throw AccessDeniedException.of("Địa chỉ mặc định không được phép xóa!");
        }
        service.delete(entity);
        return new ServiceResult();
    }

    public ServiceResult findByIdNguoiDung(UserAuthentication ua) {
        var list = service.findByIdNguoiDung(ua.getPrincipal());
        var models = list.stream()
                .sorted(Comparator.comparingInt(item -> item.getTrangThai() == 1 ? 0 : 1))
                .map(this::mapToModel)
                .collect(Collectors.toList());

        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }


    public ServiceResult getActive(UserAuthentication ua) {
        var list = service.findByIdNguoiDungAndTrangThai(ua.getPrincipal(), SystemConstant.ACTIVE);
        var models = list.stream().map(this::mapToModel);
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);

    }

    public DiaChiGiaoHangModel findById(Long id) {
        return service.findById(id)
                .map(entity -> {
                    DiaChiGiaoHangModel model = new DiaChiGiaoHangModel();
                    BeanUtils.copyProperties(entity, model);
                    return model;
                })
                .orElseThrow(() -> new EntityNotFoundException("diaChiGiaoHang.not.found"));
    }


    private DiaChiGiaoHangModel mapToModel(DiaChiGiaoHang diaChiGiaoHang) {
        DiaChiGiaoHangModel model = new DiaChiGiaoHangModel();
        model.setId(diaChiGiaoHang.getId());
        model.setIdNguoiDung(diaChiGiaoHang.getIdNguoiDung());
        model.setHoTen(diaChiGiaoHang.getHoTen());
        model.setSdt(diaChiGiaoHang.getSdt());
        model.setDiaChi(diaChiGiaoHang.getDiaChi());
        model.setThanhPho(diaChiGiaoHang.getThanhPho());
        model.setQuocGia(diaChiGiaoHang.getQuocGia());
        model.setTrangThai(diaChiGiaoHang.getTrangThai());
        // Có thể thêm logic để lấy UserModel nếu cần
        return model;
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult insert(DiaChiGiaoHangRequest request, UserAuthentication ua) {
        var entity = diaChiGiaoHangTransformer.toEntity(request);
        entity.setIdNguoiDung(ua.getPrincipal());
        entity.setTrangThai(0);
        entity.setNgayTao(LocalDateTime.now());
        entity.setNgayCapNhat(LocalDateTime.now());
        entity.setNguoiTao(ua.getPrincipal());
        entity.setNguoiCapNhat(ua.getPrincipal());
        service.save(entity);
        return new ServiceResult();
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult changeDefault(Long id) {
        service.findByTrangThaiDefault(SystemConstant.DEFAULT).forEach(diaChiGiaoHang -> {
            diaChiGiaoHang.setTrangThai(SystemConstant.UNDEFAULT);
        });
        var entity = service.findById(id).orElseThrow(() -> new EntityNotFoundException("diaChiGiaoHang.not.found"));
        entity.setTrangThai(SystemConstant.DEFAULT);
        service.save(entity);
        return new ServiceResult();
    }


}
