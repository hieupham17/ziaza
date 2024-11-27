app.controller("chitietdoitra-ctrl", function ($scope, $http, $rootScope) {
    $scope.items = [];
    $scope.form = {};
    $scope.selectedId = $rootScope.selectedID; // Lấy ID từ rootScope
    $scope.selectedMaHD = $rootScope.selectedMAHD; // Lấy ID từ rootScope

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
            const filteredItems = $scope.items.filter(item => {
                return item.idYeuCauDoiTra === $scope.selectedId;
            });
            this.count = Math.ceil(filteredItems.length / this.size);
            this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
        }
    };

    $scope.initialize = function () {
        $http.get("/yeu-cau-chi-tiet").then(resp => {
            if (Array.isArray(resp.data.data)) {
                $scope.items = resp.data.data.map(item => ({
                    ...item,
                    ngayTao: new Date(item.ngayTao), // Chuyển đổi ngày
                    ngayCapNhat: new Date(item.ngayCapNhat) // Chuyển đổi ngày
                }));
                $scope.pager.updateItems(); // Cập nhật các mục cho pager
            } else {
                console.error("API không trả về một mảng. Kiểm tra cấu trúc dữ liệu.");
            }
        }).catch(error => {
            console.log("Error loading data: ", error);
        });
    };

    // Khởi tạo
    $scope.initialize();


});
