package org.example.datn.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class KhuyenMaiCreateUpdateRequest {

    private String ma;
    private String ten;
    private String moTa;
    private Integer loai; // 1 = Khuyến mãi cho sản phẩm, 2 = Khuyến mãi cho người dùng
    private BigDecimal giaTri;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;

    private List<Long> idSanPhamList;

    private List<Long> idNguoiDungList;
}
