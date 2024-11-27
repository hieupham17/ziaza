// Hàm mở modal phương thức vận chuyển
function openShippingModal() {
    document.getElementById("shipping-modal").style.display = "block";
    document.body.style.overflow = "hidden"; // Khóa cuộn khi mở modal
}

// Hàm đóng modal phương thức vận chuyển
function closeShippingModal() {
    document.getElementById("shipping-modal").style.display = "none";
    document.body.style.overflow = ""; // Khôi phục cuộn khi đóng modal
}