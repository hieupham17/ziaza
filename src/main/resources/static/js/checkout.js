let fetchedAddresses = []; // Biến toàn cục để lưu địa chỉ đã fetch
let addressDataId;
let phuongThucVanChuyenId;
let phuongThucThanhToanId = null;
let isAddingNewAddress = false;

async function fetchDeliveryAddress() {
    try {
        const token = localStorage.getItem('token');

        const response = await fetch('/dia-chi/get-active', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const result = await response.json();
        const addressElement = document.getElementById('delivery-address');
        if (result.data && result.data.length > 0) {
            const addressData = result.data[0];
            addressDataId = addressData.id; // Gán ID địa chỉ ban đầu
            console.log('Địa chỉ giao hàng: ', addressDataId);
            addressElement.textContent = `
                ${addressData.hoTen}, ${addressData.diaChi}, ${addressData.thanhPho}, ${addressData.quocGia}
            `;
        } else {
            addressElement.textContent = 'Không có địa chỉ nào';
        }
    } catch (error) {
        console.error('Error fetching delivery address:', error);
        document.getElementById('delivery-address').textContent = 'Lỗi khi tải địa chỉ';
    }
}

async function fetchAddressList() {
    try {
        const token = localStorage.getItem('token');

        const response = await fetch('/dia-chi/get-list', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const result = await response.json();
        fetchedAddresses = result.data; // Lưu kết quả vào biến toàn cục
        const addressListElement = document.getElementById('address-list');

        if (fetchedAddresses && fetchedAddresses.length > 0) {
            addressListElement.innerHTML = fetchedAddresses.map(address => `
                 <div style="background-color: #f0f0f0; border-radius: 10px; padding: 10px; margin-bottom: 10px;">
                    <input type="radio" name="address" value="${address.id}" id="address-${address.id}">
                    <label for="address-${address.id}">
                        <strong style="font-size: 1.5em;">${address.hoTen}</strong> <span style="font-size: 1em;">(${address.sdt})</span> ${address.trangThai === 1 ? '<span style="color: red; font-weight: bold; margin-left: 10px;">Mặc định</span>' : ''}<br>
                        ${address.diaChi}, ${address.thanhPho}, ${address.quocGia}
                    </label>
                </div>
            `).join('');
        } else {
            addressListElement.innerHTML = 'Không có địa chỉ nào';
        }
    } catch (error) {
        console.error('Error fetching address list:', error);
        document.getElementById('address-list').innerHTML = 'Lỗi khi tải danh sách địa chỉ';
    }
}

function selectAddress() {
    const selectedAddressId = document.querySelector('input[name="address"]:checked');
    if (selectedAddressId) {
        const addressId = selectedAddressId.value;
        const selectedAddress = fetchedAddresses.find(address => address.id == addressId);

        if (selectedAddress) {
            const addressElement = document.getElementById('delivery-address');

            // Cập nhật addressDataId với địa chỉ được chọn
            addressDataId = selectedAddress.id; // Cập nhật ID địa chỉ
            console.log('Địa chỉ đã chọn: ', addressDataId);

            addressElement.textContent = `
                ${selectedAddress.hoTen}, ${selectedAddress.diaChi}, ${selectedAddress.thanhPho}, ${selectedAddress.quocGia}
            `;
        }

        closeModal(); // Đóng modal sau khi chọn địa chỉ
    } else {
        toastr.error('Vui lòng chọn một địa chỉ.', 'Lỗi');
    }
}

function showNewAddressForm() {
    document.getElementById('address-list').style.display = 'none';
    document.getElementById('new-address-form').style.display = 'block';
    document.getElementById('add-address-button').style.display = 'none';
    document.getElementById('back-button').style.display = 'block';
    isAddingNewAddress = true;
}

async function addNewAddress() {
    const newHoTen = document.getElementById('new-hoTen').value;
    const newSdt = document.getElementById('new-sdt').value;
    const newDiaChi = document.getElementById('new-diaChi').value;
    const newThanhPho = document.getElementById('new-thanhPho').value;
    const newQuocGia = document.getElementById('new-quocGia').value;

    // Kiểm tra các điều kiện ràng buộc
    if (!newHoTen || newHoTen.length > 100) {
        toastr.error('Họ tên không được để trống và không vượt quá 100 ký tự.', 'Lỗi');
        return;
    }
    if (!newSdt || newSdt.length < 10 || newSdt.length > 15) {
        toastr.error('Số điện thoại không được để trống và phải có từ 10 đến 15 ký tự.', 'Lỗi');
        return;
    }
    if (!newDiaChi) {
        toastr.error('Địa chỉ không được để trống.', 'Lỗi');
        return;
    }
    if (!newThanhPho) {
        toastr.error('Thành phố không được để trống.', 'Lỗi');
        return;
    }
    if (!newQuocGia) {
        toastr.error('Quốc gia không được để trống.', 'Lỗi');
        return;
    }

    // Dữ liệu gửi đi
    const requestData = {
        hoTen: newHoTen,
        sdt: newSdt,
        diaChi: newDiaChi,
        thanhPho: newThanhPho,
        quocGia: newQuocGia
    };

    // Hiển thị hộp thoại xác nhận trước khi gửi yêu cầu
    const isConfirmed = await Swal.fire({
        title: 'Xác nhận',
        text: "Bạn có chắc chắn muốn thêm địa chỉ này không?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Hủy',
    });

    if (isConfirmed.isConfirmed) {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch('dia-chi/insert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(requestData)
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const result = await response.json();

            toastr.success('Địa chỉ đã được thêm thành công!', 'Thành công');
            console.log(result);

            document.getElementById('new-address-form').style.display = 'none';
            document.getElementById('address-list').style.display = 'block';
            document.getElementById('error-message').innerText = ''; // Xóa thông báo lỗi
            isAddingNewAddress = false;

            fetchAddressList();
            clearFormModal();

        } catch (error) {
            console.error('Error adding new address:', error);
            toastr.error('Lỗi khi thêm địa chỉ mới.', 'Lỗi');
        }
    } else {
       // Nếu người dùng hủy, thông báo với toastr
        toastr.info('Bạn đã hủy thao tác thêm địa chỉ.', 'Thông báo');
    }
}



function handleConfirmButton() {
    if (isAddingNewAddress) {
        addNewAddress();
    } else {
        selectAddress();
    }
}

function cancelNewAddress() {
    // Ẩn form và hiển thị lại danh sách địa chỉ
    document.getElementById('new-address-form').style.display = 'none';
    document.getElementById('address-list').style.display = 'block';
    document.getElementById('add-address-button').style.display = 'block';
    document.getElementById('back-button').style.display = 'none';
    isAddingNewAddress = false;
    document.getElementById('error-message').innerText = ''; // Xóa thông báo lỗi
}


function showModal() {
    document.getElementById('address-modal').style.display = 'block';
    document.body.style.overflow = "hidden"; //
    fetchAddressList(); // Gọi hàm lấy danh sách địa chỉ khi hiển thị modal
}

function clearFormModal() {
    document.getElementById('add-address-button').style.display = 'block';
    document.getElementById('new-hoTen').value = '';
    document.getElementById('new-sdt').value = '';
    document.getElementById('new-diaChi').value = '';
    document.getElementById('new-thanhPho').value = '';
    document.getElementById('new-quocGia').value = '';
}

function closeModal() {
    document.getElementById('address-modal').style.display = 'none';
    document.body.style.overflow = "";
    cancelNewAddress();
    clearFormModal();
}


// Gọi hàm khi trang được tải
document.addEventListener('DOMContentLoaded', fetchDeliveryAddress);

// Đóng modal khi nhấp bên ngoài
window.onclick = function (event) {
    const addressModal = document.getElementById("address-modal");
    const shippingModal = document.getElementById("shipping-modal");

    if (event.target === addressModal || event.target === shippingModal) {
        closeModal();
    }
};

let data; // Biến lưu trữ dữ liệu giỏ hàng

async function fetchCartData() {
    try {
        const token = localStorage.getItem('token');
        const headers = new Headers();
        if (token) {
            headers.append('Authorization', `Bearer ${token}`);
        }

        const response = await fetch('gio-hang-chi-tiet/get-list', {
            method: 'GET',
            headers: headers,
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        data = await response.json();

        if (data && data.code === "200" && data.data) {
            const productCount = data.data.length;
            document.getElementById('productCount').textContent = productCount;
            document.getElementById('productCountSummary').textContent = productCount;

            const totalPrice = data.data.reduce((total, item) => total + (item.gia * item.soLuong), 0);
            document.getElementById('totalPrice').textContent = `${totalPrice.toLocaleString()} đ`;

            const cartList = document.getElementById('cartList');
            cartList.innerHTML = ''; // Xóa danh sách hiện tại
            data.data.forEach(item => {
                const productDetail = item.sanPhamChiTiet;
                const product = item.sanPham;
                const cartItem = document.createElement('div');
                cartItem.classList.add('cart_item');
                cartItem.innerHTML = `
                    <input type="checkbox" class="product-checkbox" id="product-${item.id}" onclick="handleCheckboxChange(${item.id})" />
                    <img class="cart_thumb" src="${productDetail.imageUrl || 'default-image.png'}" alt=""/>
                    <div class="cart_info">
                        <h3>
                            <h2>${product.ten}</h2>
                           <span>(Màu: ${productDetail.mauSac.ten}, Size: ${productDetail.size.ten})</span>
                        </h3>
                        <div class="cart_item_price">${item.gia.toLocaleString()} đ</div>
                        <div class="cart_item_quantity">
                            <div class="quantity_group">
                                <button onclick="decreaseQuantity(this, ${item.id})">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" class="size-6">
                                        <path strokeLinecap="round" strokeLinejoin="round" d="M5 12h14"/>
                                    </svg>
                                </button>
                                <input type="number" value="${item.soLuong}" min="1" onchange="updateQuantity(this, ${item.id})"/>
                                <button onclick="increaseQuantity(this, ${item.id})">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" class="size-6">
                                        <path strokeLinecap="round" strokeLinejoin="round" d="M12 4.5v15m7.5-7.5h-15"/>
                                    </svg>
                                </button>
                            </div>
                            <b style="color: red;"  id="totalItemPrice-${item.id}">${(item.gia * item.soLuong).toLocaleString()} đ</b>
                        </div>
                    </div>
                `;
                cartList.appendChild(cartItem);
            });
        } else {
            console.error('Invalid data structure:', data);
        }
    } catch (error) {
        console.error('Error fetching cart data:', error);
    }
}


function increaseQuantity(button, itemId) {
    const quantityInput = button.previousElementSibling;
    let quantity = parseInt(quantityInput.value);
    quantity++;
    quantityInput.value = quantity;
    updateTotalPrice(itemId, quantity);
}

function decreaseQuantity(button, itemId) {
    const quantityInput = button.nextElementSibling;
    let quantity = parseInt(quantityInput.value);
    if (quantity > 1) {
        quantity--;
        quantityInput.value = quantity;
        updateTotalPrice(itemId, quantity);
    }
}

function updateQuantity(input, itemId) {
    const quantity = parseInt(input.value);
    if (quantity < 1) {
        input.value = 1;
    }
    updateTotalPrice(itemId, parseInt(input.value));
}

function updateTotalPrice(itemId, quantity) {
    const item = data.data.find(i => i.id === itemId);
    const price = item.gia;
    const totalPriceElement = document.getElementById('totalPrice');

    const totalItemPrice = price * quantity;

    // Cập nhật giá trị tổng cho từng mặt hàng
    const totalItemPriceElement = document.getElementById(`totalItemPrice-${itemId}`);
    totalItemPriceElement.textContent = `${totalItemPrice.toLocaleString()} đ`;

    // Tính tổng giá trị của tất cả mặt hàng
    const allItems = document.querySelectorAll('.cart_item');
    const grandTotal = Array.from(allItems).reduce((sum, item) => {
        const itemQuantity = parseInt(item.querySelector('input[type="number"]').value);
        const itemPrice = parseInt(item.querySelector('.cart_item_price').textContent.replace(/ đ/g, '').replace(/,/g, ''));
        return sum + (itemQuantity * itemPrice);
    }, 0);

    totalPriceElement.textContent = `${grandTotal.toLocaleString()} đ`;
}

function handleCheckboxChange(itemId) {
    const checkbox = document.getElementById(`product-${itemId}`);
    const selectedItems = JSON.parse(localStorage.getItem('selectedItems')) || [];

    console.log(`Checkbox for item ${itemId} is now ${checkbox.checked}.`);
    console.log(`Current selected items:`, selectedItems);

    // Kiểm tra nếu checkbox được đánh dấu
    if (checkbox.checked) {
        // Nếu chưa có, thêm itemId vào danh sách đã chọn
        if (!selectedItems.includes(itemId)) {
            selectedItems.push(itemId);
            console.log(`Added item ${itemId} to selected items.`);
        } else {
            console.log(`Item ${itemId} is already selected.`);
        }
    } else {
        // Nếu không, tìm vị trí của itemId trong danh sách và xóa nó
        const index = selectedItems.indexOf(itemId);
        if (index > -1) {
            selectedItems.splice(index, 1); // Xóa itemId khỏi danh sách đã chọn
            console.log(`Removed item ${itemId} from selected items.`);
        } else {
            console.log(`Item ${itemId} was not found in selected items.`);
        }
    }

    // Cập nhật localStorage với danh sách mới
    localStorage.setItem('selectedItems', JSON.stringify(selectedItems));
    console.log(`Updated selected items in localStorage:`, selectedItems);
}


// Gọi hàm fetchCartData để tải dữ liệu giỏ hàng khi trang được tải
window.onload = fetchCartData;


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
    tableBody.innerHTML = ''; // Clear old content
    let totalItemPrice = 0; // Variable to store total item price

    data.forEach(item => {
        const gia = item.sanPham.gia; // Get price from product details
        const soLuong = item.soLuong; // Get quantity
        const tongCong = gia * soLuong; // Calculate total

        totalItemPrice += tongCong; // Add to total item price

        const row = document.createElement('tr');
        row.style.backgroundColor = '#f0f0f0'; // Set gray background
        row.style.borderRadius = '10px'; // Rounded corners
        row.style.marginBottom = '10px'; // Space between items
        row.style.padding = '10px'; // Padding inside the product box

        row.innerHTML = `
            <td class="product-name">
                <img src="images/${item.sanPham.anh}" alt="${item.sanPham.ten}">
                <span>${item.sanPham.ten}</span>
                <span style="font-size: 14px;">(Size: ${item.sanPhamChiTiet.size.ten}, Màu: ${item.sanPhamChiTiet.mauSac.ten})</span>
            </td>
            <td class="product-price">${gia.toLocaleString()} đ</td>
            <td class="product-quantity">${soLuong}</td>
            <td class="product-total">${tongCong.toLocaleString()} đ</td>
        `;
        tableBody.appendChild(row);
    });

    // Update total item price in the table
    document.getElementById('totalItemPrice').textContent = `${totalItemPrice.toLocaleString()} đ`;
    document.querySelector('.total-price').textContent = `Tổng Cộng: ${totalItemPrice.toLocaleString()} ₫`;

    const shippingFee = 30000; // Default shipping fee
    const totalPayment = totalItemPrice + shippingFee;
    document.getElementById('totalPayment').textContent = `${totalPayment.toLocaleString()} đ`;
}


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

async function fetchPaymentMethods() {
    try {
        const response = await fetch('/phuong-thuc-thanh-toan/get-list', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Không thể kết nối với server');
        }

        const result = await response.json();
        const paymentOptionsElement = document.getElementById('payment-options');

        // Kiểm tra xem result.data có phải là mảng và có phần tử hay không
        if (Array.isArray(result.data) && result.data.length > 0) {
            const optionsHtml = result.data.map(option => `
                <div class="payment-option" id="payment-option-${option.id}" onclick="selectPaymentOption(${option.id})">
                    <span>${option.ten}</span>
                </div>
            `).join('');
            paymentOptionsElement.innerHTML = optionsHtml;
        } else {
            paymentOptionsElement.innerHTML = '<p>Không có phương thức thanh toán nào</p>';
        }
    } catch (error) {
        console.error('Lỗi khi lấy phương thức thanh toán:', error);
        document.getElementById('payment-options').innerHTML = '<p>Lỗi khi tải phương thức thanh toán</p>';
    }
}

function selectPaymentOption(id) {
    phuongThucThanhToanId = id; // Cập nhật id của phương thức thanh toán được chọn
    console.log('Phương thức thanh toán đã chọn:', phuongThucThanhToanId);

    // Loại bỏ lớp 'selected' khỏi tất cả các payment option
    const allOptions = document.querySelectorAll('.payment-option');
    allOptions.forEach(option => option.classList.remove('selected'));

    // Thêm lớp 'selected' vào option đã được chọn
    const selectedOption = document.getElementById(`payment-option-${id}`);
    selectedOption.classList.add('selected');
}

// Gọi hàm khi trang được tải
document.addEventListener('DOMContentLoaded', fetchPaymentMethods);


async function fetchShippingMethods() {
    try {
        const response = await fetch('/phuong-thuc-van-chuyen/get-active', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Phản hồi mạng không hợp lệ');
        }

        const result = await response.json();
        const shippingMethodElement = document.getElementById('shipping-method');

        // Kiểm tra xem có dữ liệu hay không
        if (result.data && result.data.length > 0) {
            const shippingData = result.data[0]; // Lấy phương thức vận chuyển đầu tiên

            // Gán id vào biến toàn cục phuongThucVanChuyenId
            phuongThucVanChuyenId = shippingData.id;
            console.log('Phương thức vận chuyển ID:', phuongThucVanChuyenId);

            shippingMethodElement.innerHTML = `
                <img src="/images/fast-delivery.png" alt="Shipping Icon" class="shipping-icon">
                <span class="shipping-text">${shippingData.ten}</span>
                <button class="change-button" onclick="openShippingModal()">Thay đổi</button>
            `;
        } else {
            shippingMethodElement.innerHTML = '<p>Không có phương thức vận chuyển nào</p>';
        }
    } catch (error) {
        console.error('Lỗi khi lấy phương thức vận chuyển:', error);
        document.querySelector('.shipping-method').innerHTML = '<p>Lỗi khi tải phương thức vận chuyển</p>';
    }
}


// Gọi hàm khi trang được tải
document.addEventListener('DOMContentLoaded', fetchShippingMethods);

function showNotification(message, type) {
    const notification = $('<div class="notification"></div>')
        .text(message)
        .css({
            'position': 'fixed',
            'top': '70%',
            'left': '50%',
            'transform': 'translate(-50%, -50%)',
            'padding': '15px 30px',
            'background-color': type === 'success' ? 'green' : 'yellow',
            'color': 'black',
            'border-radius': '5px',
            'font-size': '16px',
            'z-index': '9999'
        });

    $('body').append(notification);

    setTimeout(function () {
        notification.fadeOut(function () {
            notification.remove();
        });
    }, 2000);
}

function showConfirmationModal(callback) {
    // Hiển thị modal
    document.getElementById("confirmationModal").classList.add("show");

    // Xử lý khi nhấn "Yes"
    $('#confirmPlaceOrder').on('click', function () {
        document.getElementById("confirmationModal").classList.remove("show");
        callback(true); // Gọi callback với true khi nhấn Yes
    });

    // Xử lý khi nhấn "No"
    $('#cancelPlaceOrder').on('click', function () {
        document.getElementById("confirmationModal").classList.remove("show");
        callback(false); // Gọi callback với false khi nhấn No
    });
}

function closeConfirmationModal() {
    document.getElementById("confirmationModal").classList.remove("show");
}

async function placeOrder() {
    // Hiển thị modal xác nhận trước khi tiếp tục
    const isConfirmed = await Swal.fire({
        title: "Xác nhận",
        text: "Bạn có chắc chắn muốn tiếp tục đặt hàng?",
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "Xác nhận",
        cancelButtonText: "Hủy",
        reverseButtons: true
    }).then(result => result.isConfirmed);

    if (!isConfirmed) {
        return; // Nếu người dùng không đồng ý, dừng lại
    }

    const idDiaChiGiaoHang = addressDataId;
    const idPhuongThucVanChuyen = phuongThucVanChuyenId;

    // Kiểm tra phương thức thanh toán
    if (!phuongThucThanhToanId) {
        toastr.warning('Vui lòng chọn phương thức thanh toán.', 'Cảnh báo');
        return;
    }

    const idPhuongThucThanhToan = phuongThucThanhToanId;
    const selectedItems = JSON.parse(localStorage.getItem('selectedItems'));

    // Kiểm tra giỏ hàng
    if (!selectedItems || selectedItems.length === 0) {
        toastr.warning('Giỏ hàng của bạn đang trống.', 'Cảnh báo');
        return;
    }

    // Tạo đối tượng HoaDonRequest
    const request = {
        idGioHangChiTiet: selectedItems,
        idDiaChiGiaoHang: idDiaChiGiaoHang,
        idPhuongThucVanChuyen: idPhuongThucVanChuyen,
        idPhuongThucThanhToan: idPhuongThucThanhToan,
        diemSuDung: 0,
        giaTriVoucher: selectedVoucherValue
    };

    const token = localStorage.getItem('token');

    // Gọi API để lưu đơn hàng
    try {
        const response = await fetch('/rest/hoadon/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(request),
        });

        if (!response.ok) {
            throw new Error('Lưu đơn hàng không thành công.');
        }

        const result = await response.json();

        if (result.code === "200") {
            if (result.data === null) {
                // Đặt hàng thành công và chuyển hướng đến /bill
                toastr.success('Đặt hàng thành công!', 'Thành công');
                await applyVoucher();
                window.location.href = '/bill';
            } else if (typeof result.data === 'string') {
                // Chuyển hướng tới VNPay link
                window.location.href = result.data;
            }
        } else {
            toastr.warning(`Có lỗi xảy ra: ${result.message}`, 'Cảnh báo');
        }

    } catch (error) {
        console.error('Error placing order:', error);
        toastr.error('Có lỗi xảy ra khi đặt hàng.', 'Lỗi');
    }
}

async function applyVoucher() {
    const token = localStorage.getItem('token');

    try {
        const response = await fetch(`/ap-dung-khuyen-mai/change?idKhuyenMai=${selectedVoucherId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Áp dụng khuyến mãi không thành công.');
        }

        const result = await response.json();
        console.log('Voucher applied successfully:', result);

        toastr.success('Khuyến mãi đã được áp dụng thành công!', 'Thành công');

    } catch (error) {
        console.error('Error applying voucher:', error);
        toastr.error('Có lỗi xảy ra khi áp dụng khuyến mãi.', 'Lỗi');
    }
}


let selectedVoucherId = null;
let selectedVoucherValue = 0;

async function fetchVouchers() {
    try {
        const token = localStorage.getItem('token');

        const response = await fetch('/khuyen-mai/get-list-by-user', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const result = await response.json();
        const voucherListElement = document.getElementById('voucher-list');

        if (result.data && result.data.length > 0) {
            voucherListElement.innerHTML = result.data.map(voucher => `
                <div style="background-color: #f0f0f0; border-radius: 10px; padding: 10px; margin-bottom: 10px;">
                    <input type="radio" name="voucher" value="${voucher.id}" data-value="${voucher.giaTri}" id="voucher-${voucher.id}">
                    <label for="voucher-${voucher.id}">
                        <strong style="font-size: 1.5em;">${voucher.ma}</strong> <span style="font-size: 1em; color: #FF0000">-${voucher.giaTri}đ</span><br>
                        ${voucher.moTa}
                    </label>
                </div>
            `).join('');
        } else {
            voucherListElement.innerHTML = 'Không có khuyến mãi nào';
        }
    } catch (error) {
        console.error('Error fetching vouchers:', error);
        document.getElementById('voucher-list').innerHTML = 'Lỗi khi tải danh sách khuyến mãi';
    }
}

function selectVoucher() {
    const selectedVoucher = document.querySelector('input[name="voucher"]:checked');

    if (selectedVoucher) {
        const voucherValue = parseFloat(selectedVoucher.getAttribute('data-value'));

        if (isNaN(voucherValue) || voucherValue === null) {
            toastr.error('Voucher không hợp lệ.', 'Lỗi');
            return;
        }

        selectedVoucherId = selectedVoucher.value;
        selectedVoucherValue = voucherValue; // Lưu giá trị voucher dưới dạng số

        const selectedVoucherLabel = selectedVoucher.nextElementSibling;
        const voucherInfo = document.querySelector('.voucher');
        const newVoucherInfo = `
            <h2>${selectedVoucherLabel.querySelector('strong').textContent}</h2>
            <p>${selectedVoucherLabel.querySelector('span').textContent}</p>
        `;

        voucherInfo.innerHTML = newVoucherInfo;
        closeVoucherModal();
        updateTotalPayment();
        toastr.success('Voucher đã được chọn thành công!', 'Thành công');
    } else {
        toastr.error('Vui lòng chọn một voucher.', 'Lỗi');
    }
}


function updateTotalPayment() {
    const totalItemPriceText = document.getElementById('totalItemPrice').textContent;
    console.log('totalItemPriceText', totalItemPriceText);  // In ra: "2.000.000 đ"

// Loại bỏ dấu chấm phân cách hàng nghìn và các ký tự không phải số (bao gồm " đ")
    const totalItemPrice = parseFloat(totalItemPriceText.replace(/[^\d.-]/g, '').replace(/\./g, ''));
    console.log('totalItemPrice', totalItemPrice);  // In ra: 2000000

    const shippingFee = 30000; // Default shipping fee
    const totalPayment = totalItemPrice + shippingFee - selectedVoucherValue; // Subtract the voucher value and add shipping fee
    console.log('totalPayment', totalPayment);
    document.getElementById('totalPayment').textContent = `${totalPayment.toLocaleString()} đ`;
    document.querySelector('.total-price').textContent = `Tổng Cộng: ${(totalItemPrice - selectedVoucherValue).toLocaleString()} ₫`; // Update the total price without shipping fee
    document.getElementById('voucherDiscount').textContent = `${selectedVoucherValue.toLocaleString()} đ`;
}

function openVoucherModal() {
    document.getElementById('voucher-modal').style.display = 'block';
    document.body.style.overflow = "hidden";
    fetchVouchers();
}

function closeVoucherModal() {
    document.getElementById('voucher-modal').style.display = 'none';
    document.body.style.overflow = "";
}

// // Add event listener to the "Chọn voucher khác" button
// document.querySelector('.voucher-text button').addEventListener('click', openVoucherModal);




