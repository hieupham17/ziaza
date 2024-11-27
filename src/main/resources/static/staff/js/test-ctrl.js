// app.controller("doitrahang-ctrl", function ($scope, $http, $rootScope, $location) {
//     $scope.items = [];
//     $scope.form = {};
//
//
//     $scope.selectedID = null;
//     $scope.selectedMAHD = null;
//     $scope.searchText1 = ''; // Tìm kiếm cho trạng thái 1
//     $scope.searchText2 = '';
//     $scope.searchText3 = '';
//     $scope.searchText4 = '';
//     $scope.searchText5 = '';
//     $scope.searchText6 = '';
//     // Thêm searchText cho các trạng thái khác nếu cần
//
//     $scope.initialize = function () {
//         // Gọi API và kiểm tra dữ liệu
//         $http.get("/yeu-cau").then(resp => {
//             console.log("Dữ liệu từ API: ", resp.data); // Kiểm tra dữ liệu từ API
//             // Kiểm tra xem resp.data.data có phải là mảng không
//             if (Array.isArray(resp.data.data)) {
//                 $scope.items = resp.data.data.map(item => ({
//                     ...item,
//                     ngayTao: new Date(item.ngayTao), // Chuyển đổi ngày
//                     ngayCapNhat: new Date(item.ngayCapNhat) // Chuyển đổi ngày
//                 }));
//                 $scope.pagerDH0.updateItems();
//                 $scope.pagerDH1.updateItems();
//                 $scope.pagerDH2.updateItems();
//                 $scope.pagerTH0.updateItems();
//                 $scope.pagerTH1.updateItems();
//                 $scope.pagerTH2.updateItems();
//             } else {
//                 console.error("API không trả về một mảng. Kiểm tra cấu trúc dữ liệu.");
//             }
//         }).catch(error => {
//             console.error("Lỗi khi tải danh mục: ", error);
//         });
//     };
//
//     $scope.pagerDH0 = {
//         page: 0,
//         size: 5,
//         items: [],
//         count: 0,
//         first: function () {
//             this.page = 0;
//             this.updateItems();
//         },
//         prev: function () {
//             if (this.page > 0) {
//                 this.page--;
//                 this.updateItems();
//             }
//         },
//         next: function () {
//             if (this.page < this.count - 1) {
//                 this.page++;
//                 this.updateItems();
//             }
//         },
//         last: function () {
//             this.page = this.count - 1;
//             this.updateItems();
//         },
//         updateItems: function () {
//             const filteredItems = $scope.items.filter(item => {
//                 const statusMatches = item.trangThai === 0;
//                 const loaiYeuCau = item.loai === 'EXCHANGE';
//                 const idMatches = item.id.toString().includes($scope.searchText1);
//                 return statusMatches && idMatches && loaiYeuCau;
//             });
//             this.count = Math.ceil(filteredItems.length / this.size);
//             this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
//         }
//     };
//
//     $scope.pagerDH1 = {
//         page: 0,
//         size: 5,
//         items: [],
//         count: 0,
//         first: function () {
//             this.page = 0;
//             this.updateItems();
//         },
//         prev: function () {
//             if (this.page > 0) {
//                 this.page--;
//                 this.updateItems();
//             }
//         },
//         next: function () {
//             if (this.page < this.count - 1) {
//                 this.page++;
//                 this.updateItems();
//             }
//         },
//         last: function () {
//             this.page = this.count - 1;
//             this.updateItems();
//         },
//         updateItems: function () {
//             const filteredItems = $scope.items.filter(item => {
//                 const statusMatches = item.trangThai === 1;
//                 const loaiYeuCau = item.loai === 'EXCHANGE';
//                 const idMatches = item.id.toString().includes($scope.searchText1);
//                 return statusMatches && idMatches && loaiYeuCau;
//             });
//             this.count = Math.ceil(filteredItems.length / this.size);
//             this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
//         }
//     };
//
//     $scope.pagerDH2 = {
//         page: 0,
//         size: 5,
//         items: [],
//         count: 0,
//         first: function () {
//             this.page = 0;
//             this.updateItems();
//         },
//         prev: function () {
//             if (this.page > 0) {
//                 this.page--;
//                 this.updateItems();
//             }
//         },
//         next: function () {
//             if (this.page < this.count - 1) {
//                 this.page++;
//                 this.updateItems();
//             }
//         },
//         last: function () {
//             this.page = this.count - 1;
//             this.updateItems();
//         },
//         updateItems: function () {
//             const filteredItems = $scope.items.filter(item => {
//                 const statusMatches = item.trangThai === 2;
//                 const loaiYeuCau = item.loai === 'EXCHANGE';
//                 const idMatches = item.id.toString().includes($scope.searchText1);
//                 return statusMatches && idMatches && loaiYeuCau;
//             });
//             this.count = Math.ceil(filteredItems.length / this.size);
//             this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
//         }
//     };
//
//
//     $scope.pagerTH0 = {
//         page: 0,
//         size: 5,
//         items: [],
//         count: 0,
//         first: function () {
//             this.page = 0;
//             this.updateItems();
//         },
//         prev: function () {
//             if (this.page > 0) {
//                 this.page--;
//                 this.updateItems();
//             }
//         },
//         next: function () {
//             if (this.page < this.count - 1) {
//                 this.page++;
//                 this.updateItems();
//             }
//         },
//         last: function () {
//             this.page = this.count - 1;
//             this.updateItems();
//         },
//         updateItems: function () {
//             const filteredItems = $scope.items.filter(item => {
//                 const statusMatches = item.trangThai === 0;
//                 const loaiYeuCau = item.loai === 'RETURN';
//                 const idMatches = item.id.toString().includes($scope.searchText1);
//                 return statusMatches && idMatches && loaiYeuCau;
//             });
//             this.count = Math.ceil(filteredItems.length / this.size);
//             this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
//         }
//     };
//
//     $scope.pagerTH1 = {
//         page: 0,
//         size: 5,
//         items: [],
//         count: 0,
//         first: function () {
//             this.page = 0;
//             this.updateItems();
//         },
//         prev: function () {
//             if (this.page > 0) {
//                 this.page--;
//                 this.updateItems();
//             }
//         },
//         next: function () {
//             if (this.page < this.count - 1) {
//                 this.page++;
//                 this.updateItems();
//             }
//         },
//         last: function () {
//             this.page = this.count - 1;
//             this.updateItems();
//         },
//         updateItems: function () {
//             const filteredItems = $scope.items.filter(item => {
//                 const statusMatches = item.trangThai === 1;
//                 const loaiYeuCau = item.loai === 'RETURN';
//                 const idMatches = item.id.toString().includes($scope.searchText1);
//                 return statusMatches && idMatches && loaiYeuCau;
//             });
//             this.count = Math.ceil(filteredItems.length / this.size);
//             this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
//         }
//     };
//
//     $scope.pagerTH2 = {
//         page: 0,
//         size: 5,
//         items: [],
//         count: 0,
//         first: function () {
//             this.page = 0;
//             this.updateItems();
//         },
//         prev: function () {
//             if (this.page > 0) {
//                 this.page--;
//                 this.updateItems();
//             }
//         },
//         next: function () {
//             if (this.page < this.count - 1) {
//                 this.page++;
//                 this.updateItems();
//             }
//         },
//         last: function () {
//             this.page = this.count - 1;
//             this.updateItems();
//         },
//         updateItems: function () {
//             const filteredItems = $scope.items.filter(item => {
//                 const statusMatches = item.trangThai === 2;
//                 const loaiYeuCau = item.loai === 'RETURN';
//                 const idMatches = item.id.toString().includes($scope.searchText1);
//                 return statusMatches && idMatches && loaiYeuCau;
//             });
//             this.count = Math.ceil(filteredItems.length / this.size);
//             this.items = filteredItems.slice(this.page * this.size, (this.page + 1) * this.size);
//         }
//     };
//
//     // Theo dõi sự thay đổi trong ô tìm kiếm cho từng trạng thái
//     $scope.$watch('searchText1', function (newValue, oldValue) {
//         if (newValue !== oldValue) {
//             $scope.pagerDH0.updateItems();
//         }
//     });
//     $scope.$watch('searchText2', function (newValue, oldValue) {
//         if (newValue !== oldValue) {
//             $scope.pagerDH1.updateItems();
//         }
//     });
//     $scope.$watch('searchText3', function (newValue, oldValue) {
//         if (newValue !== oldValue) {
//             $scope.pagerTH0.updateItems();
//         }
//     });
//     $scope.$watch('searchText4', function (newValue, oldValue) {
//         if (newValue !== oldValue) {
//             $scope.pagerTH1.updateItems();
//         }
//     });
//     $scope.$watch('searchText5', function (newValue, oldValue) {
//         if (newValue !== oldValue) {
//             $scope.pagerTH2.updateItems();
//         }
//     });
//
//
//     // Hàm cập nhật trạng thái hóa đơn
//     $scope.update2 = function (item) {
//         swal({
//             title: "Xác nhận",
//             text: "Bạn có chắc muốn cập nhật trạng thái hóa đơn này không?",
//             icon: "warning",
//             buttons: true,
//             dangerMode: true,
//         }).then((willUpdate) => {
//             if (willUpdate) {
//                 item.trangThai = 2; // Cập nhật trạng thái
//                 $http.put(`/yeu-cau/${item.id}`, item).then(resp => {
//                     $scope.initialize(); // Tải lại dữ liệu
//                     swal("Success!", "Cập nhật thành công", "success");
//                 }).catch(error => {
//                     swal("Error!", "Cập nhật thất bại", "error");
//                     console.log("Error: ", error);
//                 });
//             } else {
//                 swal("Hủy cập nhật", "Cập nhật trạng thái hóa đơn đã bị hủy", "error");
//             }
//         });
//     };
//
//     $scope.update1 = function (item) {
//         swal({
//             title: "Xác nhận",
//             text: "Bạn có chắc muốn cập nhật trạng thái hóa đơn này không?",
//             icon: "warning",
//             buttons: true,
//             dangerMode: true,
//         }).then((willUpdate) => {
//             if (willUpdate) {
//                 item.trangThai = 1; // Cập nhật trạng thái
//                 $http.put(`/yeu-cau/${item.id}`, item).then(resp => {
//                     $scope.initialize(); // Tải lại dữ liệu
//                     swal("Success!", "Cập nhật thành công", "success");
//                 }).catch(error => {
//                     swal("Error!", "Cập nhật thất bại", "error");
//                     console.log("Error: ", error);
//                 });
//             } else {
//                 swal("Hủy cập nhật", "Cập nhật trạng thái hóa đơn đã bị hủy", "error");
//             }
//         });
//     };
//     //////////
//     $scope.edit = function (item) {
//         // Chuyển timestamp thành Date object
//         item.ngayCapNhat = new Date(item.ngayCapNhat);
//         item.ngayTao = new Date(item.ngayTao);
//         $scope.form = angular.copy(item);
//     };
//
//
//     $scope.selectInvoice = function (item) {
//         console.log("Selected Invoice ID: ", item.id); // Thêm log này
//         $rootScope.selectedID = item.id; // Lưu ID hóa đơn vào rootScope
//         $rootScope.selectedMAHD = item.hoaDon.ma; // Lưu ID hóa đơn vào rootScope
//         $location.path('/chitietdoitra'); // Chuyển hướng đến trang hdct
//     };
//
//     // Khởi tạo
//     $scope.initialize();
// });
