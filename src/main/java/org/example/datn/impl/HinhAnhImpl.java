package org.example.datn.impl;

import org.example.datn.entity.HinhAnh;
import org.example.datn.model.response.HinhAnhModel;
import org.example.datn.repository.HinhAnhRepository;
import org.example.datn.service.HinhAnhService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HinhAnhImpl implements HinhAnhService {
    private final HinhAnhRepository repository;
    private static final String UPLOAD_DIR = "images";  // Đường dẫn thư mục lưu ảnh trong dự án

    public HinhAnhImpl(HinhAnhRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HinhAnh> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Optional<HinhAnh> getProductById(Long productId) {
        return Optional.ofNullable(repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm!")));
    }

    @Override
    public List<HinhAnh> createProduct(HinhAnhModel model, MultipartFile[] images) {
        List<HinhAnh> hinhAnhs = new ArrayList<>();

        // Kiểm tra và tạo thư mục nếu chưa có
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        for (MultipartFile image : images) {
            // Tạo tên ảnh mới (với UUID để tránh trùng lặp)
            String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String imagePath = UPLOAD_DIR + File.separator + imageName;

            HinhAnh hinhAnh = new HinhAnh();
            hinhAnh.setIdSanPham(model.getIdSanPham());
            hinhAnh.setAnh(imageName);  // Lưu tên ảnh, không phải đường dẫn đầy đủ
            hinhAnh.setTrangThai(1);  // Trạng thái ảnh (có thể thay đổi tùy theo yêu cầu)

            // Kiểm tra nếu ảnh đã tồn tại trong database (theo tên ảnh)
            Optional<HinhAnh> existingImage = repository.findByAnh(imageName);
            if (existingImage.isPresent()) {
                // Nếu ảnh đã tồn tại, chỉ cần cập nhật lại thông tin (có thể là trạng thái hoặc thời gian tạo, cập nhật)
                hinhAnh = existingImage.get();
                hinhAnh.setTrangThai(1);  // Ví dụ, chỉ cập nhật trạng thái
                hinhAnh.setNgayCapNhat(LocalDateTime.now());
            } else {
                // Nếu ảnh chưa có, lưu ảnh mới vào cơ sở dữ liệu
                saveImage(image, imagePath);  // Lưu ảnh vào thư mục
            }

            hinhAnhs.add(repository.save(hinhAnh));
        }

        return hinhAnhs;  // Trả về danh sách ảnh đã lưu hoặc cập nhật
    }

    // Hàm lưu ảnh vào thư mục
    private void saveImage(MultipartFile image, String imagePath) {
        try {
            byte[] bytes = image.getBytes();
            Path path = Paths.get(imagePath);
            Files.write(path, bytes);  // Lưu ảnh vào thư mục
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể lưu hình ảnh!");
        }
    }

    @Override
    public HinhAnh updateProduct(Long productId, HinhAnhModel model, MultipartFile[] images) {
        // Kiểm tra xem sản phẩm có tồn tại không
        HinhAnh existingHinhAnh = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Hình ảnh không tồn tại"));

        // Cập nhật thông tin sản phẩm
        existingHinhAnh.setIdSanPham(model.getIdSanPham());
        existingHinhAnh.setTrangThai(model.getTrangThai());

        // Cập nhật ảnh nếu có ảnh mới
        if (images != null && images.length > 0) {
            // Tạo tên ảnh mới (có thể sử dụng UUID để tránh trùng lặp)
            String imageName = UUID.randomUUID().toString() + "_" + images[0].getOriginalFilename();
            String imagePath = UPLOAD_DIR + File.separator + imageName;

            // Lưu ảnh mới vào thư mục
            saveImage(images[0], imagePath);

            // Cập nhật tên ảnh mới trong cơ sở dữ liệu
            existingHinhAnh.setAnh(imageName);
        }

        // Cập nhật thời gian cập nhật
        existingHinhAnh.setNgayCapNhat(LocalDateTime.now());

        // Lưu lại đối tượng đã cập nhật
        return repository.save(existingHinhAnh);
    }


    @Override
    public void deleteProduct(Long productId) {
        // Kiểm tra xem hình ảnh có tồn tại không
        HinhAnh hinhAnh = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Hình ảnh không tồn tại"));

        // Xóa hình ảnh trong cơ sở dữ liệu
        repository.delete(hinhAnh);

        // Xóa ảnh từ hệ thống tệp tin nếu cần
        File file = new File(UPLOAD_DIR + File.separator + hinhAnh.getAnh());
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new RuntimeException("Không thể xóa tệp ảnh khỏi hệ thống");
            }
        }
    }


    @Override
    public List<HinhAnh> searchProductByName(String productName) {
        return null;  // Tìm kiếm sản phẩm theo tên nếu cần
    }

    @Override
    public void updateTrangThai(Long id, Integer trangThai) {
        HinhAnh hinhAnh = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hình ảnh không tồn tại"));

        hinhAnh.setTrangThai(trangThai);  // Cập nhật trạng thái theo giá trị được truyền vào
        repository.save(hinhAnh);  // Lưu lại vào cơ sở dữ liệu
    }


}
