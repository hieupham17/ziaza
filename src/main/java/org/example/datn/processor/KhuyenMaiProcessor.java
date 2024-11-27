package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.ApDungKhuyenMai;
import org.example.datn.entity.KhuyenMai;
import org.example.datn.entity.Profile;
import org.example.datn.entity.SanPham;
import org.example.datn.exception.DuplicatedException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.KhuyenMaiCreateUpdateRequest;
import org.example.datn.model.request.KhuyenMaiQuery;
import org.example.datn.model.response.KhuyenMaiModel;
import org.example.datn.model.response.ProfileModel;
import org.example.datn.model.response.SanPhamModel;
import org.example.datn.model.response.UserModel;
import org.example.datn.service.*;
import org.example.datn.transformer.KhuyenMaiTransformer;
import org.example.datn.transformer.ProfileTransformer;
import org.example.datn.transformer.SanPhamTransformer;
import org.example.datn.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class KhuyenMaiProcessor {
    @Autowired
    KhuyenMaiService service;

    @Autowired
    ApDungKhuyenMaiService apDungKhuyenMaiService;

    @Autowired
    KhuyenMaiTransformer khuyenMaiTransformer;
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamTransformer sanPhamTransformer;
    @Autowired
    private UserService userService;
    @Autowired
    private UserTransformer userTransformer;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileTransformer profileTransformer;

    public ServiceResult getList() {
        return new ServiceResult(service.findAll(), SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult save(KhuyenMai khuyenMai) {
        service.save(khuyenMai);
        return new ServiceResult(khuyenMai, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult delete(Long id) {
        KhuyenMai khuyenMai = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khuyến mãi"));
        var apDungKhuyenMais = apDungKhuyenMaiService.findByIdKhuyenMai(id);
        for (ApDungKhuyenMai adkm : apDungKhuyenMais) {
            apDungKhuyenMaiService.delete(adkm);
        }
        service.delete(khuyenMai);
        return new ServiceResult();
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult create(KhuyenMaiCreateUpdateRequest request, UserAuthentication ua) throws DuplicatedException {
        if (request.getMa() != null && !request.getMa().isEmpty()) {
            validateCreateDuplicated(request);
        }
        KhuyenMai khuyenMai = khuyenMaiTransformer.toEntity(request);
        khuyenMai.setNguoiTao(ua.getPrincipal());
        khuyenMai.setNguoiCapNhat(ua.getPrincipal());
        khuyenMai.setNgayTao(LocalDateTime.now());
        khuyenMai.setNgayCapNhat(LocalDateTime.now());
        khuyenMai.setTrangThai(
                (request.getNgayBatDau().isBefore(LocalDateTime.now()) && request.getNgayKetThuc().isAfter(LocalDateTime.now()))
                        ? SystemConstant.DA_AP_DUNG
                        : SystemConstant.CHUA_AP_DUNG
        );
        KhuyenMai km = service.saveKm(khuyenMai);

        List<Long> idList = (km.getLoai() == SystemConstant.KHUYEN_MAI_SAN_PHAM)
                ? request.getIdSanPhamList()
                : request.getIdNguoiDungList();

        if (idList != null && !idList.isEmpty()) {
            idList.forEach(id -> {
                ApDungKhuyenMai apDungKhuyenMai = createApDungKhuyenMai(km, id, request.getGiaTri(), km.getLoai(), ua);
                apDungKhuyenMai.setTrangThai(km.getLoai() == SystemConstant.KHUYEN_MAI_NGUOI_DUNG
                        ? SystemConstant.CHUA_AP_DUNG
                        : (km.getTrangThai() == SystemConstant.DA_AP_DUNG ? SystemConstant.DA_AP_DUNG : SystemConstant.CHUA_AP_DUNG)
                );
                apDungKhuyenMaiService.save(apDungKhuyenMai);
            });
        }

        return new ServiceResult();
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult update(Long id, KhuyenMaiCreateUpdateRequest request, UserAuthentication ua) throws DuplicatedException {
        KhuyenMai existingKhuyenMai = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khuyến mãi"));

        if (request.getMa() != null && !request.getMa().isEmpty()) {
            validateUpdateDuplicated(request, id);
        }
        existingKhuyenMai.setMa(request.getMa());
        existingKhuyenMai.setTen(request.getTen());
        existingKhuyenMai.setMoTa(request.getMoTa());
        existingKhuyenMai.setGiaTri(request.getGiaTri());
        existingKhuyenMai.setNgayBatDau(request.getNgayBatDau());
        existingKhuyenMai.setNgayKetThuc(request.getNgayKetThuc());
        existingKhuyenMai.setTrangThai(
                (request.getNgayBatDau().isBefore(LocalDateTime.now()) && request.getNgayKetThuc().isAfter(LocalDateTime.now()))
                        ? SystemConstant.DA_AP_DUNG
                        : SystemConstant.CHUA_AP_DUNG
        );
        existingKhuyenMai.setNguoiCapNhat(ua.getPrincipal());
        existingKhuyenMai.setNgayCapNhat(LocalDateTime.now());

        // Lưu lại khuyến mãi đã cập nhật
        service.saveKm(existingKhuyenMai);

        List<Long> idList = (existingKhuyenMai.getLoai() == SystemConstant.KHUYEN_MAI_SAN_PHAM)
                ? request.getIdSanPhamList()
                : request.getIdNguoiDungList();

        if (idList != null && !idList.isEmpty()) {
            // Xóa các bản ghi cũ của sản phẩm/người dùng không có trong idList
            apDungKhuyenMaiService.deleteByKhuyenMaiAndNotIn(existingKhuyenMai.getId(), idList);
        }

        // Cập nhật các sản phẩm hoặc người dùng liên quan
        idList.forEach(ids -> {
            ApDungKhuyenMai apDungKhuyenMai = createApDungKhuyenMai(existingKhuyenMai, ids, request.getGiaTri(), existingKhuyenMai.getLoai(), ua);
            apDungKhuyenMai.setTrangThai(existingKhuyenMai.getLoai() == SystemConstant.KHUYEN_MAI_NGUOI_DUNG
                    ? SystemConstant.CHUA_AP_DUNG
                    : (existingKhuyenMai.getTrangThai() == SystemConstant.DA_AP_DUNG ? SystemConstant.DA_AP_DUNG : SystemConstant.CHUA_AP_DUNG)
            );
            apDungKhuyenMaiService.save(apDungKhuyenMai);
        });

        return new ServiceResult();
    }

    private ApDungKhuyenMai createApDungKhuyenMai(KhuyenMai km, Long id, BigDecimal giaTriGiam, Integer loaiKhuyenMai, UserAuthentication ua) {
        ApDungKhuyenMai apDungKhuyenMai = new ApDungKhuyenMai();
        apDungKhuyenMai.setIdKhuyenMai(km.getId());
        apDungKhuyenMai.setIdSanPham(loaiKhuyenMai == SystemConstant.KHUYEN_MAI_SAN_PHAM ? id : null);
        apDungKhuyenMai.setIdNguoiDung(loaiKhuyenMai == SystemConstant.KHUYEN_MAI_NGUOI_DUNG ? id : null);
        apDungKhuyenMai.setGiaTriGiam(giaTriGiam);
        apDungKhuyenMai.setNguoiTao(ua.getPrincipal());
        apDungKhuyenMai.setNguoiCapNhat(ua.getPrincipal());
        apDungKhuyenMai.setNgayTao(LocalDateTime.now());
        apDungKhuyenMai.setNgayCapNhat(LocalDateTime.now());
        return apDungKhuyenMai;
    }

    private void validateUpdateDuplicated(KhuyenMaiCreateUpdateRequest request, Long id) throws DuplicatedException {
        if (service.existsByMaAndIdNot(request.getMa(), id)) {
            throw DuplicatedException.of("Mã khuyến mãi đã tồn tại");
        }
    }

    private void validateCreateDuplicated(KhuyenMaiCreateUpdateRequest request) throws DuplicatedException {
        if (service.existsByMa(request.getMa())) {
            throw DuplicatedException.of("Mã khuyến mãi đã tồn tại");
        }
    }

    public ServiceResult findByKeywordAndLoai(KhuyenMaiQuery request) {
        var list = service.findByKeywordAndLoai(request.getKeyword(), request.getLoai());
        var models = list.stream().map(khuyenMaiTransformer::toModel).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult findById(Long id) {
        KhuyenMai khuyenMai = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khuyến mãi"));
        KhuyenMaiModel model = khuyenMaiTransformer.toModel(khuyenMai);
        List<ApDungKhuyenMai> apDungKhuyenMaiList = apDungKhuyenMaiService.findByIdKhuyenMai(id);
        List<Long> sanPhamIds = apDungKhuyenMaiList.stream()
                .map(ApDungKhuyenMai::getIdSanPham)
                .collect(Collectors.toList());
        List<SanPhamModel> sanPhamModels = sanPhamService.findByIdIn(sanPhamIds).stream().map(sanPhamTransformer::toModel).collect(Collectors.toList());
        model.setSanPhamModels(sanPhamModels);
        List<Long> userIds = apDungKhuyenMaiList.stream()
                .map(ApDungKhuyenMai::getIdNguoiDung)
                .collect(Collectors.toList());

        List<UserModel> userModels = userService.findByIdIn(userIds).stream()
                .map(e -> {
                    UserModel userModel = userTransformer.toModel(e);
                    Optional<Profile> profile = profileService.findByUserId(e.getId());
                    ProfileModel profileModel = profile.map(profileTransformer::toModel).orElse(null);
                    userModel.setProfile(profileModel);
                    return userModel;
                }).collect(Collectors.toList());
        model.setUserModels(userModels);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getAllUserId(UserAuthentication ua) {
        var apDungKhuyenMais = apDungKhuyenMaiService.findByIdNguoiDungAndDaSuDung(ua.getPrincipal(), false);
        List<Long> khuyenMaiIds = apDungKhuyenMais.stream()
                .map(ApDungKhuyenMai::getIdKhuyenMai)
                .distinct()
                .collect(Collectors.toList());
        List<KhuyenMai> khuyenMais = service.findByIdIn(khuyenMaiIds);
        List<KhuyenMaiModel> khuyenMaiModels = khuyenMais.stream().map(khuyenMaiTransformer::toModel).collect(Collectors.toList());
        return new ServiceResult(khuyenMaiModels, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

}
