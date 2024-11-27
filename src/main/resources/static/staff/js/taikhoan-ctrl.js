app.controller('taikhoan-ctrl', function ($scope, $http) {
    // Biến để lưu danh sách người dùng
    $scope.employees = [];

    // Biến cho phân trang
    $scope.pager = {
        page: 0,
        count: 0,
        itemsPerPage: 10, // Số lượng người dùng trên mỗi trang
        first: function () {
            this.page = 0;
            fetchUsers();
        },
        prev: function () {
            if (this.page > 0) {
                this.page--;
                fetchUsers();
            }
        },
        next: function () {
            if (this.page < this.count - 1) {
                this.page++;
                fetchUsers();
            }
        },
        last: function () {
            this.page = this.count - 1;
            fetchUsers();
        }
    };

    // Biến cho vai trò (khởi tạo không có giá trị)
    $scope.selectedRole = null; // Mặc định không có giá trị role

    // Hàm gọi API để lấy danh sách người dùng với vai trò và phân trang
    function fetchUsers() {
        const userQuery = {
            page: $scope.pager.page + 1,  // API mong muốn page bắt đầu từ 1
            size: $scope.pager.itemsPerPage,
            keyword: $scope.searchText || ""  // Tìm kiếm theo họ và tên
        };

        // Chỉ thêm role vào request nếu role có giá trị
        if ($scope.selectedRole) {
            userQuery.role = $scope.selectedRole;
        }

        $http.post('user/get-list', userQuery)
            .then(function (response) {
                if (response.data && response.data.data) {
                    $scope.employees = response.data.data;
                    $scope.employees.currentPage = response.data.currentPage;
                    $scope.employees.totalPage = response.data.totalPage;
                    $scope.pager.count = Math.ceil(response.data.totalCount / $scope.pager.itemsPerPage);
                    updateUsersForCurrentPage();
                }
            }).catch(function (error) {
            console.error('Lỗi khi gọi API:', error);
        });
    }

    // Cập nhật danh sách người dùng cho trang hiện tại
    function updateUsersForCurrentPage() {
        const startIndex = $scope.pager.page * $scope.pager.itemsPerPage;
        const endIndex = startIndex + $scope.pager.itemsPerPage;
        $scope.currentEmployees = $scope.employees.slice(startIndex, endIndex);
    }

    // Gọi hàm fetchUsers khi controller khởi tạo
    fetchUsers();

    // Khi thay đổi vai trò, gọi lại API để lấy dữ liệu mới
    $scope.$watch('selectedRole', function() {
        $scope.pager.page = 0; // Reset về trang đầu tiên
        fetchUsers();
    });

    // Khi thay đổi từ khóa tìm kiếm, gọi lại API để lọc theo từ khóa
    $scope.$watch('searchText', function() {
        $scope.pager.page = 0; // Reset về trang đầu tiên
        fetchUsers();
    });
    $scope.newAccount = {
        role: 'USER'
    };
    // Hàm thêm tài khoản
    $scope.addAccount = function () {
        // Kiểm tra các trường không được trống
        if (!$scope.newAccount.name || $scope.newAccount.name.trim() === "") {
            toastr.error('Tên không được để trống.', 'Lỗi');
            return;
        }

        if (!$scope.newAccount.email || $scope.newAccount.email.trim() === "") {
            toastr.error('Email không được để trống.', 'Lỗi');
            return;
        }

        if (!$scope.newAccount.phone || $scope.newAccount.phone.trim() === "") {
            toastr.error('Số điện thoại không được để trống.', 'Lỗi');
            return;
        }

        if (!$scope.newAccount.password || $scope.newAccount.password.trim() === "") {
            toastr.error('Mật khẩu không được để trống.', 'Lỗi');
            return;
        }

        if (!$scope.newAccount.retypePassword || $scope.newAccount.retypePassword.trim() === "") {
            toastr.error('Nhập lại mật khẩu không được để trống.', 'Lỗi');
            return;
        }

        // Kiểm tra tính hợp lệ của các trường nhập liệu
        if ($scope.newAccount.name.length > 50) {
            toastr.error('Tên không hợp lệ. Vui lòng kiểm tra lại.', 'Lỗi');
            return;
        }

        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailPattern.test($scope.newAccount.email)) {
            toastr.error('Email không hợp lệ. Vui lòng nhập lại.', 'Lỗi');
            return;
        }

        const phonePattern = /^[0-9]{10,20}$/;
        if (!phonePattern.test($scope.newAccount.phone)) {
            toastr.error('Số điện thoại không hợp lệ. Vui lòng kiểm tra lại.', 'Lỗi');
            return;
        }

        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*([0-9]|[^0-9dA-Za-z])).{8,100}$/;
        if (!passwordPattern.test($scope.newAccount.password)) {
            toastr.error('Mật khẩu yếu. Mật khẩu phải chứa chữ hoa, chữ thường, số và ký tự đặc biệt.', 'Lỗi');
            return;
        }

        if ($scope.newAccount.password !== $scope.newAccount.retypePassword) {
            toastr.error('Mật khẩu không khớp. Vui lòng nhập lại mật khẩu.', 'Lỗi');
            return;
        }

        // Xác nhận với SweetAlert (swal) trước khi gọi API
        swal({
            title: "Bạn có chắc chắn?",
            text: "Bạn muốn thêm tài khoản này?",
            icon: "warning",
            buttons: ["Hủy", "Đồng ý"],
            dangerMode: true,
        }).then((willAdd) => {
            if (willAdd) {
                // Gọi API nếu người dùng xác nhận
                $http.post('/register', $scope.newAccount)
                    .then(function (response) {
                        if (response.data && response.data.code === "200" && response.data.message === "Thành công") {
                            toastr.success('Đăng ký thành công!', 'Thành công');
                            fetchUsers(); // Cập nhật danh sách người dùng
                            $('#addModal').modal('hide');
                            $scope.newAccount = {}; // Reset form
                        } else {
                            toastr.error('Có lỗi xảy ra: ' + (response.data.message || 'Vui lòng kiểm tra thông tin nhập vào.'), 'Lỗi');
                        }
                    })
                    .catch(function (error) {
                        console.error('Lỗi khi gọi API:', error);
                        toastr.error('Có lỗi xảy ra khi gọi API. Vui lòng kiểm tra console.', 'Lỗi');
                    });
            }
        });
    };



    // Các hàm chi tiết, xóa người dùng
    $scope.detailUser = function (userId) {
        $http.get(`/user/${userId}`)
            .then(function (response) {
                if (response.data && response.data.code === "200") {
                    $scope.selectedUser = response.data.data;
                    $('#detailModal').modal('show');
                } else {
                    alert('Có lỗi xảy ra: ' + response.data.message);
                }
            })
            .catch(function (error) {
                console.error('Lỗi khi gọi API:', error);
                alert('Có lỗi xảy ra khi gọi API. Vui lòng kiểm tra console.');
            });
    };

    // Hàm chuẩn bị modal để khóa người dùng
    $scope.prepareLockUser = function (user) {
        $scope.selectedUser = user;
        $scope.modalTitle = 'Xác nhận khóa người dùng';
        $scope.modalMessage = 'Bạn có chắc chắn muốn khóa người dùng này không?';
        $scope.modalConfirmButton = 'Khóa';
        $scope.modalAction = 'LOCKED';

        // Sử dụng SweetAlert để xác nhận hành động
        swal({
            title: $scope.modalTitle,
            text: $scope.modalMessage,
            icon: "warning",
            buttons: ["Hủy", $scope.modalConfirmButton],
            dangerMode: true
        }).then((willLock) => {
            if (willLock) {
                $scope.confirmAction(); // Gọi hàm confirmAction nếu người dùng chọn "Khóa"
            }
        });
    };

