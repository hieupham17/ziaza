async function fetchCartData() {
    const token = localStorage.getItem('token');
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };
    const response = await fetch('gio-hang-chi-tiet/get-list', {
        method: 'GET',
        headers: headers,
    });
    const result = await response.json();
    const data = result.data; // Lấy dữ liệu từ result
    renderCart(data);
}

function handleCheckboxClick(itemId, isChecked) {
    const selectedItems = JSON.parse(localStorage.getItem('selectedItems')) || [];
    if (isChecked) {
        selectedItems.push(itemId);
    } else {
        const index = selectedItems.indexOf(itemId);
        if (index > -1) {
            selectedItems.splice(index, 1);
        }
    }
    localStorage.setItem('selectedItems', JSON.stringify(selectedItems));
    updateTotalSection();
}

function handleCheckout() {
    const selectedItems = JSON.parse(localStorage.getItem('selectedItems')) || [];

    if (selectedItems.length === 0) {
        Swal.fire({
            icon: 'warning',
            title: 'Cảnh báo',
            text: 'Không có sản phẩm nào được chọn!',
            confirmButtonText: 'OK'
        });
    } else {
        Swal.fire({
            icon: 'question',
            title: 'Bạn có chắc chắn?',
            text: 'Bạn muốn tiếp tục đến trang thanh toán?',
            showCancelButton: true,
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = '/checkout';
            }
        });
    }
}



function updateTotalSection() {
    const selectedItems = JSON.parse(localStorage.getItem('selectedItems')) || [];
    const productItems = document.querySelectorAll('.product-item');
    let totalAmount = 0;
    let selectedCount = 0;
    productItems.forEach(item => {
        const checkbox = item.querySelector('input[type="checkbox"]');
        if (checkbox.checked) {
            const total = parseFloat(item.querySelector('.total').textContent.replace(/[^\d.-]/g, ''));
            totalAmount += total;
            selectedCount++;
        }
    });
    const totalSection = document.querySelector('.total-section .total');
    totalSection.innerHTML = `Tổng thanh toán (${selectedCount} Sản phẩm): ₫${totalAmount}`;
}

function handleQuantityChange(itemId, isIncreasing) {
    const productItem = document.querySelector(`.product-item input[data-id="${itemId}"]`).closest('.product-item');
    let currentQuantity = parseInt(productItem.querySelector('.quantity span').textContent);
    const newQuantity = isIncreasing ? currentQuantity + 1 : currentQuantity - 1;

    if (newQuantity < 1) return; // Không cho phép số lượng nhỏ hơn 1

    // Gọi đến API để cập nhật số lượng
    const token = localStorage.getItem('token');
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };

    fetch(`/gio-hang-chi-tiet/change/${itemId}`, {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(newQuantity)
    })
        .then(response => response.json())
        .then(result => {
            if (result.code == '200') {
                // Cập nhật hiển thị số lượng và tổng tiền
                productItem.querySelector('.quantity span').textContent = newQuantity;
                const price = parseFloat(productItem.querySelector('.price').textContent.replace(/[^\d.-]/g, ''));
                const total = newQuantity * price;
                productItem.querySelector('.total').textContent = `₫${total}`;
                updateTotalSection();
            } else {
                console.error('Cập nhật số lượng thất bại', result.message);
            }
        })
        .catch(error => console.error('Lỗi khi gọi API', error));
}

function hienThiThongBao(message, success = true) {
    const notification = document.getElementById('notification');
    notification.innerText = message;
    notification.style.backgroundColor = success ? 'rgba(0, 128, 0, 0.8)' : 'rgba(255, 0, 0, 0.8)';
    notification.style.display = 'block';
    setTimeout(() => {
        notification.style.display = 'none';
    }, 3000);
}

function handleDelete(itemId) {
    Swal.fire({
        title: 'Bạn có chắc chắn muốn xóa sản phẩm này?',
        text: 'Hành động này không thể hoàn tác!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            deleteItem(itemId);
        }
    });
}

