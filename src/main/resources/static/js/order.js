async function fetchCartData() {
    const selectedItems = JSON.parse(localStorage.getItem('selectedItems'));
    console.log(`Updated selected items in localStorage:`, selectedItems);

    if (selectedItems && selectedItems.length > 0) {
        try {
            const response = await fetch('gio-hang-chi-tiet/get-by-ids', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(selectedItems)
            });

            if (!response.ok) {
                throw new Error('Phản hồi mạng không hợp lệ');
            }

            const data = await response.json();
            console.log(data); // Kiểm tra cấu trúc dữ liệu
            populateTable(data.data); // Sử dụng data.data
        } catch (error) {
            console.error('Có vấn đề với yêu cầu:', error);
        }
    } else {
        console.log('Không có sản phẩm nào được chọn.');
    }
}

document.addEventListener('DOMContentLoaded', fetchCartData);

function populateTable(data) {
    const tableBody = document.querySelector('#products tbody');
    tableBody.innerHTML = ''; // Xóa nội dung cũ

    data.forEach(item => {
        const gia = item.sanPhamChiTiet.gia; // Lấy giá từ sản phẩm chi tiết
        const soLuong = item.soLuong; // Lấy số lượng
        const tongCong = gia * soLuong; // Tính tổng cộng

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.sanPham.ten}</td>
            <td>${gia.toLocaleString()} đ</td>
            <td>${soLuong}</td>
            <td>${tongCong.toLocaleString()} đ</td>
        `;
        tableBody.appendChild(row);
    });
}
