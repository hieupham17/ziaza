package org.example.datn.repository;

import io.swagger.models.auth.In;
import org.example.datn.entity.SanPham;
import org.example.datn.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Long> {
    @Query("SELECT p FROM SanPhamChiTiet p WHERE p.id = ?1")
    List<SanPhamChiTiet> findByCateId(Long cid);

    List<SanPhamChiTiet> findByIdIn(List<Long> ids);

    Optional<SanPhamChiTiet> findByIdSanPhamAndIdSizeAndIdMauSac(Long idSanPham, Long idSize, Long idMauSac);

    List<SanPhamChiTiet> findByIdSanPham(Long idSanPham);

    SanPhamChiTiet findByIdAndTrangThai(Long id, Integer trangThai);

        // Custom query để lấy sản phẩm với trạng thái "active"
        List<SanPhamChiTiet> findByTrangThai(Integer trangThai);


}
