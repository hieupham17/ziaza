package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.HinhAnh;
import org.example.datn.model.response.HinhAnhModel;
import org.example.datn.service.HinhAnhService;
import org.example.datn.service.HinhAnhServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("/hinh-anh")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HinhAnhController {

    private final HinhAnhService hinhAnhService;
    @Autowired
    private final HinhAnhServices hinhAnhServices;

    // Lấy danh sách tất cả hình ảnh
    @GetMapping
    public ResponseEntity<List<HinhAnh>> getAllImages() {
        List<HinhAnh> hinhAnhList = hinhAnhService.getAllProducts();
        return ResponseEntity.ok(hinhAnhList);
    }

    // Lấy thông tin hình ảnh theo ID sản phẩm
    @GetMapping("/{id}")
    public ResponseEntity<HinhAnh> getImageById(@PathVariable Long id) {
        return hinhAnhService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm mới hình ảnh
    @PostMapping
    public ResponseEntity<List<HinhAnh>> addImages(@RequestParam("images") MultipartFile[] images,
                                                   @RequestParam("idSanPham") Long idSanPham) {
        HinhAnhModel model = new HinhAnhModel();
        model.setIdSanPham(idSanPham);

        // Gọi service để tạo các hình ảnh
        List<HinhAnh> hinhAnhs = hinhAnhService.createProduct(model, images);

        return ResponseEntity.status(HttpStatus.CREATED).body(hinhAnhs);
    }

    // Cập nhật thông tin hình ảnh
    @PutMapping("/{id}")
    public ResponseEntity<HinhAnh> updateImage(@PathVariable Long id,
                                               @RequestParam("images") MultipartFile[] images,
                                               @RequestParam("idSanPham") Long idSanPham,
                                               @RequestParam("trangThai") Integer trangThai) {
        HinhAnhModel hinhAnhModel = new HinhAnhModel();
        hinhAnhModel.setIdSanPham(idSanPham);
        hinhAnhModel.setTrangThai(trangThai);

        HinhAnh updatedHinhAnh = hinhAnhService.updateProduct(id, hinhAnhModel, images);
        return updatedHinhAnh != null ? ResponseEntity.ok(updatedHinhAnh) : ResponseEntity.notFound().build();
    }

    // Xóa hình ảnh theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        hinhAnhService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //    hieu
     //Lấy hình ảnh theo idSanPham
    @GetMapping("/san-pham/{id}")
    public ResponseEntity<List<HinhAnh>> getImagesByProductId(@PathVariable Long id) {
        List<HinhAnh> hinhAnhs = hinhAnhServices.getImagesByProductId(id); // Lấy hình ảnh theo idSanPham
        if (hinhAnhs.isEmpty()) {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không có hình ảnh
        }
        return ResponseEntity.ok(hinhAnhs); // Trả về hình ảnh nếu có
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateTrangThai(@PathVariable Long id, @RequestParam("status") int status) {
        hinhAnhService.updateTrangThai(id, status);
        return ResponseEntity.noContent().build();
    }


}
