app.controller("quanlyspct-ctrl", function ($scope, $http, $rootScope, $location) {
    $scope.items = [];
    $scope.form = {};
    $scope.formAdd = {};
    $scope.item = {};
    $scope.selectedProduct = {}; // Đối tượng lưu thông tin sản phẩm đã chọn
    $scope.filters = {};
    $scope.error = {};
    $scope.size = [];
    $scope.mausac = [];
    $scope.sanpham = [];
    $scope.searchText = '';
    $scope.productDetails = []; // Khởi tạo mảng sản phẩm chi tiết
    $scope.filteredSizes = [];
    $scope.filteredColors = [];
    $scope.selectedProductId = null;
    $scope.selectedProductTen = null;
    $scope.selectedProductId = $rootScope.selectedProductId; // Lấy ID sản phẩm từ rootScope
    $scope.selectedProductTen = $rootScope.selectedProductTen; // Lấy ID sản phẩm từ rootScope
    $scope.pager = {
        page: 0, size: 10, items: [], count: 0, first: function () {
            this.page = 0;
            this.updateItems();
        }, prev: function () {
            if (this.page > 0) {
                this.page--;
                this.updateItems();
            }
        }, next: function () {
            if (this.page < this.count - 1) {
                this.page++;
                this.updateItems();
            }
        }, last: function () {
            this.page = this.count - 1;
            this.updateItems();
        }, updateItems: function () {
            const filteredItems = $scope.items.filter(item => {
                const statusMatches = item.idSanPham === $scope.selectedProductId;
                const matchesSearch = item.id.toString().toLowerCase().includes($scope.searchText.toLowerCase());
                return matchesSearch && statusMatches;
            });
            this.count = Math.ceil(filteredItems.length / this.size);
            this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
        }
    };


    $scope.initialize = function () {
        $http.get("/spct").then(resp => {
            $scope.items = resp.data.data.map(item => ({
                ...item, ngayTao: new Date(item.ngayTao), ngayCapNhat: new Date(item.ngayCapNhat)
            }));
            $scope.pager.updateItems();
        }).catch(error => console.error("Lỗi khi tải danh mục: ", error));

        $http.get("/size/get-list").then(resp => {
            $scope.size = resp.data.data.filter(item => item.trangThai === 1);
            $scope.filterSizesByIdCha();
            $scope.productDetails = []; // Khởi tạo mảng sản phẩm chi tiết

        });

        $http.get("/mau-sac/get-list").then(resp => {
            $scope.mausac = resp.data.data.filter(item => item.trangThai === 1);
            $scope.filterColorsByIdCha();
        });

        $http.get("/san-pham").then(resp => {
            $scope.sanpham = resp.data.data;
        });

    };


    $scope.edit = function (item) {
        item.ngayCapNhat = new Date(item.ngayCapNhat);
        $scope.form = angular.copy(item);
    };

    $scope.add = function () {
        // Kiểm tra xem đã chọn màu sắc và size hay chưa
        if ($scope.productDetails.length === 0) {
            toastr.info("Vui lòng thêm ít nhất một sản phẩm chi tiết!", "Lỗi!");
            return;
        }

        // Kiểm tra giá của từng sản phẩm chi tiết
        for (let i = 0; i < $scope.productDetails.length; i++) {
            const detail = $scope.productDetails[i];

            // Kiểm tra giá trị có hợp lệ hay không (>= 50,000 và <= 100,000,000)
            if (detail.gia < 0 || !detail.gia) {
                toastr.warning("Chưa nhập giá sản phẩm hoặc giá sản phẩm phải lớn hơn 0", "Lỗi!");
                return;
            }
            if (detail.soLuong < 0 || !detail.gia) {
                toastr.warning("Chưa nhập số lượng sản phẩm hoặc giá sản phẩm phải lớn hơn 0", "Lỗi!");
                return;
            }
        }

        // Hiển thị confirmation trước khi thực hiện thêm hoặc cập nhật
        swal({
            title: "Xác nhận",
            text: "Bạn có chắc chắn muốn thêm hoặc cập nhật sản phẩm chi tiết?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willAdd) => {
            if (willAdd) {
                // Tạo một danh sách sản phẩm chi tiết từ mảng productDetails
                const addDetailsPromises = $scope.productDetails.map(detail => {
                    // Kiểm tra xem sản phẩm chi tiết đã tồn tại trong danh sách hay chưa
                    const existingDetailIndex = $scope.items.findIndex(item =>
                        item.idSize === detail.idSize && item.idMauSac === detail.idMauSac && item.idSanPham === $scope.selectedProductId
                    );

                    const token = localStorage.getItem('token'); // Điều chỉnh key 'authToken' theo token bạn lưu trữ trong localStorage

                    if (existingDetailIndex !== -1) {
                        // Nếu đã tồn tại, cập nhật thông tin sản phẩm chi tiết
                        const existingDetail = $scope.items[existingDetailIndex];
                        existingDetail.soLuong = detail.soLuong;
                        existingDetail.gia = detail.gia;
                        existingDetail.ghiChu = detail.ghiChu;

                        // Gọi API để cập nhật sản phẩm chi tiết
                        return $http.put(`/spct/${existingDetail.id}`, existingDetail,
                            {
                                headers: {
                                    'Authorization': `Bearer ${token}` // Thêm token vào header cho yêu cầu chi tiết sản phẩm
                                }
                            }); // Sử dụng ID của sản phẩm chi tiết để cập nhật
                    } else {
                        // Nếu không tồn tại, tạo một sản phẩm chi tiết mới
                        return $http.post("/spct", {
                                idSanPham: $scope.selectedProductId,
                                idSize: detail.idSize,
                                idMauSac: detail.idMauSac,
                                soLuong: detail.soLuong,
                                gia: detail.gia,
                                ghiChu: detail.ghiChu,
                                trangThai: 1,
                            },
                            {
                                headers: {
                                    'Authorization': `Bearer ${token}` // Thêm token vào header cho yêu cầu chi tiết sản phẩm
                                }
                            });
                    }
                });

                // Chờ tất cả các yêu cầu hoàn thành
                Promise.all(addDetailsPromises).then(() => {
                    $scope.initialize();  // Cập nhật lại dữ liệu
                    $('#addModal').modal('hide');
                    toastr.success("hêm hoặc cập nhật sản phẩm chi tiết thành công!", "Thành công");
                }).catch(error => {
                    console.error("Lỗi khi thêm hoặc cập nhật chi tiết sản phẩm:", error);
                    toastr.error("Có lỗi xảy ra khi thêm hoặc cập nhật chi tiết sản phẩm!", "Lỗi");
                });
            } else {
                toastr.info("Hành động bị hủy bỏ!", "Thông báo");
            }
        });
    };

    $scope.filterSizesByIdCha = function () {
        if ($scope.selectedSizeIdCha) {
            $scope.filteredSizes = $scope.size.filter(size => size.idCha == $scope.selectedSizeIdCha);
        } else {
            $scope.filteredSizes = $scope.size;
        }
    };

    $scope.filterColorsByIdCha = function () {
        if ($scope.selectedColorIdCha) {
            $scope.filteredColors = $scope.mausac.filter(mausac => mausac.idCha == $scope.selectedColorIdCha);
        } else {
            $scope.filteredColors = $scope.mausac;
        }
    };

    $scope.filterColorsAndSizes = function () {
        $scope.productDetails = [];
        const selectedColors = $scope.filteredColors.filter(color => color.selected);
        const selectedSizes = $scope.filteredSizes.filter(size => size.selected);

        selectedColors.forEach(function (color) {
            selectedSizes.forEach(function (size) {
                $scope.productDetails.push({
                    idSize: size.id,
                    idMauSac: color.id,
                    soLuong: "",
                    gia: "",
                    ghiChu: '',
                    size: size,
                    mauSac: color
                });
            });
        });
    };

    $scope.removeProductDetail = function (detail) {
        // Xóa sản phẩm chi tiết khỏi danh sách
        const index = $scope.productDetails.indexOf(detail);
        if (index > -1) {
            $scope.productDetails.splice(index, 1); // Xóa sản phẩm chi tiết
        }
    };

    $scope.update = function () {
        $scope.error = {
            soLuong: false,
            gia: false,
            ghiChu: false
        };

        // Kiểm tra các trường dữ liệu
        let isValid = true;

        if (!$scope.form.gia || $scope.form.gia < 100000 || $scope.form.gia > 100000000) {
            $scope.error.gia = true;
            isValid = false;
            toastr.error("Giá sản phẩm phải từ 10.000 đến 100.000.000.", "Lỗi!");
        }

        if (!$scope.form.soLuong || $scope.form.soLuong < 100 || $scope.form.soLuong > 10000) {
            $scope.error.soLuong = true;
            isValid = false;
            toastr.error("Số lượng sản phẩm phải từ 1 đến 1.000 sản phẩm.", "Lỗi!");
        }

        if (!$scope.form.ghiChu || $scope.form.ghiChu.length < 5 || $scope.form.ghiChu.length > 300) {
            $scope.error.ghiChu = true;
            isValid = false;
            toastr.error("Ghi chú phải từ 5 - 10 ký tự.", "Lỗi!");
        }

        // Nếu dữ liệu không hợp lệ, hiển thị thông báo và không thực hiện thêm
        if (!isValid) {
            return; // Ngừng thực hiện nếu không hợp lệ
        }

        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn cập nhật trạng thái thành 2?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                var item = angular.copy($scope.form);
                // Lấy token từ localStorage
                const token = localStorage.getItem('token'); // Thay 'authToken' bằng khóa token thực tế của bạn

                $http.put(`/spct/${item.id}`, item, {
                    headers: {
                        'Authorization': `Bearer ${token}` // Thêm token vào header Authorization
                    }
                }).then(resp => {
                    $scope.initialize(); // Tải lại dữ liệu
                    toastr.success("Cập nhật thành công", "Thành công!");
                    $('#exampleModal').modal('hide');
                }).catch(error => {
                    toastr.error("Cập nhật thất bại", "Thành công!");
                    console.log("Error: ", error);
                });
            } else {
                toastr.info("Cập nhật sản phẩm đã bị hủy", "Hủy cập nhật!");
            }
        });
    };

    $scope.update1 = function (item) {
        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn cập nhật trạng thái thành 1?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                let updatedItem = angular.copy(item);
                updatedItem.trangThai = 1;
                var token = localStorage.getItem('token');
                $http.put(`/spct/${updatedItem.id}`, updatedItem, {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                }).then(resp => {
                    $scope.initialize();
                    toastr.success("Đã cập nhật trạng thái thành 1", "Thành công!");
                }).catch(error => {
                    toastr.error("Cập nhật trạng thái thất bại", "Lỗi!");
                    console.error("Error: ", error);
                });
            }
        });
    };

    $scope.update2 = function (item) {
        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn cập nhật trạng thái thành 2?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                let updatedItem = angular.copy(item);
                updatedItem.trangThai = 2;
                var token = localStorage.getItem('token');
                $http.put(`/spct/${updatedItem.id}`, updatedItem, {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                }).then(resp => {
                    $scope.initialize();
                    toastr.success("Đã cập nhật trạng thái thành 2", "Thành công!");
                }).catch(error => {
                    toastr.error("Cập nhật trạng thái thất bại", "Lỗi!");
                    console.error("Error: ", error);
                });
            }
        });
    };
    // Gọi hàm initialize khi controller được khởi tạo
    $scope.initialize();

    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": true,
        "progressBar": true,
        "positionClass": "toast-top-right", // Hiển thị ở góc trên bên phải
        "preventDuplicates": true,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000", // Thời gian thông báo tồn tại (ms)
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
});
