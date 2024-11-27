$(document).ready(function() {
    const token = localStorage.getItem('token');

    $.ajax({
        url: '/gio-hang-chi-tiet/get-list',
        method: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        success: function(response) {
            if (response.data) {
                renderCartItems(response.data);
            } else {
                alert('Không có sản phẩm nào trong giỏ hàng.');
            }
        },
        error: function(err) {
            console.error("Lỗi khi lấy giỏ hàng:", err);
            alert('Đã xảy ra lỗi khi tải giỏ hàng. Vui lòng thử lại.');
        }
    });

    function renderCartItems(items) {
        const cartItemsContainer = $('#cartItems');
        let total = 0;
        cartItemsContainer.empty(); // Xóa các sản phẩm trước đó

        items.forEach(item => {
            const itemTotal = item.gia;
            total += itemTotal;

            const cartItemHtml = `
                <div class="cart_item">
                    <img class="cart_thumb" src="${item.imageUrl}" alt="${item.name}"/>
                    <div class="cart_info">
                        <h3>
                            ${item.sanPham.ten}
                            <span>
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" class="size-6">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12"/>
                                </svg>
                            </span>
                        </h3>
                        <div class="cart_item_price">${itemTotal.toLocaleString()}đ</div>
                        <div class="cart_item_quantity">
                            <div class="quantity_group">
                                <button onclick="updateQuantity(${item.id}, -1)">-</button>
                                <input type="number" value="${item.soLuong}" readonly/>
                                <button onclick="updateQuantity(${item.id}, 1)">+</button>
                            </div>
                            <b><span style="color: red !important;">${itemTotal.toLocaleString()}đ</span></b>

                        </div>
                    </div>
                </div>`;
            cartItemsContainer.append(cartItemHtml); // Thêm sản phẩm mới
        });

        $('#cartTotal b').text(total.toLocaleString() + 'đ');
        $('#cartCount').text(`Có ${items.length} sản phẩm trong giỏ hàng`);
    }

    // Hàm để cập nhật số lượng
    window.updateQuantity = function(itemId, change) {
        // Implement the logic to update quantity
    };
});
