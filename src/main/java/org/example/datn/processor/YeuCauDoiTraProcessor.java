package org.example.datn.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.apache.commons.lang3.StringEscapeUtils;
import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.*;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.enums.LoaiYeuCau;
import org.example.datn.model.enums.StatusHoaDon;
import org.example.datn.model.enums.StatusYeuCauDoiTra;
import org.example.datn.model.enums.TrangThaiDoiTra;
import org.example.datn.model.request.CancelOrderRequest;
import org.example.datn.model.request.ProfileRequest;
import org.example.datn.model.request.ThuongHieuRequest;
import org.example.datn.model.request.YeuCauDoiTraRequest;
import org.example.datn.model.response.SanPhamModel;
import org.example.datn.model.response.YeuCauDoiTraModel;
import org.example.datn.repository.SanPhamRepository;
import org.example.datn.service.*;
import org.example.datn.transformer.YeuCauDoiTraTransformer;
import org.example.datn.utils.VNPayUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class YeuCauDoiTraProcessor {
    @Autowired
    private SanPhamDoiTraService sanPhamDoiTraService;
    @Autowired
    private YeuCauDoiTraService service;
    @Autowired
    private YeuCauDoiTraChiTietService yeuCauDoiTraChiTietService;
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonProcessor hoaDonProcessor;
    @Autowired
    private UserService userService;
    @Autowired
    private UserProcessor userProcessor;
    @Autowired
    private YeuCauDoiTraTransformer yeuCauDoiTraTransformer;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private HinhAnhService hinhAnhService;
    @Autowired
    private HinhAnhServices hinhAnhServices;

    public ServiceResult getById(Long id) {
        var sp = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin sản phẩm"));
        var hoadon = hoaDonService.findById(sp.getIdHoaDon()).orElse(null);
        var user = userProcessor.findById(sp.getIdNguoiDung());
        YeuCauDoiTraModel model = new YeuCauDoiTraModel();
        BeanUtils.copyProperties(sp, model);
        model.setHoaDon(hoadon);
        model.setUser(user);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getAll() {
        List<YeuCauDoiTraModel> models = service.findAll().stream().map(sp -> {
            YeuCauDoiTraModel model = new YeuCauDoiTraModel();
            BeanUtils.copyProperties(sp, model);
            model.setHoaDon(hoaDonService.findById(sp.getIdHoaDon()).orElse(null));
            model.setUser(userProcessor.findById(sp.getIdNguoiDung()));
            return model;
        }).collect(Collectors.toList());

        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult save(YeuCauDoiTra yeuCauDoiTra) {
        service.save(yeuCauDoiTra);
        return new ServiceResult(yeuCauDoiTra, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult update(Long id, YeuCauDoiTra request) {
        var p = service.findById(id).orElseThrow(() -> new EntityNotFoundException("yeuCau.not.found"));
        BeanUtils.copyProperties(request, p);
        service.save(p);
        return new ServiceResult();
    }

    public ServiceResult delete(Long id) {
        service.delete(id);
        return new ServiceResult("Sản phẩm đã được xóa thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getByLoaiAndTrangThai(LoaiYeuCau loai, Integer trangThai) {
        List<YeuCauDoiTraModel> models = service.findByLoaiAndTrangThai(loai, trangThai).stream().map(sp -> {
            YeuCauDoiTraModel model = new YeuCauDoiTraModel();
            BeanUtils.copyProperties(sp, model);
            model.setHoaDon(hoaDonService.findById(sp.getIdHoaDon()).orElse(null));
            model.setUser(userProcessor.findById(sp.getIdNguoiDung()));
            return model;
        }).collect(Collectors.toList());

        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getByLoai(LoaiYeuCau loai) {
        List<YeuCauDoiTraModel> models = service.findByLoai(loai).stream().map(sp -> {
            YeuCauDoiTraModel model = new YeuCauDoiTraModel();
            BeanUtils.copyProperties(sp, model);
            model.setHoaDon(hoaDonService.findById(sp.getIdHoaDon()).orElse(null));
            model.setUser(userProcessor.findById(sp.getIdNguoiDung()));
            return model;
        }).collect(Collectors.toList());

        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }
    private void updateYeuCauDoiTraChiTiet(Long idDoiTra, Integer trangThai) {
        var yeuCauDoiTraChiTiet = yeuCauDoiTraChiTietService.findByIdYeuCauDoiTra(idDoiTra);
        yeuCauDoiTraChiTiet.forEach(e -> e.setTrangThai(trangThai));
        yeuCauDoiTraChiTietService.saveAll(yeuCauDoiTraChiTiet);
    }

    private void saveSanPhamDoiTraFromChiTiet(YeuCauDoiTra yeuCauDoiTra, UserAuthentication ua) {
        List<YeuCauDoiTraChiTiet> yeuCauDoiTraChiTiets = yeuCauDoiTraChiTietService.findByIdYeuCauDoiTra(yeuCauDoiTra.getId());

        // Duyệt qua từng chi tiết yêu cầu đổi trả
        yeuCauDoiTraChiTiets.forEach(chiTiet -> {
            SanPhamDoiTra sanPhamDoiTra = new SanPhamDoiTra();

            // Lấy thông tin từ chi tiết yêu cầu đổi trả và lưu vào bảng SanPhamDoiTra
            sanPhamDoiTra.setIdYeuCauDoiTra(yeuCauDoiTra.getId());
            sanPhamDoiTra.setIdSPCT(chiTiet.getIdSPCT());
            sanPhamDoiTra.setSoLuong(chiTiet.getSoLuong());
            sanPhamDoiTra.setLoai(String.valueOf(yeuCauDoiTra.getLoai()));

            sanPhamDoiTra.setNguoiCapNhat(ua.getPrincipal());
            sanPhamDoiTra.setNgayTao(LocalDateTime.now());
            sanPhamDoiTra.setNgayCapNhat(LocalDateTime.now());
            sanPhamDoiTra.setNguoiTao(ua.getPrincipal());
            sanPhamDoiTra.setTrangThai(0);

            // Lưu vào bảng SanPhamDoiTra
            sanPhamDoiTraService.save(sanPhamDoiTra); // Giả sử có service sanPhamDoiTraService
        });
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult updateStatus(Long id, UserAuthentication ua) {
        // Tìm hóa đơn theo ID
        YeuCauDoiTra yeuCauDoiTra = service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tồn tại"));

        // Xác định trạng thái mới dựa trên trạng thái hiện tại
        Integer newTrangThai;
        switch (yeuCauDoiTra.getTrangThai()) {
            case 0:
                newTrangThai = StatusYeuCauDoiTra.DANG_XU_LY.getValue();
                break;
            case 1:
                newTrangThai = StatusYeuCauDoiTra.HOAN_THANH.getValue();
                saveSanPhamDoiTraFromChiTiet(yeuCauDoiTra, ua); // Lưu thông tin vào bảng SanPhamDoiTra
                break;
            default:
                throw new IllegalArgumentException("Trạng thái không hợp lệ để cập nhật");
        }

        // Cập nhật trạng thái hóa đơn
        yeuCauDoiTra.setTrangThai(newTrangThai);
        yeuCauDoiTra.setNgayCapNhat(LocalDateTime.now());
        yeuCauDoiTra.setNguoiCapNhat(ua.getPrincipal());
        yeuCauDoiTra.setNgayHoanTat(LocalDateTime.now());
        // Cập nhật trạng thái các chi tiết hóa đơn và lưu thông tin vào bảng SanPhamDoiTra
        updateYeuCauDoiTraChiTiet(id, newTrangThai); // Cập nhật trạng thái chi tiết
        // Lưu hóa đơn
        service.save(yeuCauDoiTra);
        // Trả về kết quả thành công
        return new ServiceResult("Hóa đơn đã được cập nhật trạng thái thành công",
                SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }


    @Transactional(rollbackOn = Exception.class)
    public ServiceResult cancelOrder(Long id, CancelOrderRequest request, UserAuthentication ua) throws IOException, InterruptedException {
        // Tìm yêu cầu đổi trả theo ID
        YeuCauDoiTra yeuCauDoiTra = service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Yêu cầu không tồn tại"));

        // Kiểm tra trạng thái
        if (yeuCauDoiTra.getTrangThai() != StatusYeuCauDoiTra.CHO_XU_LY.getValue() &&
                yeuCauDoiTra.getTrangThai() != StatusYeuCauDoiTra.DANG_XU_LY.getValue()) {
            throw new IllegalArgumentException("Yêu cầu không thể hủy vì trạng thái không hợp lệ.");
        }
        Integer newTrangThai = StatusYeuCauDoiTra.TU_CHOI.getValue();
        // Cập nhật thông tin yêu cầu đổi trả
        yeuCauDoiTra.setTrangThai(newTrangThai); // Đổi trạng thái
        yeuCauDoiTra.setGhiChu(request.getOrderInfo()); // Ghi chú từ request
        yeuCauDoiTra.setNgayCapNhat(LocalDateTime.now()); // Ngày cập nhật
        yeuCauDoiTra.setNgayHoanTat(LocalDateTime.now()); // Ngày hoàn tất
        yeuCauDoiTra.setNguoiCapNhat(ua.getPrincipal()); // Người cập nhật
        updateYeuCauDoiTraChiTiet(id, newTrangThai);

        // Lưu lại yêu cầu đổi trả
        service.save(yeuCauDoiTra);

        // Trả về kết quả thành công
        return new ServiceResult("Yêu cầu đã được cập nhật thành công.");
    }


//    public ServiceResult updateTrangThai(Long id, Integer trangThai) {
//        // Tìm yêu cầu đổi trả
//        var yeuCauDoiTra = service.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Yêu cầu đổi trả không tìm thấy"));
//
//        // Cập nhật trạng thái cho yêu cầu đổi trả
//        yeuCauDoiTra.setTrangThai(trangThai);
//        service.save(yeuCauDoiTra);
//
//        // Cập nhật trạng thái cho các chi tiết của yêu cầu đổi trả
//        updateYeuCauDoiTraChiTiet(id, trangThai);
//
//        // Trả về kết quả thành công
//        return new ServiceResult("Cập nhật trạng thái yêu cầu đổi trả thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult create(String request, MultipartFile[] files, UserAuthentication ua) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        JsonSerializer<LocalDate> serializer = (src, typeOfSrc, context) ->
                context.serialize(src.format(formatter));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, serializer)
                .create();
        YeuCauDoiTraRequest target = gson.fromJson(removeQuotesAndUnescape(request), YeuCauDoiTraRequest.class);
        var yeuCauDoiTra = yeuCauDoiTraTransformer.toEntity(target);
        yeuCauDoiTra.setIdNguoiDung(ua.getPrincipal());
        yeuCauDoiTra.setTrangThai(StatusYeuCauDoiTra.CHO_XU_LY.getValue());
        yeuCauDoiTra.setNgayYeuCau(LocalDateTime.now());
        yeuCauDoiTra.setNgayTao(LocalDateTime.now());
        yeuCauDoiTra.setNgayCapNhat(LocalDateTime.now());
        yeuCauDoiTra.setNguoiTao(ua.getPrincipal());
        yeuCauDoiTra.setNguoiCapNhat(ua.getPrincipal());
        service.save(yeuCauDoiTra);

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String fileName = saveImage(file);
                HinhAnh hinhAnh = new HinhAnh();
                hinhAnh.setIdYeuCauDoiTra(yeuCauDoiTra.getId());
                hinhAnh.setAnh(fileName);
                hinhAnh.setNgayTao(LocalDateTime.now());
                hinhAnh.setNgayCapNhat(LocalDateTime.now());
                hinhAnh.setNguoiTao(ua.getPrincipal());
                hinhAnh.setNguoiCapNhat(ua.getPrincipal());
                hinhAnhServices.save(hinhAnh);
            }
        }

        for (Long idSPCT : target.getIdSanPhamChiTiets()) {
            YeuCauDoiTraChiTiet yeuCauChiTiet = new YeuCauDoiTraChiTiet();
            yeuCauChiTiet.setIdYeuCauDoiTra(yeuCauDoiTra.getId());
            yeuCauChiTiet.setIdSPCT(idSPCT);
            yeuCauChiTiet.setSoLuong(1);
            yeuCauChiTiet.setTrangThai(StatusYeuCauDoiTra.CHO_XU_LY.getValue());
            yeuCauChiTiet.setNgayTao(LocalDateTime.now());
            yeuCauChiTiet.setNgayCapNhat(LocalDateTime.now());
            yeuCauChiTiet.setNguoiTao(ua.getPrincipal());
            yeuCauChiTiet.setNguoiCapNhat(ua.getPrincipal());
            yeuCauDoiTraChiTietService.save(yeuCauChiTiet);
        }
        var hoaDon = hoaDonService.findById(target.getIdHoaDon()).orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tồn tại"));
        hoaDon.setTrangThaiDoiTra(TrangThaiDoiTra.CHO_XU_LY.getValue());
        hoaDonService.save(hoaDon);
        hoaDonChiTietService.saveAll(
                hoaDonChiTietService.getHoaDonChiTietByHoaDonAndSanPhamChiTiet(target.getIdHoaDon(), target.getIdSanPhamChiTiets())
                        .stream()
                        .peek(e -> e.setTrangThaiDoiTra(TrangThaiDoiTra.CHO_XU_LY.getValue()))
                        .collect(Collectors.toList())
        );

        return new ServiceResult();

    }

    private static final String UPLOAD_DIR = "images";

    private String saveImage(MultipartFile image) {
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        String imagePath = UPLOAD_DIR + File.separator + fileName;

        try {
            // Kiểm tra và tạo thư mục UPLOAD_DIR nếu nó chưa tồn tại
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // Lưu hình ảnh
            byte[] bytes = image.getBytes();
            Path path = Paths.get(imagePath);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể lưu hình ảnh!");
        }

        return imagePath;
    }

    public String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }
}
