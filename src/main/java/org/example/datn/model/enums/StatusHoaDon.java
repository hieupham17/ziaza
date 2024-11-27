package org.example.datn.model.enums;

public enum StatusHoaDon {
    CHO_XAC_NHAN(0), //Đơn hàng vừa được đặt và đang chờ người bán xác nhận.
    CHO_THANH_TOAN(1), //Đơn hàng thanh toán khuyển khoản được tạo nhưng chưa hoàn tất thanh toán.
    CHO_GIAO_HANG(2), //Đơn hàng đang chờ người bán chuẩn bị giao hàng.
    VAN_CHUYEN(3), //Đơn hàng đã được gửi đi và đang trên đường vận chuyển.
    HOAN_THANH(4), //Đơn hàng đã được giao và khách hàng đã xác nhận nhận hàng, không có vấn đề gì.
    DA_HUY(5), //Đơn hàng đã bị hủy bởi khách hàng hoặc người bán.
    TRA_HANG(6); //Đơn hàng đã được trả lại và đang chờ xử lý hoàn trả.  // Đã hoàn trả

    private final int value;

    StatusHoaDon(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
