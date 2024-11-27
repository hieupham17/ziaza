app.controller("danhsachspkm-ctrl", function ($scope, $http) {

    // Hàm tải danh sách sản phẩm
    $scope.loadProducts = function () {
        $http.get('/san-pham')  // Gọi API GET để lấy danh sách sản phẩm
            .then(function (response) {
                $scope.products = response.data.data;  // Cập nhật dữ liệu sản phẩm
                $scope.loadPromotions(); // Gọi hàm loadPromotions để lấy danh sách khuyến mãi
            }, function (error) {
                console.error("Có lỗi xảy ra khi gọi API sản phẩm: ", error);
            });
    };

    // Hàm tải danh sách khuyến mãi và áp dụng cho sản phẩm
    $scope.loadPromotions = function () {
        $http.get('/ap-dung-khuyen-mai')  // Gọi API GET để lấy danh sách khuyến mãi đã áp dụng
            .then(function (response) {
                var appliedPromotions = response.data.data;  // Dữ liệu khuyến mãi đã áp dụng

                $scope.products.forEach(function (product) {
                    var applicablePromotions = appliedPromotions.filter(function (promo) {
                        return promo.idSanPham === product.id;
                    });

                    var totalDiscount = 0; // Tổng giá trị khuyến mãi
                    var promotionNames = []; // Danh sách tên khuyến mãi

                    if (applicablePromotions.length > 0) {
                        applicablePromotions.forEach(function (promo) {
                            $http.get('/khuyen-mai/' + promo.idKhuyenMai)
                                .then(function (response) {
                                    var promotionDetail = response.data.data;

                                    var currentDate = new Date();
                                    var startDate = new Date(promotionDetail.ngayBatDau);
                                    var endDate = new Date(promotionDetail.ngayKetThuc);

                                    if (currentDate < startDate) {
                                        // Chưa tới ngày áp dụng khuyến mãi
                                        product.promotionName = 'Chưa tới ngày khuyến mãi';
                                        product.discountValue = 0;
                                        product.newPrice = product.gia;
                                    } else if (currentDate > endDate) {
                                        // Hết thời gian áp dụng khuyến mãi
                                        product.promotionName = 'Không có khuyến mãi';
                                        product.discountValue = 0;
                                        product.newPrice = product.gia;
                                    } else {
                                        // Khuyến mãi đang diễn ra
                                        totalDiscount += promotionDetail.giaTri;
                                        promotionNames.push(promotionDetail.ten); // Thêm tên khuyến mãi
                                    }

                                    product.discountValue = totalDiscount;
                                    product.newPrice = product.gia - totalDiscount;
                                    product.promotionName = promotionNames.length > 0
                                        ? promotionNames.join(', ')
                                        : product.promotionName;

                                }, function (error) {
                                    console.error("Có lỗi xảy ra khi gọi API chi tiết khuyến mãi: ", error);
                                });
                        });
                    } else {
                        product.promotionName = 'Không có khuyến mãi';
                        product.discountValue = 0;
                        product.newPrice = product.gia;
                    }
                });

            }, function (error) {
                console.error("Có lỗi xảy ra khi gọi API áp dụng khuyến mãi: ", error);
            });
    };

    $scope.loadProducts();
});
