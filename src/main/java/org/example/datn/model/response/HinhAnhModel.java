package org.example.datn.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.*;
import org.example.datn.model.CommonModel;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HinhAnhModel extends CommonModel {
    Long id;
    Long idSanPham;
    Integer trangThai;
    private List<String> anh;
    private List<MultipartFile> images;
}
