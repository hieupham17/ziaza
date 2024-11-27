package org.example.datn.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusGioHang {

    CHUA_DAT_HANG(0), // Khi sản phẩm vẫn còn trong giỏ hàng và bạn chưa quyết định đặt mua.
    DA_DAT_HANG(1), //Khi đã xác nhận mua sản phẩm và gửi đơn hàng cho người bán.
    DA_THANH_TOAN(2), // Khi đã hoàn tất thanh toán cho đơn hàng.
    DANG_XU_LY(3), // Khi đơn hàng đang được người bán chuẩn bị và đóng gói để giao.
    DA_GIAO_HANG(4); // Khi sản phẩm đã được giao đến địa chỉ của bạn.

    StatusGioHang(Integer value) {
        this.value = value;
    }

    @JsonValue
    private final Integer value;

    public Integer getValue() {
        return this.value;
    }
}
