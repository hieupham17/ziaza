package org.example.datn.processor;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.Blog;
import org.example.datn.entity.SanPham;
import org.example.datn.entity.SanPhamChiTiet;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.response.SanPhamModel;
import org.example.datn.repository.SanPhamRepository;
import org.example.datn.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BlogProcessor {
    @Autowired
    private BlogService service;

//    public ServiceResult getById(Long id) {
//        var sp = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin sản phẩm"));
//        SanPhamModel model = new SanPhamModel();
//        BeanUtils.copyProperties(sp, model);
//        model.setChatLieu(chatLieu);
//
//        var mauSac = mauSacService.findByIdIn(idMauSacs);
//
//        model.setListMauSac(mauSac);
//
//        var hinhAnhs = hinhAnhServices.getImagesByProductId(id); // Lấy hình ảnh từ bảng hinh_anh
//        model.setHinhAnhList(hinhAnhs);
//        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }

//    public ServiceResult getAll() {
//        List<SanPhamModel> models = service.findAll().stream().map(sp -> {
//            SanPhamModel model = new SanPhamModel();
//            BeanUtils.copyProperties(sp, model);
//            model.setDanhMuc(danhMucService.findById(sp.getIdDanhMuc()).orElse(null));
//            return model;
//        }).collect(Collectors.toList());
//
//        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }

    // Lưu ảnh vào thư mục và trả về tên file
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


    // Thêm mới sản phẩm
    public ServiceResult save(Blog blog, MultipartFile file, UserAuthentication ua) {

        if (file != null && !file.isEmpty()) {
            var url = saveImage(file);
            blog.setHinhAnh(url);
        }
        blog.setNguoiCapNhat(ua.getPrincipal());
        blog.setNguoiTao(ua.getPrincipal());
        blog.setNgayTao(LocalDateTime.now());
        blog.setNgayCapNhat(LocalDateTime.now());
        service.save(blog);
        return new ServiceResult("Sản phẩm đã được thêm thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    // Cập nhật thông tin sản phẩm
//    public ServiceResult update(Long id, Blog blog, MultipartFile file) {
//        Blog blog = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm để cập nhật"));
//        BeanUtils.copyProperties(model, sanPham);
//
//        // Chỉ cập nhật ảnh nếu có ảnh mới (file != null và file không rỗng)
//        if (file != null && !file.isEmpty()) {
//            try {
//                String fileName = saveImage(file);  // Gọi phương thức lưu ảnh và lấy tên file
//                sanPham.setAnh(fileName);  // Cập nhật tên file ảnh mới
//            } catch (IOException e) {
//                return new ServiceResult("Lỗi khi lưu ảnh", SystemConstant.STATUS_FAIL, SystemConstant.CODE_400);
//            }
//        }
//
//        service.update(sanPham);
//        return new ServiceResult("Sản phẩm đã được cập nhật thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }
//
//
//    // Cập nhật nếu không có ảnh mới
//    public ServiceResult update(Long id, SanPhamModel model) {
//        SanPham sanPham = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm để cập nhật"));
//        BeanUtils.copyProperties(model, sanPham);
//
//        // Không thay đổi ảnh nếu không có ảnh mới
//        service.update(sanPham);
//        return new ServiceResult("Sản phẩm đã được cập nhật thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }
//
//
//    public ServiceResult delete(Long id) {
//        service.delete(id);
//        return new ServiceResult("Sản phẩm đã được xóa thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }
//
//
//    public ServiceResult updateStatus(Long id, Integer trangThai) {
//        SanPham sanPham = service.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm để cập nhật trạng thái"));
//        sanPham.setTrangThai(trangThai); // Cập nhật trạng thái
//        service.update(sanPham); // Lưu thay đổi vào database
//        return new ServiceResult("Trạng thái sản phẩm đã được cập nhật thành công", SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }

}
