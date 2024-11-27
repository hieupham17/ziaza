package org.example.datn.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum LoaiYeuCau {
    DOI_HANG("Đổi hàng"),
    TRA_HANG("Trả hàng");

    private final String value;

    LoaiYeuCau(String value) {
        this.value = value;
    }
}
