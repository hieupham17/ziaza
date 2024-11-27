package org.example.datn.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.CommonModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhuyenMaiRequest extends CommomRequest {

    private Long id;
    private String ten;
    private String moTa;
    private Integer loai;
    private BigDecimal giaTri;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private Integer trangThai;
}