package org.example.datn.service;

import lombok.RequiredArgsConstructor;
import org.example.datn.entity.SanPham;
import org.example.datn.entity.SanPhamChiTiet;
import org.example.datn.entity.YeuCauDoiTraChiTiet;
import org.example.datn.repository.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietService {
    @Autowired
    private SanPhamChiTietRepository repo;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    public Optional<SanPhamChiTiet> findById(Long id) {
        return repo.findById(id);
    }

    public List<SanPhamChiTiet> findAll() {
        return repo.findAll();
    }

    public List<SanPhamChiTiet> findByCateId(Long cid) {
        return repo.findByCateId(cid);
    }

    public SanPhamChiTiet save(SanPhamChiTiet sanPhamChiTiet) {
        return repo.save(sanPhamChiTiet);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<SanPhamChiTiet> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }

    public Optional<SanPhamChiTiet> findByIdSanPhamAndIdSizeAndIdMauSac(Long idSanPham, Long idSize, Long idMauSac) {
        return repo.findByIdSanPhamAndIdSizeAndIdMauSac(idSanPham, idSize, idMauSac);
    }

    // Chi lay san pham chi tiet dang hoat dong
    public List<SanPhamChiTiet> getAllActive() {
        return sanPhamChiTietRepository.findByTrangThai(1);
    }

    //kiem tra ....
    public SanPhamChiTiet getActive(Long id) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdAndTrangThai(id, 1);
        if (spct == null) {
            throw new RuntimeException("san pham nay khong hoat dong");
        }
        return spct;
    }

    public String addToCart(Long idSPCT, Integer soLuongGioHang) {
        // Kiểm tra sản phẩm có hoạt động và có đủ số lượng không
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findByIdAndTrangThai(idSPCT, 1);
        if (sanPhamChiTiet == null) {
            return "san pham nay khong hoat dong";
        }

        if (sanPhamChiTiet.getSoLuong() < soLuongGioHang) {
            return "không có đủ số lượng";
        }

        // Trừ số lượng sản phẩm
        sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuongGioHang);
        sanPhamChiTietRepository.save(sanPhamChiTiet);

        // Thêm sản phẩm vào giỏ hàng
        SanPhamChiTiet cartItem = new SanPhamChiTiet();
        cartItem.setId(idSPCT);
        cartItem.setSoLuong(soLuongGioHang);
        sanPhamChiTietRepository.save(cartItem);

        return "them san pham vao gio hang thanh cong";
    }

    // Xoá sản phẩm khỏi giỏ hàng và tăng lại số lượng sản phẩm
    public String removeFromCart(Long cartItemId) {
        SanPhamChiTiet cartItem = sanPhamChiTietRepository.findById(cartItemId).orElse(null);
        if (cartItem == null) {
            return "khong tim thay san pham";
        }

        // Tăng lại số lượng sản phẩm
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(cartItem.getId()).orElse(null);
        if (spct != null) {
            spct.setSoLuong(spct.getSoLuong() + cartItem.getSoLuong());
            sanPhamChiTietRepository.save(spct);
        }

        // Xóa sản phẩm khỏi giỏ hàng
        sanPhamChiTietRepository.delete(cartItem);

        return "xoa khoi gio hang thanh cong";
    }

    public List<SanPhamChiTiet> findByIdSanPham(Long idSanPham) {
        return repo.findByIdSanPham(idSanPham);
    }
    public void saveAll(List<SanPhamChiTiet> sanPhamChiTiets) {
        repo.saveAll(sanPhamChiTiets);
    }
}
