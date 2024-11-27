// headerJs.js
document.addEventListener('click', function(event) {

    const logoImage = document.getElementById('logoImage'); // Lấy phần tử img có id là logoImage

    // Kiểm tra nếu người dùng nhấn vào ảnh logo
    if (logoImage && logoImage.contains(event.target)) {
        // Chuyển hướng đến trang chủ
        window.location.href = '/'; // Chuyển hướng đến trang chủ
    }
    const userIcon = document.getElementById('userIcon');
    const dropdownMenu = document.querySelector('.user-dropdown .dropdown-menu');
    const cartIcon = document.querySelector('.bi-bag'); // Lấy phần tử biểu tượng giỏ hàng

    // Kiểm tra nếu người dùng nhấn vào biểu tượng người dùng
    if (userIcon.contains(event.target)) {
        // Tạo hiệu ứng bật/tắt menu khi người dùng nhấn vào biểu tượng người dùng
        dropdownMenu.classList.toggle('show');

        // Lấy token từ localStorage (kiểm tra xem người dùng đã đăng nhập chưa)
        const token = localStorage.getItem('token');

        // Lấy tất cả các mục trong menu dropdown
        const dropdownItems = dropdownMenu.querySelectorAll('.dropdown-item');

        // Nếu có token, nghĩa là người dùng đã đăng nhập
        if (token) {
            // Cập nhật các mục trong dropdown khi người dùng đã đăng nhập
            dropdownItems[0].textContent = 'Hồ sơ'; // Mục đầu tiên: Hồ sơ
            dropdownItems[1].textContent = 'Đổi mật khẩu'; // Mục thứ hai: Đổi mật khẩu
            dropdownItems[2].textContent = 'Đăng xuất'; // Mục thứ ba: Đăng xuất

            // Đảm bảo các mục khác (Đăng nhập và Đăng ký) không hiển thị
            dropdownItems[0].style.display = 'block'; // Hiển thị "Hồ sơ"
            dropdownItems[1].style.display = 'block'; // Hiển thị "Đổi mật khẩu"
            dropdownItems[2].style.display = 'block'; // Hiển thị "Đăng xuất"

            // Khi người dùng nhấn vào "Hồ sơ", chuyển hướng đến trang "Hồ sơ"
            dropdownItems[0].addEventListener('click', function() {
                window.location.href = '/profile'; // Chuyển hướng đến trang /profile
            });

            // Khi người dùng nhấn vào "Đổi mật khẩu", chuyển hướng đến trang thay đổi mật khẩu
            dropdownItems[1].addEventListener('click', function() {
                window.location.href = '/v1/auth/change-password'; // Chuyển hướng đến trang /v1/auth/change-password
            });

            // Khi người dùng nhấn vào "Đăng xuất", xóa token và tải lại trang
            dropdownItems[2].addEventListener('click', function() {
                localStorage.removeItem('token'); // Xóa token khỏi localStorage
                window.location.href = '/';
                // window.location.reload(); // Tải lại trang hiện tại
            });

        } else {
            // Nếu không có token, nghĩa là người dùng chưa đăng nhập
            // Cập nhật các mục trong dropdown khi người dùng chưa đăng nhập
            dropdownItems[0].textContent = 'Đăng nhập'; // Mục đầu tiên: Đăng nhập
            dropdownItems[1].textContent = 'Đăng ký'; // Mục thứ hai: Đăng ký

            // Ẩn mục "Đăng xuất" khi người dùng chưa đăng nhập
            dropdownItems[2].style.display = 'none'; // Ẩn mục "Đăng xuất"

            // Khi người dùng nhấn vào "Đăng nhập", chuyển hướng đến trang đăng nhập
            dropdownItems[0].addEventListener('click', function() {
                window.location.href = '/v1/auth/login'; // Chuyển hướng đến trang /v1/auth/login
            });

            // Khi người dùng nhấn vào "Đăng ký", chuyển hướng đến trang đăng ký
            dropdownItems[1].addEventListener('click', function() {
                window.location.href = '/v1/auth/register'; // Chuyển hướng đến trang /v1/auth/register
            });
        }
    } else if (!dropdownMenu.contains(event.target)) {
        // Nếu người dùng nhấn ra ngoài menu dropdown, đóng menu
        dropdownMenu.classList.remove('show');
    }

    // Kiểm tra nếu người dùng nhấn vào biểu tượng giỏ hàng (biểu tượng .bi-bag)
    if (cartIcon.contains(event.target)) {
        // Chuyển hướng đến trang giỏ hàng
        window.location.href = '/cart'; // Chuyển hướng đến trang /cart
    }
});

document.addEventListener('DOMContentLoaded', function () {
    // Lấy token từ localStorage
    const token = localStorage.getItem('token');

    // Kiểm tra nếu có token thì fetch API, nếu không có thì hiển thị số lượng giỏ hàng là 0
    if (token) {
        fetchCartItemCount(token); // Gọi hàm lấy số lượng giỏ hàng nếu có token
    } else {
        updateCartBadge(0); // Nếu không có token, hiển thị 0
    }
});

// Hàm lấy số lượng sản phẩm trong giỏ hàng và cập nhật badge
function fetchCartItemCount(token) {
    fetch('/gio-hang-chi-tiet/get-list', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}` // Thêm token vào header Authorization
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data && data.data) {
                const cartItemCount = data.data.length;
                updateCartBadge(cartItemCount); // Cập nhật số lượng giỏ hàng
            } else {
                console.error('Không có dữ liệu giỏ hàng');
                updateCartBadge(0); // Nếu không có dữ liệu giỏ hàng, hiển thị 0
            }
        })
        .catch(error => {
            console.error('Lỗi khi gọi API:', error);
            updateCartBadge(0); // Hiển thị 0 nếu có lỗi trong việc gọi API
        });
}

// Hàm cập nhật số lượng giỏ hàng trong badge
function updateCartBadge(count) {
    const cartBadge = document.querySelector('.cart-badge');
    if (cartBadge) {
        cartBadge.textContent = count; // Cập nhật số lượng giỏ hàng
    }
}
//