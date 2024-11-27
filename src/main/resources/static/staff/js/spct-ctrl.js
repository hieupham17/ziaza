app.controller("spct-ctrl", function ($scope, $http, $rootScope, $location) {
    $scope.items = [];
    $scope.form = {};
    $scope.filters = {};  // Đối tượng lưu giá trị bộ lọc riêng
    $scope.error = {};
    $scope.size = [];
    $scope.mausac = [];
    $scope.danhmuc = [];
    $scope.thuonghieu = [];
    $scope.chatlieu = [];
    $scope.searchText = ''; // Biến tìm kiếm
    $scope.selectedProductId = null;
    $scope.selectedImagesId = null;

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
                const statusMatches = item.trangThai === 1;
                const matchesSearch = item.ma.toString().toLowerCase().includes($scope.searchText.toLowerCase()) || item.ten.toLowerCase().includes($scope.searchText.toLowerCase());
                return matchesSearch && $scope.filterByAttributes(item) && statusMatches;
            });
            this.count = Math.ceil(filteredItems.length / this.size);
            this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
        }
    };
    $scope.pager2 = {
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
                const statusMatches = item.trangThai === 2;
                const matchesSearch = item.ma.toString().toLowerCase().includes($scope.searchText.toLowerCase()) || item.ten.toLowerCase().includes($scope.searchText.toLowerCase());
                return matchesSearch && $scope.filterByAttributes(item) && statusMatches;
            });
            this.count = Math.ceil(filteredItems.length / this.size);
            this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
        }
    };

    $scope.filterByAttributes = function (item) {
        return ((!$scope.filters.idChatLieu || item.idChatLieu === $scope.filters.idChatLieu) && (!$scope.filters.idDanhMuc || item.idDanhMuc === $scope.filters.idDanhMuc) && (!$scope.filters.idThuongHieu || item.idThuongHieu === $scope.filters.idThuongHieu) && (!$scope.filters.xuatXu || item.xuatXu === $scope.filters.xuatXu));
    };

    $scope.initialize = function () {
        $http.get("/san-pham").then(resp => {
            $scope.items = resp.data.data.map(item => ({
                ...item, ngayTao: new Date(item.ngayTao), ngayCapNhat: new Date(item.ngayCapNhat)
            }));
            $scope.pager.updateItems();
            $scope.pager2.updateItems();
        }).catch(error => console.error("Lỗi khi tải danh mục: ", error));

        $http.get("/rest/danhmuc").then(resp => {
            $scope.danhmuc = resp.data.data;
        });
        $http.get("/rest/thuonghieu").then(resp => {
            $scope.thuonghieu = resp.data.data;
        });
        $http.get("/chat-lieu/get-list").then(resp => {
            $scope.chatlieu = resp.data.data;
        });
        $http.get("/size/get-list").then(resp => {
            $scope.size = resp.data.data;
        });
        $http.get("/mau-sac/get-list").then(resp => {
            $scope.mausac = resp.data.data;
        });
    };

    $scope.$watchGroup(['filters.idChatLieu', 'filters.idDanhMuc', 'filters.idThuongHieu', 'filters.xuatXu', 'searchText'], function () {
        $scope.pager.updateItems();
    });

    $scope.updateStatus2 = function (item) {
        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn ẩn sản phẩm này không?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                const token = localStorage.getItem('token');
                $http({
                    method: 'PUT',
                    url: '/san-pham/' + item.id + '/status?status=2',
                    headers: {
                        'Authorization': `Bearer ${token}`, // Thêm token vào header Authorization
                    }
                })
                    .then(function (response) {
                        $scope.initialize();
                        toastr.success("Đã ẩn sản phẩm", "Thành công!");
                    }).catch(function (error) {
                    console.error("Cập nhật thất bại", error);
                    toastr.error("Ẩn sản phẩm thất bại", "Lỗi!");
                });
            } else {
                toastr.info("Ẩn sản phẩm đã bị hủy", "Hủy ẩn!");
            }
        });
    };

    $scope.updateStatus1 = function (item) {
        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn hiện lại sản phẩm này không?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                const token = localStorage.getItem('token');
                $http({
                    method: 'PUT',
                    url: '/san-pham/' + item.id + '/status?status=1',
                    headers: {
                        'Authorization': `Bearer ${token}`, // Thêm token vào header Authorization
                    }
                })
                    .then(function (response) {
                        $scope.initialize();
                        toastr.success("Hiện sản phẩm thành công", "Thành công!");
                    }).catch(function (error) {
                    console.error("Cập nhật thất bại", error);
                    toastr.error("Hiện sản phẩm thất bại", "Lỗi!");
                });
            } else {
                toastr.info("Hiện sản phẩm đã bị hủy", "Hủy ẩn!");
            }
        });
    };


    $scope.edit = function (item) {
        $scope.form = angular.copy(item); // sao chép dữ liệu sản phẩm
        $scope.form.anh.preview = "/images/" + item.anh;  // Đảm bảo đường dẫn ảnh đúng

        const input2 = document.getElementById('anh');
        if (input2) {
            input2.value = null;
        }
    };

    $scope.onFileChange = function (event) {
        const file = event.target.files[0];
        if (file) {
            if (file.size > 5 * 1024 * 1024) {
                $scope.errorMessage = "Kích thước file phải nhỏ hơn 5MB.";
                return;
            }
            if (!file.type.startsWith("image/")) {
                $scope.errorMessage = "Vui lòng chọn một file ảnh.";
                return;
            }

            // Lưu ảnh vào form và hiển thị ảnh đã chọn
            $scope.form.anh = file;
            const reader = new FileReader();
            reader.onload = function (e) {
                $scope.$apply(() => {
                    $scope.form.anh.preview = e.target.result;
                    $scope.errorMessage = ""; // Xóa lỗi nếu file hợp lệ
                });
            };
            reader.readAsDataURL(file); // Đọc file ảnh và lưu vào form
        } else {
            $scope.errorMessage = "Không có ảnh được chọn.";
        }
    };

    $scope.updateProduct = function () {
        if (!$scope.validateFields()) {
            return; // Stop if validation fails
        }

        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn cập nhật sản phẩm này không?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                const now = new Date().toISOString().split('.')[0];

                const formData = new FormData();

                // Kiểm tra nếu có ảnh mới từ input file
                const fileInput = document.getElementById('anh');
                if (fileInput.files.length > 0) {
                    // Nếu có ảnh mới, gửi file ảnh lên server
                    formData.append("file", fileInput.files[0]);
                } else if ($scope.form.anh) {
                    // Nếu không có ảnh mới, gửi lại ảnh cũ
                    formData.append("anh", $scope.form.anh.replace("/images/", ""));
                }

                formData.append("ten", $scope.form.ten);
                formData.append("xuatXu", $scope.form.xuatXu);
                formData.append("moTa", $scope.form.moTa);
                formData.append("gia", $scope.form.gia);
                formData.append("idDanhMuc", $scope.form.idDanhMuc);
                formData.append("idThuongHieu", $scope.form.idThuongHieu);
                formData.append("idChatLieu", $scope.form.idChatLieu);
                formData.append("trangThai", 1);
                // formData.append("ngayCapNhat", now);
                // formData.append("nguoiCapNhat", 1);

                formData.append("ma", $scope.form.ma);
                // formData.append("ngayTao", now);
                // formData.append("nguoiTao", $scope.form.nguoiTao);
                const token = localStorage.getItem('token');

                $http.put(`/san-pham/${$scope.form.id}`, formData, {
                    headers: {
                        'Content-Type': undefined,
                        'Authorization': `Bearer ${token}` // Thêm token vào header Authorization
                    }
                }).then(function (response) {
                    $scope.initialize();
                    toastr.success("Update sản phẩm thành công", "Thành công!");
                    $('#exampleModal').modal('hide');
                }).catch(function (error) {
                    console.error("Cập nhật thất bại", error);
                    toastr.error("Update sản phẩm thất bại", "Lỗi!");
                });
            } else {
                toastr.info("Update sản phẩm đã bị hủy", "Hủy cập nhật!");
            }
        });
    };


    $scope.validateFields = function () {
        let isValid = true;
        $scope.errorMessages = {};

        if (!$scope.form.ten || $scope.form.ten.length < 5 || $scope.form.ten.length > 300) {
            $scope.errorMessages.ten = "Tên sản phẩm phải có từ 5 đến 300 ký tự.";
            isValid = false;
        }

        if (!$scope.form.moTa || $scope.form.moTa.length < 5 || $scope.form.moTa.length > 300) {
            $scope.errorMessages.moTa = "Mô tả phải có từ 5 đến 300 ký tự.";
            isValid = false;
        }

        if (!$scope.form.gia || $scope.form.gia < 100000 || $scope.form.gia > 100000000) {
            $scope.errorMessages.gia = "Giá phải lớn hơn 100,000 và nhỏ hơn 100,000,000.";
            isValid = false;
        }

        return isValid;
    };

    $scope.selectProduct = function (item) {
        console.log("Selected Product ID: ", item.id); // Log để kiểm tra
        $rootScope.selectedProductId = item.id; // Lưu ID sản phẩm vào rootScope để sử dụng ở trang khác
        $rootScope.selectedProductTen = item.ten; // Lưu ID sản phẩm vào rootScope để sử dụng ở trang khác
        $location.path('/spct'); // Chuyển hướng đến trang sản phẩm chi tiết
    };

    $scope.selectImages = function (item) {
        console.log("Selected Images ID: ", item.id); // Log để kiểm tra
        $rootScope.selectedImagesId = item.id; // Lưu ID sản phẩm vào rootScope để sử dụng ở trang khác
        $rootScope.selectedImagesTen = item.ten; // Lưu ID sản phẩm vào rootScope để sử dụng ở trang khác
        $location.path('/hinhanh'); // Chuyển hướng đến trang sản phẩm chi tiết
    };

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
