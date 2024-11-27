$(document).ready(function () {
    // Lấy token từ localStorage
    const token = localStorage.getItem('token');

    if (token) {
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // Truyền token trong header
        };

        // Gọi API lấy danh sách sản phẩm yêu thích
        $.ajax({
            url: '/yeu-thich', // Đường dẫn API
            method: 'GET',
            headers: headers, // Truyền header với token
            success: function (products) {
                const container = $('#wishlist-container');
                container.empty();

                // Kiểm tra nếu response là một mảng
                if (Array.isArray(products)) {
                    const pageSize = 10; // Số sản phẩm mỗi trang
                    const totalPages = Math.ceil(products.length / pageSize); // Tổng số trang
                    let currentPage = 1; // Trang hiện tại

                    // Hàm để hiển thị các sản phẩm cho trang hiện tại
                    function displayProductsForPage(page) {
                        container.empty(); // Xóa tất cả sản phẩm hiện tại
                        const startIndex = (page - 1) * pageSize; // Tính chỉ số bắt đầu
                        const endIndex = Math.min(startIndex + pageSize, products.length); // Tính chỉ số kết thúc

                        // Lặp qua các sản phẩm cần hiển thị cho trang này
                        for (let i = startIndex; i < endIndex; i++) {
                            const product = products[i];

                            // Kiểm tra và định dạng giá
                            const price = product.gia ? product.gia : 0;
                            const formattedPrice = price.toLocaleString();

                            const productHTML = `
                                <div class="col-2 product" data-product-id="${product.id}">
                                    <a href="/productDetail/${product.id}">
                                        <img src="/images/${product.anh}" alt="${product.ten}" />
                                    </a>
                                    <div class="delete-wishlist-item">
                                        <div class="delete-icon" data-bs-toggle="tooltip" data-bs-placement="top" title="Huỷ yêu thích">
                                            <i class="bi bi-x-lg"></i>
                                        </div>
                                    </div>
                                    <h3>${product.ten}</h3>
                                    <div>${formattedPrice}đ</div>
                                </div>
                            `;
                            container.append(productHTML);
                        }

                        // Gắn sự kiện click cho nút xóa trong trang này
                        $(".delete-wishlist-item").click(function (e) {
                            e.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

                            // Lấy ID sản phẩm
                            const productId = $(this).closest(".product").data("product-id");

                            // Gọi API xóa sản phẩm
                            $.ajax({
                                url: `/yeu-thich/${productId}`, // Đường dẫn API xóa sản phẩm theo ID
                                method: 'DELETE',
                                headers: headers, // Truyền header với token
                                success: function () {
                                    // Xóa sản phẩm khỏi giao diện
                                    $(`.product[data-product-id="${productId}"]`).fadeOut(300, function () {
                                        $(this).remove();
                                    });
                                    console.log("Sản phẩm đã được xóa khỏi danh sách yêu thích.");
                                },
                                error: function (error) {
                                    console.error("Lỗi khi xóa sản phẩm khỏi danh sách yêu thích:", error);
                                }
                            });
                        });
                    }

                    // Hiển thị các sản phẩm cho trang đầu tiên
                    displayProductsForPage(currentPage);

                    // Thêm sự kiện click cho các nút phân trang
                    function updatePagination() {
                        const paginationContainer = $(".pagination");
                        paginationContainer.empty();

                        // Thêm nút prev
                        if (currentPage > 1) {
                            paginationContainer.append(`<a href="#" class="prev" data-page="${currentPage - 1}">«</a>`);
                        }

                        // Thêm các nút trang
                        for (let i = 1; i <= totalPages; i++) {
                            paginationContainer.append(`<a href="#" class="page-num ${i === currentPage ? 'active' : ''}" data-page="${i}">${i}</a>`);
                        }

                        // Thêm nút next
                        if (currentPage < totalPages) {
                            paginationContainer.append(`<a href="#" class="next" data-page="${currentPage + 1}">»</a>`);
                        }

                        // Hiển thị phân trang nếu có hơn 1 trang
                        if (totalPages <= 1) {
                            paginationContainer.hide();
                        } else {
                            paginationContainer.show();
                        }
                    }

                    // Gắn sự kiện click cho các nút phân trang
                    $(".pagination").on("click", ".page-num", function (e) {
                        e.preventDefault();
                        currentPage = $(this).data('page');
                        displayProductsForPage(currentPage);
                        updatePagination();
                    });

                    $(".pagination").on("click", ".prev", function (e) {
                        e.preventDefault();
                        if (currentPage > 1) {
                            currentPage--;
                            displayProductsForPage(currentPage);
                            updatePagination();
                        }
                    });

                    $(".pagination").on("click", ".next", function (e) {
                        e.preventDefault();
                        if (currentPage < totalPages) {
                            currentPage++;
                            displayProductsForPage(currentPage);
                            updatePagination();
                        }
                    });

                    // Cập nhật phân trang ban đầu
                    updatePagination();
                } else {
                    console.log("Dữ liệu trả về không phải là một mảng:", products);
                }
            },
            error: function (error) {
                console.log("Lỗi khi lấy danh sách yêu thích:", error);
            }
        });
    } else {
        console.log("Không tìm thấy token trong localStorage.");
    }
});




document.querySelectorAll('.page-num').forEach(page => {
    page.addEventListener('click', function(e) {
        e.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

        // Xóa lớp active từ tất cả các liên kết
        document.querySelectorAll('.page-num').forEach(link => {
            link.classList.remove('active');
        });

        // Thêm lớp active vào liên kết được nhấn
        this.classList.add('active');
    });
});
