app.controller("hoadon-ctrl", function ($scope, $http, $location) {
    $scope.hoaDons = [];
    $scope.trangThai = 0; // Giá trị mặc định, cập nhật từ URL nếu có
    $scope.form = {};
    $scope.hoaDonChiTiets = []; // Mảng chứa danh sách hóa đơn chi tiết

    $scope.pager = {
        page: 0,
        size: 10, // Giá trị mặc định
        hoaDons: [],
        count: 0,
        first: function () {
            this.page = 0;
            this.updateItems();
        },
        prev: function () {
            if (this.page > 0) {
                this.page--;
                this.updateItems();
            }
        },
        next: function () {
            if (this.page < this.count - 1) {
                this.page++;
                this.updateItems();
            }
        },
        last: function () {
            this.page = this.count - 1;
            this.updateItems();
        },
        updateItems: function () {
            const filteredItems = $scope.items.filter(item => {
                const matchesSearch = item.id.toString().toLowerCase().includes($scope.searchText.toLowerCase()) ||
                    item.ten.toLowerCase().includes($scope.searchText.toLowerCase());
                const matchesIdCha = !$scope.selectedIdCha || item.idCha === Number($scope.selectedIdCha);
                return matchesSearch && matchesIdCha;
            });

            // Sắp xếp theo ngày cập nhật mới nhất
            filteredItems.sort((a, b) => {
                const dateA = new Date(a.ngayCapNhat); // Giả sử `ngayCapNhat` là trường ngày
                const dateB = new Date(b.ngayCapNhat);
                return dateB - dateA; // Sắp xếp theo thứ tự giảm dần
            });

            this.count = Math.ceil(filteredItems.length / this.size);
            this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
        }
    };


    $scope.edit0 = function (item) {
        // Chuyển timestamp thành Date object
        item.ngayCapNhat = new Date(item.ngayCapNhat);
        item.ngayTao = new Date(item.ngayTao);
        $scope.form = angular.copy(item);
    };

    $scope.getHoaDonChiTietById = function (idHoaDon) {
        $http.get('/hoa-don-chi-tiet/get-id-hoa-don', {params: {idHoaDon: idHoaDon}})
            .then(response => {
                $scope.hoaDonChiTiets = response.data.data; // Gán danh sách hóa đơn chi tiết
            })
            .catch(error => {
                console.error("Lỗi khi lấy danh sách hóa đơn chi tiết:", error);
            });
    };

    $scope.getTotalQuantity = function () {
        return $scope.hoaDonChiTiets.reduce(function (total, detail) {
            return total + detail.soLuong; // Cộng dồn số lượng
        }, 0);
    };


    $scope.getTitleByTrangThai = function (trangThai) {
        switch (trangThai) {
            case 0:
                return "chờ xác nhận";
            case 2:
                return "chờ giao hàng";
            case 3:
                return "đang vận chuyển";
            case 4:
                return "đã giao";
            case 5:
                return "đã hủy";
            default:
                return "không xác định";
        }
    };

    $scope.loadHoaDonsFromUrl = function () {
        const queryParams = $location.search();
        $scope.trangThai = parseInt(queryParams.trangThai) || 0; // Trạng thái mặc định là 0
        $scope.getHoaDonsByTrangThai($scope.trangThai);
    };

    $scope.getHoaDonsByTrangThai = function (trangThai) {
        $http.get('/rest/hoadon/get-by-status', {params: {trangThai: trangThai}})
            .then(response => {
                $scope.hoaDons = response.data.data;
                $scope.pager.updateItems();
            })
            .catch(error => {
                console.error("Lỗi khi lấy danh sách hóa đơn:", error);
            });
    };

    $scope.updateHoaDonStatus = function (hoaDonId) {
        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn cập nhật trạng thái đơn hàng này?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                // Thực hiện cập nhật trạng thái hóa đơn nếu người dùng xác nhận
                $http.put('/rest/hoadon/update-status/' + hoaDonId, {}, {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    }
                })
                    .then(response => {
                        toastr.success("Trạng thái đơn hàng đã được cập nhật", "Thành công!");
                        $scope.getHoaDonsByTrangThai($scope.trangThai); // Refresh danh sách
                    })
                    .catch(error => {
                        console.error("Lỗi khi cập nhật trạng thái:", error);
                        toastr.error("Không cập nhật được trạng thái đơn hàng", "Lỗi!");
                    });
            } else {
                toastr.info("Hành động đã hủy", "Hủy!");
            }
        });
    };


    $scope.selectedLyDoHuy = '';  // Lý do hủy được chọn
    $scope.hoaDonId = null;  // ID hóa đơn cần cập nhật

    // Mở modal và lưu ID hóa đơn
    $scope.openModal = function (hoaDonId) {
        $scope.hoaDonId = hoaDonId; // Lưu ID hóa đơn
    };

    $scope.updateCancelOrder = function () {
        if (!$scope.selectedLyDoHuy) {
            toastr.error("Bạn chưa nhập lý do hủy", "Lỗi!");
            return;
        }

        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn hủy đơn hàng này?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                var cancelOrderRequest = {
                    orderInfo: $scope.selectedLyDoHuy
                };

                // Lấy token từ localStorage
                var token = localStorage.getItem('token');  // Thay 'token' bằng tên bạn đã lưu trong localStorage

                // Gửi yêu cầu hủy đơn hàng
                $http.post('/rest/hoadon/cancel/' + $scope.hoaDonId, cancelOrderRequest, {
                    headers: {
                        'Authorization': 'Bearer ' + token  // Thêm token vào header Authorization
                    }
                })
                    .then(function (response) {
                        toastr.success("Hủy đơn hàng thành công", "Thành công!");
                        // Đóng modal sau khi hủy thành công
                        $('#lyDoHuy').modal('hide');
                        // Refresh danh sách hóa đơn nếu cần
                        $scope.getHoaDonsByTrangThai($scope.trangThai);
                    })
                    .catch(function (error) {
                        toastr.error("Có lỗi không thể hủy đơn hàng", "Lỗi!");
                        console.error(error);
                    });
            } else {
                toastr.info("Hành động đã hủy", "Hủy!");
            }
        });
    };


    // Gọi khi trang được tải
    $scope.loadHoaDonsFromUrl();


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
