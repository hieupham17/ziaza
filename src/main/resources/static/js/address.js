let fetchedAddresses = []; // Biến toàn cục để lưu địa chỉ đã fetch

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
                <div>
                    <input type="radio" name="address" value="${address.id}" id="address-${address.id}">
                    <label for="address-${address.id}">
                        <strong style="font-size: 1.5em;">${address.hoTen}</strong> <span style="font-size: 1em;">(${address.sdt})</span><br>
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
            addressElement.textContent = `
                ${selectedAddress.hoTen}, ${selectedAddress.diaChi}, ${selectedAddress.thanhPho}, ${selectedAddress.quocGia}
            `;
        }

        closeModal(); // Đóng modal sau khi chọn địa chỉ
    } else {
        alert('Vui lòng chọn một địa chỉ.');
    }
}

function showModal() {
    document.getElementById('address-modal').style.display = 'block';
    document.body.style.overflow = "hidden"; //
    fetchAddressList(); // Gọi hàm lấy danh sách địa chỉ khi hiển thị modal
}

function closeModal() {
    document.getElementById('address-modal').style.display = 'none';
    document.body.style.overflow = "";
}

// Gọi hàm khi trang được tải
document.addEventListener('DOMContentLoaded', fetchDeliveryAddress);

// Đóng modal khi nhấp bên ngoài
window.onclick = function(event) {
    const modal = document.getElementById("address-modal");
    if (event.target === modal) {
        closeModal();
    }
};