package org.example.datn.model.enums;

public enum TrangThaiDoiTra {
    CHO_XU_LY(0),
    DANG_XU_LY(1),
    HOAN_THANH(2),
    TU_CHOI(3);

    private final int value;

    TrangThaiDoiTra(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
