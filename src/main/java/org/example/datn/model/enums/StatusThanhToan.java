package org.example.datn.model.enums;

public enum StatusThanhToan {
    CHUA_THANH_TOAN(0),
    DA_THANH_TOAN(1),
    KHONG_THANH_CONG(2),
    DANG_XU_LY(3),
    HOAN_TIEN(4),
    HUY(5),
    CHO_XAC_NHAN(6);

    private final int value;

    StatusThanhToan(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
