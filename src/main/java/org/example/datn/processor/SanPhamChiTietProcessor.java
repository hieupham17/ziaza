package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.MauSac;
import org.example.datn.entity.SanPham;
import org.example.datn.entity.SanPhamChiTiet;
import org.example.datn.entity.Size;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.response.DiaChiGiaoHangModel;
import org.example.datn.model.response.HoaDonChiTietModel;
import org.example.datn.model.response.SanPhamChiTietModel;
import org.example.datn.model.response.SanPhamModel;
import org.example.datn.service.MauSacService;
import org.example.datn.service.SanPhamChiTietService;
import org.example.datn.service.SanPhamService;
import org.example.datn.service.SizeService;
import org.example.datn.transformer.HoaDonChiTietTransformer;
import org.example.datn.transformer.SanPhamChiTietTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SanPhamChiTietProcessor {
    @Autowired
    private SanPhamChiTietService service;

    @Autowired
    private SizeService sizeService;
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    SanPhamChiTietTransformer sanPhamChiTietTransformer;

    public ServiceResult getById(Long id) {
        var s = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin chi tiết sản phẩm"));
        var size = sizeService.findById(s.getIdSize()).orElse(null);
        var mauSac = mauSacService.findById(s.getIdSize()).orElse(null);
        var sanPham = sanPhamService.findById(s.getIdSanPham()).orElse(null);
        SanPhamChiTietModel model = new SanPhamChiTietModel();
        BeanUtils.copyProperties(s, model);
        model.setSize(size);
        model.setMauSac(mauSac);
        model.setSanPham(sanPham);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getAll() {
        List<SanPhamChiTietModel> models = service.findAll().stream().map(sp -> {
            SanPhamChiTietModel model = new SanPhamChiTietModel();
            BeanUtils.copyProperties(sp, model);
            model.setSanPham(sanPhamService.findById(sp.getIdSanPham()).orElse(null));
            model.setMauSac(mauSacService.findById(sp.getIdMauSac()).orElse(null));
            model.setSize(sizeService.findById(sp.getIdSize()).orElse(null));
            return model;
        }).collect(Collectors.toList());

        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult save(SanPhamChiTietModel model, UserAuthentication ua) {
        SanPhamChiTiet sanPham = new SanPhamChiTiet();
        BeanUtils.copyProperties(model, sanPham);
        sanPham.setNguoiTao(ua.getPrincipal());
        sanPham.setNgayTao(LocalDateTime.now());
        sanPham.setNguoiCapNhat(ua.getPrincipal());
        sanPham.setNgayCapNhat(LocalDateTime.now());
        service.save(sanPham);
        return new ServiceResult("Sản phẩm đã được thêm thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult update(Long id, SanPhamChiTietModel model, UserAuthentication ua) {
        SanPhamChiTiet sanPham = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm để cập nhật"));
        BeanUtils.copyProperties(model, sanPham);
        sanPham.setNguoiCapNhat(ua.getPrincipal());
        sanPham.setNgayCapNhat(LocalDateTime.now());
        service.save(sanPham);
        return new ServiceResult("Sản phẩm đã được cập nhật thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult delete(Long id) {
        service.deleteById(id);
        return new ServiceResult("Sản phẩm đã được xóa thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public SanPhamChiTietModel findById(Long id) {
        return service.findById(id)
                .map(entity -> {
                    SanPhamChiTietModel model = new SanPhamChiTietModel();
                    BeanUtils.copyProperties(entity, model);
                    model.setSanPham(sanPhamService.findById(entity.getIdSanPham()).orElse(null));
                    model.setMauSac(mauSacService.findById(entity.getIdMauSac()).orElse(null));
                    model.setSize(sizeService.findById(entity.getIdSize()).orElse(null));
                    return model;
                })
                .orElseThrow(() -> new EntityNotFoundException("sanPhamChiTiet.not.found với ID: " + id));
    }

    public ServiceResult getByIdSanPham(Long idSanPham) {
        // Tìm các hóa đơn chi tiết dựa trên idHoaDon
        List<SanPhamChiTiet> sanPhamChiTiets = service.findByIdSanPham(idSanPham);

        // Kiểm tra nếu không có dữ liệu
        if (sanPhamChiTiets == null || sanPhamChiTiets.isEmpty()) {
            return new ServiceResult(null, SystemConstant.STATUS_FAIL, "Không tìm thấy hóa đơn chi tiết");
        }

        // Chuyển các hóa đơn chi tiết thành mô hình
        List<SanPhamChiTietModel> models = sanPhamChiTiets.stream()
                .map(sanPhamChiTiet -> {
                    SanPhamChiTietModel model = sanPhamChiTietTransformer.toModel(sanPhamChiTiet);

                    Size size = sizeService.findById(sanPhamChiTiet.getIdSize()).orElse(null);
                    MauSac mauSac = mauSacService.findById(sanPhamChiTiet.getIdMauSac()).orElse(null);
                    model.setSize(size);
                    model.setMauSac(mauSac);

                    return model;
                })
                .collect(Collectors.toList());

        // Trả kết quả với danh sách hóa đơn chi tiết
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

}
