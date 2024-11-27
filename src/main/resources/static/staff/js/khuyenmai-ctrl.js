app.controller("khuyenmai-ctrl", function ($scope, $http) {
    $scope.items = [];
    $scope.form = {};
    $scope.formAdd = {};
    $scope.selectedLoai = null;
    $scope.searchText = ''; // Biến tìm kiếm
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
            // Lọc các mục theo tìm kiếm và loại
            const filteredItems = $scope.items.filter(item => {
                const matchesSearch = item.id.toString().toLowerCase().includes($scope.searchText.toLowerCase()) || item.ten.toLowerCase().includes($scope.searchText.toLowerCase());
                const matchesLoai = !$scope.selectedLoai || item.loai === Number($scope.selectedLoai);
                return matchesSearch && matchesLoai;
            });

            // Cập nhật số trang
            this.count = Math.ceil(filteredItems.length / this.size);
            // Cập nhật danh sách các mục cho trang hiện tại
            this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
        }
    };
    // Hàm để gọi API lấy danh sách khuyến mãi
    $scope.getKhuyenMaiList = function () {
        var requestData = {
            keyword: $scope.searchText, loai: $scope.selectedLoai
        };

        $http.post('/khuyen-mai/get-list', requestData)
            .then(function (response) {
                // Xử lý dữ liệu trả về từ API
                if (response.data && response.data.data) {
                    $scope.items = response.data.data; // Gán dữ liệu vào mảng items
                    $scope.pager.updateItems(); // Cập nhật danh sách khuyến mãi cho pager
                } else {
                    console.error("Dữ liệu không hợp lệ");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi gọi API: ", error);
                toastr.error("Có lỗi xảy ra khi tải dữ liệu khuyến mãi.", "Lỗi!");
            });
    };


    // Hàm gọi khi thay đổi từ khóa tìm kiếm hoặc loại khuyến mãi
    $scope.$watch('searchText', function (newValue, oldValue) {
        if (newValue !== oldValue) {
            $scope.getKhuyenMaiList(); // Gọi lại API khi thay đổi từ khóa tìm kiếm
        }
    });

    // Hàm gọi khi thay đổi loại khuyến mãi
    $scope.$watch('selectedLoai', function (newValue, oldValue) {
        if (newValue !== oldValue) {
            $scope.getKhuyenMaiList(); // Gọi lại API khi thay đổi loại khuyến mãi
        }
    });

    // Khởi tạo danh sách khuyến mãi khi load trang
    $scope.initialize = function () {
        $scope.getKhuyenMaiList(); // Lấy danh sách khuyến mãi
    };

    // Khởi tạo trang ban đầu
    $scope.initialize();
    // Mở modal tạo mới khuyến mãi
    $scope.openCreateModal = function () {
        $scope.form = {};  // Reset dữ liệu form trước khi mở modal
        $('#addNewModal').modal('show');  // Mở modal
        $scope.loadProducts();
        $scope.loadUsers();
    };

    // Dữ liệu khuyến mãi
    $scope.form = {
        loai: null,  // 1: Sản phẩm, 2: Người dùng
        ma: '', ten: '', moTa: '', giaTri: 0, ngayBatDau: '', ngayKetThuc: '', idSanPhamList: [], idNguoiDungList: []
    };

    $scope.saveKhuyenMai = function () {

        if (!$scope.form.loai) {
            toastr.error("Vui lòng chọn đối tượng khuyến mãi.", "Lỗi!");
            return;
        }


        if (($scope.form.loai == 1) && !$scope.form.ten) {
            toastr.error("Vui lòng nhập tên khuyến mãi.", "Lỗi!");
            return;
        }
        if (($scope.form.loai == 2) && !$scope.form.ma) {
            toastr.error("Vui lòng nhập mã khuyến mãi.", "Lỗi!");
            return;
        }


        if (!$scope.form.moTa) {
            toastr.error("Vui lòng nhập mô tả cho khuyến mãi.", "Lỗi!");
            return;
        }


        if (!$scope.form.giaTri || $scope.form.giaTri <= 0) {
            toastr.error("Vui lòng nhập giá trị khuyến mãi hợp lệ.", "Lỗi!");
            return;
        }


        if (!$scope.form.ngayBatDau) {
            toastr.error("Vui lòng chọn ngày bắt đầu.", "Lỗi!");
            return;
        }

        if (!$scope.form.ngayKetThuc) {
            toastr.error("Vui lòng chọn ngày kết thúc.", "Lỗi!");
            return;
        }

        // Kiểm tra nếu ngày kết thúc phải lớn hơn ngày bắt đầu
        var ngayBatDau = dayjs($scope.form.ngayBatDau);
        var ngayKetThuc = dayjs($scope.form.ngayKetThuc);
        if (ngayKetThuc.isBefore(ngayBatDau)) {
            toastr.error("Ngày kết thúc phải lớn hơn ngày bắt đầu.", "Lỗi!");
            return;
        }

        var requestData = {
            ma: $scope.form.loai === 1 ? '' : $scope.form.ma,  // Nếu loai là 1 thì không gửi ma
            ten: $scope.form.loai === 2 ? '' : $scope.form.ten, // Nếu loai là 2 thì không gửi ten
            moTa: $scope.form.moTa,
            giaTri: $scope.form.giaTri,
            ngayBatDau: ngayBatDau.format('YYYY-MM-DDTHH:mm:ss'),
            ngayKetThuc: ngayKetThuc.format('YYYY-MM-DDTHH:mm:ss'),
            loai: $scope.form.loai,
            idSanPhamList: [],
            idNguoiDungList: []
        };

        // Nếu loại là 1 (Khuyến mãi cho sản phẩm), lấy các sản phẩm đã chọn
        if ($scope.form.loai === 1) {
            // Kiểm tra nếu không có sản phẩm nào được chọn
            var selectedProducts = $scope.products.filter(product => product.selected);
            if (selectedProducts.length === 0) {
                toastr.error("Vui lòng chọn sản phẩm trong khuyến mãi.", "Lỗi!");
                return;
            }
            angular.forEach(selectedProducts, function (product) {
                requestData.idSanPhamList.push(product.id);
            });
        }

        // Nếu loại là 2 (Khuyến mãi cho người dùng), lấy các người dùng đã chọn
        if ($scope.form.loai === 2) {
            // Kiểm tra nếu không có người dùng nào được chọn
            var selectedUsers = $scope.users.filter(user => user.selected);
            if (selectedUsers.length === 0) {
                toastr.error("Vui lòng chọn người dùng trong khuyến mãi.", "Lỗi!");
                return;
            }
            angular.forEach(selectedUsers, function (user) {
                requestData.idNguoiDungList.push(user.id);
            });
        }

        swal({
            title: "Xác nhận",
            text: "Bạn có chắc chắn muốn lưu khuyến mãi này không?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willSave) => {
                if (willSave) {
                    var token = localStorage.getItem('token');

                    $http.post('/khuyen-mai/create', requestData, {
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    })
                        .then(function (response) {
                            toastr.success("Khuyến mãi đã được lưu thành công!", "Thành công!");
                            $('#addNewModal').modal('hide'); // Đóng modal sau khi lưu thành công
                            // $scope.resetForm();
                            $scope.getKhuyenMaiList(); // Reset form sau khi lưu thành công
                        }, function (error) {
                            console.error('Có lỗi xảy ra khi lưu khuyến mãi:', error);
                            toastr.error("Có lỗi xảy ra khi lưu khuyến mãi. Vui lòng thử lại!", "Lỗi!");
                        });
                } else {
                    // Người dùng hủy thao tác
                    toastr.info("Hủy hành động!", "Hủy!");
                }
            });
    };



    $scope.products = [];
    $scope.users = [];
    $scope.selectAllProducts = false;  // Dùng để theo dõi trạng thái checkbox ở tiêu đề bảng sản phẩm
    $scope.selectAllUsers = false;  // Dùng để theo dõi trạng thái checkbox ở tiêu đề bảng người dùng


    $scope.loadProducts = function () {
        $http.get('/san-pham')  // Gọi API GET để lấy dữ liệu sản phẩm
            .then(function (response) {
                // Cập nhật dữ liệu vào mảng products
                $scope.products = response.data.data;
            }, function (error) {
                console.error("Có lỗi xảy ra khi gọi API sản phẩm: ", error);
            });
    };

    $scope.loadUsers = function () {
        $http.get('/user/list')  // Gọi API GET để lấy dữ liệu người dùng
            .then(function (response) {
                // Cập nhật dữ liệu vào mảng users
                $scope.users = response.data.data;
            }, function (error) {
                console.error("Có lỗi xảy ra khi gọi API người dùng: ", error);
            });
    };


    $scope.toggleSelectAllProducts = function () {
        angular.forEach($scope.products, function (product) {
            product.selected = $scope.selectAllProducts;
        });
    };

    $scope.toggleSelectAllUsers = function () {
        angular.forEach($scope.users, function (user) {
            user.selected = $scope.selectAllUsers;
        });
    };

    $scope.delete = function (item) {
        // Xác nhận người dùng muốn xóa không
        swal({
            title: "Xác nhận",
            text: "Bạn có chắc muốn xóa khuyến mãi này?",
            icon: "warning",
            buttons: ["Hủy", "Xác nhận"],
            dangerMode: true,
        }).then((willUpdate) => {
            if (willUpdate) {
                $http.delete('/khuyen-mai/' + item.id)
                    .then(function (response) {
                        // Thông báo xóa thành công
                        toastr.success("Khuyến mãi đã được xóa thành công!.", "Thành công!");

                        // Loại bỏ mục bị xóa khỏi danh sách hiện tại
                        var index = $scope.items.indexOf(item);
                        if (index !== -1) {
                            $scope.items.splice(index, 1);
                        }

                        // Gọi lại API để tải lại danh sách sau khi xóa
                        $scope.getKhuyenMaiList();
                    }, function (error) {
                        // Xử lý lỗi khi xóa không thành công
                        console.error('Có lỗi xảy ra khi xóa:', error);
                        toastr.error("Có lỗi xảy ra khi xóa. Vui lòng thử lại!,", "Lỗi!");
                    });
            }
        });
    };

    $scope.loaiOptions = ["Sản phẩm", "Người dùng"];
    // detail or edit
    $scope.getKhuyenMaiDetail = function (id, isEditMode) {
        $http.get('/khuyen-mai/' + id)
            .then(function (response) {
                var data = response.data.data;
                $scope.detailForm = {
                    id: data.id,
                    ten: data.ten,
                    ma: data.ma,
                    moTa: data.moTa,
                    giaTri: data.giaTri,
                    ngayBatDau: data.ngayBatDau,
                    ngayKetThuc: data.ngayKetThuc,
                    loai: data.loai === 1 ? "Sản phẩm" : "Người dùng",
                };

                if (isEditMode) {
                    // Tải sản phẩm và người dùng khi ở chế độ chỉnh sửa
                    if (data.loai === 1) {

                        $scope.loadProducts().then(function () {
                            // Đánh dấu các sản phẩm đã được chọn
                            $scope.selectedProducts = data.sanPhamModels.map(function (sp) {
                                let product = $scope.products.find(p => p.id === sp.id);
                                if (product) {
                                    product.selected = true;
                                    console.log('Product selected:', product);
                                } else {
                                    console.log('Product not found:', sp.id);
                                }
                                return {id: sp.id, selected: true};
                            });
                        });
                    } else {

                        $scope.loadUsers().then(function () {
                            // Đánh dấu các người dùng đã được chọn
                            $scope.selectedUsers = data.userModels.map(function (nd) {
                                let user = $scope.users.find(u => u.id === nd.id);
                                if (user) {
                                    user.selected = true;
                                    console.log('User selected:', user);
                                } else {
                                    console.log('User not found:', nd.id);
                                }
                                return {id: nd.id, selected: true};
                            });
                        });

                    }
                } else {
                    // Khi ở chế độ xem, chỉ giữ các giá trị hiện có
                    if (data.loai === 1) {
                        $scope.products = data.sanPhamModels;
                        $scope.users = [];
                    } else if (data.loai === 2) {
                        $scope.users = data.userModels;
                        $scope.products = [];
                    }
                }

                $scope.isEditMode = isEditMode;
            })
            .catch(function (error) {
                console.error('Error fetching khuyen mai details:', error);
            });
    };


    $scope.viewDetail = function (id) {
        $scope.isEditMode = false; // Set isEditMode to false
        $scope.getKhuyenMaiDetail(id, false);
        $('#viewDetailModal').modal('show'); // Open the modal
    };
    $scope.editDetail = function (id) {
        $scope.isEditMode = true; // Set isEditMode to true
        $scope.getKhuyenMaiDetail(id, true);
        $('#viewDetailModal').modal('show'); // Open the modal
    };

    $scope.updateKhuyenMai = function () {
        // Kiểm tra nếu chưa chọn loai
        if (!$scope.detailForm.loai) {
            toastr.warning("Vui lòng chọn đối tượng khuyến mãi.", "Vui lòng!");
            return;  // Dừng hàm nếu không chọn loại
        }

        // Nếu bạn sử dụng Day.js để định dạng lại ngày tháng
        var ngayBatDau = dayjs($scope.detailForm.ngayBatDau).format('YYYY-MM-DDTHH:mm:ss');
        var ngayKetThuc = dayjs($scope.detailForm.ngayKetThuc).format('YYYY-MM-DDTHH:mm:ss');

        // Tạo đối tượng request data để gửi lên API
        var requestData = {
            ma: $scope.detailForm.loai === 'Sản phẩm' ? '' : $scope.detailForm.ma,  // Nếu loai là Sản phẩm, không gửi mã
            ten: $scope.detailForm.loai === 'Người dùng' ? '' : $scope.detailForm.ten, // Nếu loai là Người dùng, không gửi tên
            moTa: $scope.detailForm.moTa,
            giaTri: $scope.detailForm.giaTri,
            ngayBatDau: ngayBatDau,
            ngayKetThuc: ngayKetThuc,
            loai: $scope.detailForm.loai === 'Sản phẩm' ? 1 : 2, // 1 là Sản phẩm, 2 là Người dùng
            idSanPhamList: [],
            idNguoiDungList: []
        };

        // Nếu loại là 1 (Khuyến mãi cho sản phẩm), lấy các sản phẩm đã chọn
        if ($scope.detailForm.loai === 'Sản phẩm') {
            var selectedProducts = $scope.products.filter(product => product.selected);
            if (selectedProducts.length === 0) {
                toastr.warning("Vui lòng chọn sản phẩm trong khuyến mãi.", "Vui lòng!");
                return;
            }
            angular.forEach(selectedProducts, function (product) {
                requestData.idSanPhamList.push(product.id);
            });
        }

        // Nếu loại là 2 (Khuyến mãi cho người dùng), lấy các người dùng đã chọn
        if ($scope.detailForm.loai === 'Người dùng') {
            var selectedUsers = $scope.users.filter(user => user.selected);
            if (selectedUsers.length === 0) {
                toastr.warning("Vui lòng chọn người dùng trong khuyến mãi.", "Vui lòng!");
                return;  // Dừng hàm nếu không có người dùng nào được chọn
            }
            angular.forEach(selectedUsers, function (user) {
                requestData.idNguoiDungList.push(user.id);  // Thêm ID người dùng vào danh sách
            });
        }

        // Hiển thị hộp thoại xác nhận trước khi cập nhật
        swal({
            title: "Xác nhận",
            text: "Bạn có chắc chắn muốn cập nhật khuyến mãi này không?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willUpdate) => {
                if (willUpdate) {
                    // Lấy token từ localStorage
                    var token = localStorage.getItem('token');

                    // Gửi dữ liệu đến API để cập nhật khuyến mãi
                    $http.put('/khuyen-mai/update/' + $scope.detailForm.id, requestData, {
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    })
                        .then(function (response) {
                            // Hiển thị thông báo thành công
                            toastr.success("Khuyến mãi đã được cập nhật thành công!", "Thành công!");
                            $('#viewDetailModal').modal('hide'); // Đóng modal sau khi lưu thành công
                            $scope.getKhuyenMaiList(); // Cập nhật lại danh sách khuyến mãi
                        }, function (error) {
                            console.error('Có lỗi xảy ra khi cập nhật khuyến mãi:', error);
                            toastr.error("Có lỗi xảy ra khi cập nhật khuyến mãi. Vui lòng thử lại!", "Lỗi!");
                        });
                } else {
                    // Người dùng hủy thao tác
                    toastr.info("Hủy hành động!", "Hủy!");
                }
            });
    };


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
