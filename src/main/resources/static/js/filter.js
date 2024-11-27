$(document).ready(function () {
    const form = $('#searchForm');
    const input = $('#searchInput');
    const resultsContainer = $('#searchResults');
    const categoryTitle = $('#categoryTitle');
    const paginationContainer = $('#paginationContainer');

    let currentPage = 1; // Trang hiện tại
    let totalPages = 1; // Tổng số trang
    let allProducts = []; // Lưu trữ toàn bộ sản phẩm

    // Hàm hiển thị thông báo
    function displayMessage(message, type = 'text-danger') {
        resultsContainer.html(`<p class="${type}">${message}</p>`);
    }

    // Hàm tạo HTML cho sản phẩm
    function createProductHtml(product) {
        return `
            <div class="col-md-3 mb-3 product" data-product-id="${product.id}">
                <div class="card product-card">
                    <a href="/productDetail/${product.id}">
                        <img style="width: 270px; height: 270px;" src="/images/${product.anh || 'default.png'}" alt="${product.ten}">
                    </a>
                    <div class="card-body">
                        <div class="text-start">
                            <h6 class="card-title fw-bold">${product.ten}</h6>
                            <p class="price">${product.gia.toLocaleString()}đ</p>
                        </div>
                        <div class="d-flex justify-content-between align-items-center">
                            <button class="btn btn-buy btn-dark w-75">Mua ngay</button>
                            <span style="cursor: pointer" class="icon-heart">
                                <i style="font-size: 24px" class="bi bi-heart"></i>
                            </span>
                        </div>
                    </div>
                </div>
            </div>`;
    }

    // Cập nhật tiêu đề danh mục
    function updateCategoryTitle(keyword, count) {
        categoryTitle.text(`${keyword || 'Tất cả sản phẩm'} (${count} sản phẩm)`);
    }

    // Hàm gọi API tìm kiếm
    async function fetchSearchResults(keyword, page = 1) {
        if (!keyword.trim()) {
            displayMessage('Vui lòng nhập từ khóa tìm kiếm.');
            updateCategoryTitle('Không có sản phẩm', 0);
            return;
        }

        try {
            // Gọi API với tham số tìm kiếm
            const response = await fetch(`/san-pham/search?ten=${encodeURIComponent(keyword)}`);
            if (!response.ok) throw new Error('Lỗi khi gọi API.');

            const data = await response.json();
            allProducts = data; // Lưu trữ tất cả sản phẩm
            totalPages = Math.ceil(allProducts.length / 8); // 8 sản phẩm mỗi trang

            if (allProducts.length === 0) {
                displayMessage('Không tìm thấy sản phẩm nào!');
                updateCategoryTitle(keyword, 0);
            } else {
                showPage(page); // Hiển thị trang hiện tại
                updateCategoryTitle(keyword, allProducts.length);
                attachWishlistEvent(); // Gắn sự kiện cho trái tim
                updatePagination(page); // Cập nhật phân trang
            }
        } catch (error) {
            console.error(error);
            displayMessage('Đã xảy ra lỗi khi tải dữ liệu.');
            updateCategoryTitle('Không có sản phẩm', 0);
        }
    }

    // Hàm hiển thị sản phẩm theo trang
    function showPage(page) {
        currentPage = page;
        const startIndex = (currentPage - 1) * 8; // 8 sản phẩm mỗi trang
        const endIndex = startIndex + 8;
        const productsToDisplay = allProducts.slice(startIndex, endIndex);

        resultsContainer.empty();
        productsToDisplay.forEach(product => {
            resultsContainer.append(createProductHtml(product));
        });
    }

    // Cập nhật phân trang
    function updatePagination(currentPage) {
        paginationContainer.empty();

        // Nút Previous
        paginationContainer.append(`
            <a href="#" class="prev ${currentPage === 1 ? 'disabled' : ''}">«</a>
        `);

        // Các trang
        for (let i = 1; i <= totalPages; i++) {
            paginationContainer.append(`
                <a href="#" class="page-num ${i === currentPage ? 'active' : ''}">${i}</a>
            `);
        }

        // Nút Next
        paginationContainer.append(`
            <a href="#" class="next ${currentPage === totalPages ? 'disabled' : ''}">»</a>
        `);

        // Sự kiện cho các nút trang
        $('.page-num').off('click').on('click', function () {
            const pageNum = parseInt($(this).text());
            const keyword = input.val().trim();
            fetchSearchResults(keyword, pageNum);
        });

        // Sự kiện cho nút Previous
        $('.prev').off('click').on('click', function () {
            if (currentPage > 1) {
                fetchSearchResults(input.val().trim(), currentPage - 1);
            }
        });

        // Sự kiện cho nút Next
        $('.next').off('click').on('click', function () {
            if (currentPage < totalPages) {
                fetchSearchResults(input.val().trim(), currentPage + 1);
            }
        });
    }

    // Hàm gắn sự kiện cho biểu tượng trái tim
    function attachWishlistEvent() {
        $('.icon-heart i').off('click').on('click', function () {
            const productId = $(this).closest('.product').data('product-id');
            const authToken = localStorage.getItem('token');

            if (!authToken) {
                alert('Bạn cần đăng nhập để thêm sản phẩm vào danh sách yêu thích.');
                return;
            }

            const heartIcon = $(this);

            $.ajax({
                url: `/yeu-thich/${productId}/check`,
                method: 'GET',
                headers: {
                    "Authorization": `Bearer ${authToken}`
                },
                success: function (isInWishlist) {
                    if (isInWishlist) {
                        alert('Sản phẩm đã có trong danh sách yêu thích.');
                        heartIcon.removeClass('bi-heart').addClass('bi-heart-fill');
                    } else {
                        $.ajax({
                            url: `/yeu-thich/${productId}`,
                            method: 'POST',
                            headers: {
                                "Authorization": `Bearer ${authToken}`
                            },
                            success: function () {
                                alert('Đã thêm sản phẩm vào danh sách yêu thích!');
                                heartIcon.removeClass('bi-heart').addClass('bi-heart-fill');
                            },
                            error: function () {
                                alert('Có lỗi xảy ra khi thêm vào danh sách yêu thích.');
                            }
                        });
                    }
                },
                error: function () {
                    alert('Có lỗi xảy ra khi kiểm tra danh sách yêu thích.');
                }
            });
        });
    }

    // Xử lý sự kiện gửi form
    form.on('submit', function (e) {
        e.preventDefault();
        const keyword = input.val().trim();
        fetchSearchResults(keyword);
    });

    // Kiểm tra từ khóa trong URL khi tải trang
    const params = new URLSearchParams(window.location.search);
    const keyword = params.get('ten');
    const page = params.get('page') || 1;

    if (keyword) {
        input.val(keyword); // Hiển thị từ khóa trên input
        fetchSearchResults(keyword, page); // Gọi tìm kiếm với phân trang
    } else {
        displayMessage('Không có từ khóa tìm kiếm.');
    }
});
