package org.example.datn.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.enums.LoaiYeuCau;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YeuCauDoiTraRequest {

    private Long idHoaDon;
    private List<Long> idSanPhamChiTiets;
    private LoaiYeuCau loai;
    private String lyDo;
    private String ghiChu;

}