// Hàm chuẩn bị modal để mở khóa người dùng
    $scope.prepareUnlockUser = function (user) {
        $scope.selectedUser = user;
        $scope.modalTitle = 'Xác nhận mở khóa người dùng';
        $scope.modalMessage = 'Bạn có chắc chắn muốn mở khóa người dùng này không?';
        $scope.modalConfirmButton = 'Mở khóa';
        $scope.modalAction = 'ACTIVE';

        // Sử dụng SweetAlert để xác nhận hành động
        swal({
            title: $scope.modalTitle,
            text: $scope.modalMessage,
            icon: "warning",
            buttons: ["Hủy", $scope.modalConfirmButton],
            dangerMode: true
        }).then((willUnlock) => {
            if (willUnlock) {
                $scope.confirmAction(); // Gọi hàm confirmAction nếu người dùng chọn "Mở khóa"
            }
        });
    };

// Hàm xác nhận hành động khóa/mở khóa
    $scope.confirmAction = function () {
        if ($scope.selectedUser && $scope.modalAction) {
            const status = $scope.modalAction;
            $http.put(`/user/change-status/${$scope.selectedUser.id}?status=${status}`).then(function (response) {
                if (response.data && response.data.code === "200") {
                    const action = (status === 'LOCKED') ? 'Khóa' : 'Mở khóa';
                    toastr.success(`${action} người dùng thành công!`, 'Thành công');
                    fetchUsers(); // Cập nhật lại danh sách người dùng
                } else {
                    toastr.error('Có lỗi xảy ra: ' + (response.data.message || 'Vui lòng kiểm tra lại.'), 'Lỗi');
                }
            }).catch(function (error) {
                console.error('Lỗi khi gọi API:', error);
                toastr.error('Có lỗi xảy ra khi gọi API. Vui lòng kiểm tra console.', 'Lỗi');
            });
        }
    };


});
