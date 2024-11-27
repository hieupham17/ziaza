app.controller("apdungkhuyenmai-ctrl", function ($scope, $http) {
    $scope.items = [];
    $scope.form = {};
    $scope.formAdd = {};
    $scope.selectedLoai = null;
    $scope.searchText = ''; // Biến tìm kiếm
    $scope.pager = {
        page: 0,
        size: 5,
        items: [],
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
            // Lọc các mục theo tìm kiếm và loại
            const filteredItems = $scope.items.filter(item => {
                const matchesSearch = item.id.toString().toLowerCase().includes($scope.searchText.toLowerCase()) ||
                    item.ten.toLowerCase().includes($scope.searchText.toLowerCase());
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
    $scope.getKhuyenMaiList = function() {
        var requestData = {
            keyword: $scope.searchText,
            loai: $scope.selectedLoai
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
                alert('Có lỗi xảy ra khi tải dữ liệu khuyến mãi.');
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
        ma: '',
        ten: '',
        moTa: '',
        giaTri: 0,
        ngayBatDau: '',
        ngayKetThuc: '',
        idSanPhamList: [],
        idNguoiDungList: []
    };
    $scope.saveKhuyenMai = function () {
        // Kiểm tra nếu chưa chọn loai
        if (!$scope.form.loai) {
            alert('Vui lòng chọn đối tượng khuyến mãi.');
            return;  // Dừng hàm nếu không chọn loại
        }
        // Nếu bạn sử dụng Day.js
        var ngayBatDau = dayjs($scope.form.ngayBatDau).format('YYYY-MM-DDTHH:mm:ss');
        var ngayKetThuc = dayjs($scope.form.ngayKetThuc).format('YYYY-MM-DDTHH:mm:ss');

        var requestData = {
            ma: $scope.form.loai === 1 ? '' : $scope.form.ma,  // Nếu loai là 1 thì không gửi ma
            ten: $scope.form.loai === 2 ? '' : $scope.form.ten, // Nếu loai là 2 thì không gửi ten
            moTa: $scope.form.moTa,
            giaTri: $scope.form.giaTri,
            ngayBatDau: ngayBatDau,
            ngayKetThuc: ngayKetThuc,
            loai: $scope.form.loai,
            idSanPhamList: [],
            idNguoiDungList: []
        };

        // Nếu loại là 1 (Khuyến mãi cho sản phẩm), lấy các sản phẩm đã chọn
        if ($scope.form.loai === 1) {
            // Kiểm tra nếu không có sản phẩm nào được chọn
            var selectedProducts = $scope.products.filter(product => product.selected);
            if (selectedProducts.length === 0) {
                alert('Vui lòng chọn sản phẩm trong khuyến mãi.');
                return;  // Dừng hàm nếu không có sản phẩm nào được chọn
            }
            angular.forEach(selectedProducts, function (product) {
                requestData.idSanPhamList.push(product.id);  // Thêm ID sản phẩm vào danh sách
            });
        }

        // Nếu loại là 2 (Khuyến mãi cho người dùng), lấy các người dùng đã chọn
        if ($scope.form.loai === 2) {
            // Kiểm tra nếu không có người dùng nào được chọn
            var selectedUsers = $scope.users.filter(user => user.selected);
            if (selectedUsers.length === 0) {
                alert('Vui lòng chọn người dùng trong khuyến mãi.');
                return;  // Dừng hàm nếu không có người dùng nào được chọn
            }
            angular.forEach(selectedUsers, function (user) {
                requestData.idNguoiDungList.push(user.id);  // Thêm ID người dùng vào danh sách
            });
        }

        // Lấy token từ localStorage
        var token = localStorage.getItem('token');

        // Gửi dữ liệu đến API để lưu, kèm theo token trong headers
        $http.post('/khuyen-mai/create', requestData, {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
            .then(function (response) {
                // Hiển thị thông báo thành công
                alert('Khuyến mãi đã được lưu thành công!');
                $('#addNewModal').modal('hide');  // Đóng modal sau khi lưu thành công
                $scope.resetForm();
                $scope.getKhuyenMaiList();// Reset form sau khi lưu thành công
            }, function (error) {
                console.error('Có lỗi xảy ra khi lưu khuyến mãi:', error);
                alert('Có lỗi xảy ra khi lưu khuyến mãi. Vui lòng thử lại!');
            });
    };


    $scope.products = [];
    $scope.users = [];
    $scope.selectAllProducts = false;  // Dùng để theo dõi trạng thái checkbox ở tiêu đề bảng sản phẩm
    $scope.selectAllUsers = false;  // Dùng để theo dõi trạng thái checkbox ở tiêu đề bảng người dùng


    // Hàm để gọi API lấy danh sách sản phẩm
    $scope.loadProducts = function () {
        $http.get('/san-pham')  // Gọi API GET để lấy dữ liệu sản phẩm
            .then(function (response) {
                // Cập nhật dữ liệu vào mảng products
                $scope.products = response.data.data;
            }, function (error) {
                console.error("Có lỗi xảy ra khi gọi API: ", error);
            });
    };

    // Hàm để gọi API lấy danh sách sản phẩm
    $scope.loadUsers = function () {
        $http.get('/user/list')  // Gọi API GET để lấy dữ liệu sản phẩm
            .then(function (response) {
                // Cập nhật dữ liệu vào mảng products
                $scope.users = response.data.data;
            }, function (error) {
                console.error("Có lỗi xảy ra khi gọi API: ", error);
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

    $scope.delete = function(item) {
        // Xác nhận người dùng muốn xóa không
        if (confirm('Bạn có chắc chắn muốn xóa mục này?')) {
            // Gửi yêu cầu DELETE tới API
            $http.delete('/khuyen-mai/' + item.id)
                .then(function(response) {
                    // Thông báo xóa thành công
                    alert('Khuyến mãi đã được xóa thành công!');

                    // Loại bỏ mục bị xóa khỏi danh sách hiện tại
                    var index = $scope.items.indexOf(item);
                    if (index !== -1) {
                        $scope.items.splice(index, 1);
                    }

                    // Gọi lại API để tải lại danh sách sau khi xóa
                    $scope.getKhuyenMaiList();
                }, function(error) {
                    // Xử lý lỗi khi xóa không thành công
                    console.error('Có lỗi xảy ra khi xóa:', error);
                    alert('Có lỗi xảy ra khi xóa. Vui lòng thử lại!');
                });
        }
    };
    // detail
    $scope.viewDetail = function(id) {
        // Gọi API để lấy thông tin chi tiết của khuyến mãi
        $http.get('/khuyen-mai/' + id)
            .then(function(response) {
                // Nếu API trả về thành công, cập nhật thông tin vào modal
                var data = response.data.data;
                $scope.detailForm = {
                    ten: data.ten,
                    ma: data.ma,
                    moTa: data.moTa,
                    giaTri: data.giaTri,
                    ngayBatDau: data.ngayBatDau,
                    ngayKetThuc: data.ngayKetThuc,
                    loaiText: (data.loai === 1) ? "Sản phẩm" : "Người dùng"
                };
                if (data.loai === 1) {
                    $scope.products = data.sanPhamModels;
                    $scope.users = [];
                } else if (data.loai === 2) {
                    $scope.users = data.userModels;
                    $scope.products = [];
                }
            })
            .catch(function(error) {
                console.error('Error fetching khuyen mai details:', error);
            });
    };


});
