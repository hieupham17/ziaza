package org.example.datn.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BlogRequest {

    private String tacGia;
    private String tieuDe;
    private String moTa;
    private String hinhAnh;

}