function deleteItem(itemId) {
    const token = localStorage.getItem('token');
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };

    fetch(`/gio-hang-chi-tiet/${itemId}`, {
        method: 'DELETE',
        headers: headers
    })
        .then(response => response.json())
        .then(result => {
            if (result.code === '200') {
                toastr.success('Sản phẩm đã được xóa thành công!', 'Thành công');
                fetchCartData();
                setTimeout(function() {
                    location.reload();
                }, 500);
            } else {
                console.error('Xóa sản phẩm thất bại', result.message);
                toastr.error('Xóa sản phẩm thất bại!', 'Lỗi');
            }
        })
        .catch(error => {
            console.error('Lỗi khi gọi API', error);
            toastr.error('Có lỗi xảy ra khi gọi API.', 'Lỗi');
        });
}


function renderCart(items) {
    const selectedItems = JSON.parse(localStorage.getItem('selectedItems')) || [];
    const productSection = document.querySelector('.product-section');
    productSection.innerHTML = '';
    if (items.length === 0) {
        productSection.innerHTML = '<p>Không có sản phẩm nào.</p>';
        return;
    }
    let totalAmount = 0;
    items.forEach(item => {
        const sanPham = item.sanPham || {}; // Xử lý trường hợp sanPham là null
        const sanPhamChiTiet = item.sanPhamChiTiet || {};
        const total = sanPham.gia * item.soLuong;
        totalAmount += total;
        const productItem = document.createElement('div');
        productItem.className = 'product-item';
        const itemHtml = `
           <div>
                <input type="checkbox" ${selectedItems.includes(item.id) ? 'checked' : ''} data-id="${item.id}">
                <img class="product-image" src="/images/${sanPham.anh || ''}" alt="${sanPham.ten || 'Không có ảnh'}">
                <span>
                    <span style="font-size: 20px;">${sanPham.ten || 'Sản phẩm không xác định'}</span>
                    <span style="margin-left: 10px;">Phân loại: ${sanPhamChiTiet.mauSac?.ten || 'N/A'}, Size: ${sanPhamChiTiet.size?.ten || 'N/A'}</span>
                </span>
            </div>
            <div class="price">₫${sanPham.gia}</div>
            <div class="quantity">
                <button>-</button>
                <span>${item.soLuong}</span>
                <button>+</button>
            </div>
            <div class="total">₫${total}</div>
            <div><button class="btn delete-btn">Xóa</button></div>
        `;
        productItem.innerHTML = itemHtml;
        productSection.appendChild(productItem);
    });
    updateTotalSection();

    // Thêm sự kiện cho các nút tăng giảm số lượng
    document.querySelectorAll('.product-item .quantity button').forEach(button => {
        button.addEventListener('click', (e) => {
            const itemId = e.target.closest('.product-item').querySelector('input[data-id]').dataset.id;
            const isIncreasing = e.target.textContent === '+';
            handleQuantityChange(itemId, isIncreasing);
        });
    });

    // Thêm sự kiện cho các ô checkbox
    document.querySelectorAll('.product-item input[type="checkbox"]').forEach(checkbox => {
        checkbox.addEventListener('click', (e) => {
            handleCheckboxClick(e.target.dataset.id, e.target.checked);
        });
    });

    // Thêm sự kiện cho các nút Xóa
    document.querySelectorAll('.product-item .delete-btn').forEach(button => {
        button.addEventListener('click', (e) => {
            const itemId = e.target.closest('.product-item').querySelector('input[data-id]').dataset.id;
            handleDelete(itemId);
        });
    });

    // Thêm sự kiện cho nút Mua Hàng
    document.querySelector('.checkout-btn').addEventListener('click', handleCheckout);
}


document.addEventListener('DOMContentLoaded', () => {
    // Xóa selectedItems khỏi local storage khi trang được tải lại
    localStorage.removeItem('selectedItems');
    fetchCartData();
});
document.addEventListener('DOMContentLoaded', () => {
    // Xóa selectedItems khỏi local storage khi trang được tải lại
    localStorage.removeItem('selectedItems');
    fetchCartData();

    // Thêm sự kiện cho nút "Chọn Tất cả"
    document.getElementById('selectAllCheckbox').addEventListener('change', (e) => {
        const isChecked = e.target.checked;
        document.querySelectorAll('.product-item input[type="checkbox"]').forEach(checkbox => {
            checkbox.checked = isChecked;
            handleCheckboxClick(checkbox.dataset.id, isChecked);
        });
    });
});