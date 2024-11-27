
// Khởi tạo controller AngularJS
app.controller("banhang-ctrl", function ($scope, $http, $rootScope, $firebase, $location) {
    $scope.bills = [];
    $scope.activeBill = 0;
    $scope.showPopUp = false;
    $scope.listProduct = [];
    $scope.listProductPromotion = [];
    $scope.vouchers = [];
    $scope.searchQuery = '';
    $scope.sliderOffset = 0; // Vị trí dịch chuyển của slider
    $scope.sliderPosition = 0; // Vị trí hiện tại
    $scope.maxSliderPosition = 11;

    $scope.prevTab = function () {
        if ($scope.sliderPosition > 0) {
            $scope.sliderPosition--;
            $scope.sliderOffset += 150; // Mỗi lần trượt 150px (tùy chỉnh kích thước nút)
            console.log($scope.sliderPosition)
        }
    };

    $scope.nextTab = function () {
        if ($scope.sliderPosition < $scope.maxSliderPosition) {
            $scope.sliderPosition++;
            $scope.sliderOffset -= 150; // Mỗi lần trượt 150px
            console.log($scope.sliderPosition)
        }
    };

    $http.post('/khuyen-mai/get-list', {keyword:'', loai:null})
        .then(function (response) {
            let arrKm = response.data.data;
            $http.get('/ap-dung-khuyen-mai')
                .then(function (res) {
                    $scope.listProductPromotion = res.data.data;
                    $scope.listProductPromotion.forEach(function (product) {
                        const khuyenMai = arrKm.find(km => km.id === product.idKhuyenMai);
                        product.tenKhuyenMai = khuyenMai ? khuyenMai.ten : "Không có tên khuyến mãi";
                    });
                    console.log($scope.listProductPromotion)
                }, function (error) {
                    console.error("Có lỗi xảy ra khi gọi API sản phẩm: ", error);
                });
        }, function (error) {
            console.error("Có lỗi xảy ra khi gọi API sản phẩm: ", error);
        });




    // Thiết lập Firebase
    var ref = new Firebase("https://dantn-742db-default-rtdb.firebaseio.com");
    var sync = $firebase(ref);

    var ref = new Firebase("https://dantn-742db-default-rtdb.firebaseio.com");

// Khi sản phẩm mới được thêm
    ref.on('child_added', function(snapshot) {
        const product = snapshot.val();
        const productId = product.id;

        handleProduct(product, productId); // Gọi API xử lý sản phẩm mới
    });

// Khi sản phẩm cũ thay đổi
    ref.on('child_changed', function(snapshot) {
        const updatedProduct = snapshot.val();
        const productId = updatedProduct.id;

        // Kiểm tra nếu thay đổi là về số lượng
        if (updatedProduct.quantity) {
            handleProduct(updatedProduct, productId); // Gọi API xử lý sản phẩm thay đổi
        }
    });

// Hàm xử lý sản phẩm
    function handleProduct(product, productId) {
        const billIndex = $scope.bills.findIndex(bill => bill.name === product.bill);
        if (billIndex !== -1) {
            $http.get(`/spct/${productId}`).then(response => {
                let newProduct = response.data.data;

                // Tìm sản phẩm khuyến mãi nếu có
                const promoProduct = $scope.listProductPromotion.find(promo => promo.idSanPham === newProduct.idSanPham);

                // Cập nhật thông tin sản phẩm
                newProduct.soLuongMax = newProduct.soLuong;
                newProduct.soLuong = product.quantity;
                newProduct.image = newProduct.sanPham.anh;
                newProduct.tenSanPham = newProduct.sanPham.ten;
                newProduct.color = newProduct.mauSac.ten;
                newProduct.size = newProduct.size.ten;
                newProduct.giaTriGiam = promoProduct ? promoProduct.giaTriGiam : 0;
                newProduct.tenKhuyenMai = promoProduct ? promoProduct.tenKhuyenMai : null;
                newProduct.trangThai = promoProduct ? promoProduct.trangThai : null;

                // Kiểm tra sản phẩm đã tồn tại trong hóa đơn hay chưa
                const existingProductIndex = $scope.bills[billIndex].items.findIndex(item => item.id === newProduct.id);

                if (existingProductIndex === -1) {
                    // Nếu chưa tồn tại, thêm mới sản phẩm
                    $scope.bills[billIndex].items.push(newProduct);
                } else {
                    // Nếu đã tồn tại, cập nhật số lượng
                    const existingProduct = $scope.bills[billIndex].items[existingProductIndex];
                    existingProduct.soLuong += product.quantity; // Tăng số lượng
                    if (existingProduct.soLuong > existingProduct.soLuongMax) {
                        existingProduct.soLuong = existingProduct.soLuongMax; // Đảm bảo không vượt quá số lượng tối đa
                    }
                }

                // Cập nhật số lượng trong danh sách sản phẩm
                const productInList = $scope.listProduct.find(p => p.id === newProduct.id);
                if (productInList) {
                    productInList.soLuong -= product.quantity; // Giảm số lượng sản phẩm trong danh sách
                    if (productInList.soLuong < 0) {
                        productInList.soLuong = 0; // Đảm bảo không âm
                    }
                }

                $scope.updateTotalBill($scope.bills[billIndex]);
            }).catch(error => {
                console.error(`Lỗi khi gọi API cho sản phẩm ID ${productId}:`, error);
            });
        }
    }


    $scope.clearData = function () {
        ref.remove()
    };
    $scope.clearData();

    $http.get("/khuyen-mai").then(resp => {
        if (resp.data.code === '200') {
            $scope.vouchers = resp.data.data
        }
    });
    $http.get('/san-pham').then(resp => {
        if (resp.status === 200) {
            const productList = resp.data.data; // Danh sách sản phẩm
            const requests = productList.map(item =>
                $http.get(`/san-pham/${item.id}`).then(detailResp => {
                    const productDetails = detailResp.data.data.listSanPhamChiTiet;
                    // Thêm thuộc tính `tenSanPham` vào từng mục trong `listSanPhamChiTiet`
                    productDetails.forEach(detail => {
                        detail.tenSanPham = item.ten;
                        detail.image = item.anh;
                        detail.listMauSac = detailResp.data.data.listMauSac;
                        detail.listSize = detailResp.data.data.listSize;
                    });

                    return productDetails;
                })
            );

            // Xử lý tất cả yêu cầu đồng thời với Promise.all
            Promise.all(requests).then(allDetails => {
                $scope.listProduct = allDetails.flat(); // Gộp tất cả chi tiết lại thành một mảng

                console.log("Danh sách sản phẩm chi tiết với tên sản phẩm:", $scope.listProduct);
                $scope.$apply(); // Đảm bảo giao diện được cập nhật
            }).catch(error => {
                console.error("Lỗi khi tải chi tiết sản phẩm:", error);
            });
        } else {
            console.error("Không thể lấy danh sách sản phẩm:", resp);
        }
    }).catch(error => {
        console.error("Lỗi khi gọi API sản phẩm:", error);
    });

    // lưu hóa đơn khi reload
    // var unloadHandler = function (event) {
    //     localStorage.setItem('bills', JSON.stringify($scope.bills))
    // };
    // window.addEventListener('beforeunload', unloadHandler);
    // $scope.$on('$destroy', function () {
    //     window.removeEventListener('beforeunload', unloadHandler);
    // });
    // if (localStorage.getItem('bills')) {
    //     $scope.bills = JSON.parse(localStorage.getItem('bills'));
    // };

    $scope.generateBillName = function (lastName) {
        const alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Nếu không có hóa đơn nào, bắt đầu từ "A"
        if (!lastName) return "A";

        // Lấy phần chữ cái của tên hóa đơn, ví dụ: "Hóa đơn A" -> "A"
        let letters = lastName.replace("Hóa đơn ", "").trim();

        // Chuyển từ chữ cái sang số thứ tự
        let index = 0;
        for (let i = 0; i < letters.length; i++) {
            index = index * 26 + (letters.charCodeAt(i) - "A".charCodeAt(0) + 1);
        }

        // Tăng chỉ số để tạo tên tiếp theo
        index++;

        // Chuyển từ số thứ tự sang chữ cái
        let nextName = "";
        while (index > 0) {
            index--; // Trừ 1 để đảm bảo chỉ số 0-based
            nextName = alphabet[index % 26] + nextName;
            index = Math.floor(index / 26);
        }
        return nextName;

    };
    $scope.addBill = function () {
        // Lấy tên hóa đơn cuối cùng
        const lastBillName = $scope.bills.length > 0
            ? $scope.bills[$scope.bills.length - 1].name
            : null;

        // Tạo tên hóa đơn mới
        const newBillName = $scope.generateBillName(lastBillName);
        let bill = {
            stt: $scope.bills.length + 1,
            name: newBillName,
            totalBill: 0,
            totalBillLast: 0,
            disabled: false,
            totalQuantity: 0,
            nameCustomer: "",
            diemTichLuy: 0,
            billDiem: 0,
            pointsToUse: 0,
            search: "",
            idCustomer: 0,
            phoneCustomer: '',
            moneyCustomer: 0,
            trangThai: 7,
            nguoiTao: 1,
            diemSuDung: 0,
            idDiaChiGiaoHang:0,
            idPhuongThucVanChuyen:0,
            nguoiCapNhat:0,
            payCustomer: 'money',
            payQRbank: null,
            codeBill: null,
            dateBill: null,
            diemThuong: 0,
            totalPricePromo: 0,
            items: []
        };
        $scope.bills.push(bill);
        $scope.activeBill = $scope.bills.length - 1;
    };
    $scope.removeBill = function (index) {
        const billToRemove = $scope.bills[index];

        // Khôi phục số lượng sản phẩm trong danh sách sản phẩm
        if (billToRemove && billToRemove.items) {
            billToRemove.items.forEach(product => {
                const productInList = $scope.listProduct.find(p => p.id === product.id);
                if (productInList) {
                    productInList.soLuong += product.soLuong; // Cộng lại số lượng đã sử dụng
                }
            });
        }

        // Xóa hóa đơn
        $scope.bills.splice(index, 1);

        // Đặt lại hóa đơn đang hoạt động (activeBill)
        if ($scope.activeBill >= $scope.bills.length) {
            $scope.activeBill = $scope.bills.length - 1; // Chuyển activeBill về hóa đơn cuối nếu vượt quá
        }

        // Đảm bảo activeBill không âm
        if ($scope.activeBill < 0) {
            $scope.activeBill = 0;
        }
    };

    $scope.setActiveBill = function (index) {
        $scope.activeBill = index;
    };

    $scope.getTotalAmount = function (bill) {
        bill.totalPricePromo = 0;
        bill.items.forEach(function (item) {
            if (item.trangThai) {
                bill.totalPricePromo += item.giaTriGiam;
            }
        })
        const discount = (100 - (bill.diemThuong || 0)) / 100;
        const totalAfterDiscount = bill.totalBill * discount;
        const pointsDiscount = (bill.diemSuDung || 0) * 10; // 1 điểm = 10 VNĐ
        return Math.max(totalAfterDiscount - pointsDiscount - bill.totalPricePromo, 0); // Tổng tiền không âm
    };

    $scope.updatePointsToUse = function (bill) {
        // Xử lý giá trị null hoặc undefined
        if (bill.diemSuDung == null || isNaN(bill.diemSuDung)) {
            bill.diemSuDung = null; // Gán giá trị mặc định (hoặc xử lý theo logic của bạn)
        }

        // Ràng buộc giá trị trong khoảng hợp lệ
        if (bill.diemSuDung > bill.diemTichLuy) {
            bill.diemSuDung = bill.diemTichLuy;
        } else if (bill.diemSuDung < 0) {
            bill.diemSuDung = 0;
        }

        console.log(bill.diemSuDung);
    };

    $scope.updateTotalBill = function (bill) {
        bill.totalBill = bill.items.reduce(function (total, product) {
            return total + (product.soLuong * product.gia);
        }, 0);
        bill.totalQuantity = bill.items.reduce(function (total, product) {
            return total + product.soLuong;
        }, 0);
    };
    $scope.showListProduct = function () {
        $scope.showPopUp = true;
        setTimeout(() => {
            const searchInput = document.getElementById('searchProduct');
            searchInput.addEventListener('keyup', function() {
                const filter = this.value.toLowerCase();
                const rows = document.querySelectorAll('#tableproductlist tbody tr');
                rows.forEach(row => {
                    const cells = row.getElementsByTagName('td');

                    if (cells.length > 1) {
                        const targetCell = cells[1]; // Chỉ lấy cột thứ 2 (index 1)
                        if (targetCell.textContent.toLowerCase().includes(filter)) {
                            row.style.display = ''; // Hiển thị hàng nếu khớp
                        } else {
                            row.style.display = 'none'; // Ẩn hàng nếu không khớp
                        }
                    }
                });
            });
        }, 200);
    };
    $scope.addProductToBill = function (bill, product) {
        const existingProduct = bill.items.find(item => item.id === product.id);
        const promoProduct = $scope.listProductPromotion.find(promo => promo.idSanPham === product.idSanPham);
        if (existingProduct) {
            existingProduct.soLuong++;
        } else {
            bill.items.push({
                tenSanPham: product.tenSanPham,
                id: product.id,
                idSanPham: product.idSanPham,
                image: product.image,
                gia: product.gia,
                giaTriGiam: promoProduct ? promoProduct.giaTriGiam : 0,
                tenKhuyenMai: promoProduct ? promoProduct.tenKhuyenMai : null,
                trangThai: promoProduct ? promoProduct.trangThai : null,
                ghiChu: product.ghiChu,
                soLuong: 1,
                soLuongMax: product.soLuong,
                idSize: product.idSize,
                idMauSac: product.idMauSac,
                listSize: product.listSize,
                listMauSac: product.listMauSac
            });
        }
        // Giảm số lượng sản phẩm trong danh sách sản phẩm
        const productInList = $scope.listProduct.find(p => p.id === product.id);
        if (productInList) {
            productInList.soLuong--; // Giảm số lượng
        }
        $scope.updateTotalBill(bill);
        $scope.showPopUp = false
    };
    $scope.updateQuantity = function (bill, item) {
        // Chuyển đổi số lượng thành kiểu số và kiểm tra giá trị hợp lệ
        item.soLuong = Number(item.soLuong);
        if (item.soLuong == null || isNaN(item.soLuong)) {
            item.soLuong = 1;
        }

        // Tìm sản phẩm tương ứng trong danh sách
        const productInList = $scope.listProduct.find(p => p.id === item.id);

        if (productInList) {
            // Tính số lượng còn lại trong danh sách sản phẩm
            const remainingQuantity = item.soLuongMax - item.soLuong;

            // Nếu số lượng vượt quá giới hạn, điều chỉnh về mức tối đa
            if (item.soLuong > remainingQuantity) {
                item.soLuong = remainingQuantity;
            }

            // Cập nhật số lượng trong danh sách sản phẩm
            productInList.soLuong = item.soLuongMax - item.soLuong;
        }

        // Tính lại tổng tiền cho sản phẩm
        item.total = item.soLuong * item.gia;

        // Cập nhật tổng hóa đơn
        $scope.updateTotalBill(bill);
    };
    $scope.removeProduct = function (bill, product) {
        const index = bill.items.findIndex(item => item.id === product.id);
        if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này!')) {
            if (index !== -1) {
                // Khôi phục số lượng ban đầu trong danh sách sản phẩm
                const productInList = $scope.listProduct.find(p => p.id === product.id);
                if (productInList) {
                    productInList.soLuong += product.soLuong; // Cộng lại số lượng đã sử dụng
                }

                // Xóa sản phẩm khỏi hóa đơn
                bill.items.splice(index, 1);

                // Cập nhật tổng hóa đơn
                $scope.updateTotalBill(bill);
            } else {
                console.log("Sản phẩm không được tìm thấy trong mảng items.");
            }
        }
    };

    $scope.searchFilter = function(product) {

        if (!$scope.searchQuery) {
            return true; // Trả về tất cả sản phẩm khi không có từ khóa tìm kiếm
        }
        // Kiểm tra nếu tên sản phẩm chứa từ khóa tìm kiếm
        return product.tenSanPham.toLowerCase().includes($scope.searchQuery.toLowerCase());
    };

    $scope.closeProductList = function () {
        $scope.showPopUp = false
    };


    $scope.onPhoneChange = function(bill) {
        let formData = {
            role: "CLIENT",
            phone: bill.phoneCustomer,
        }
        $http.post("/user/get-list", formData).then(resp => {
            if (resp.data.data.length === 1) {
                bill.nameCustomer = resp.data.data[0].profile.hoVaTen;
                bill.idCustomer = resp.data.data[0].id;
                let dataDiem = {
                    idNguoiDung: bill.idCustomer,
                    diem: 0
                };
                let params = new URLSearchParams();
                for (let key in dataDiem) {
                    params.append(key, dataDiem[key]);
                }
                $http({
                    method: 'PUT',
                    url: '/api/diem-tich-luy/update',
                    data: params.toString(),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(response => {
                    if (response.status === 200) {
                        bill.diemTichLuy = response.data.diem
                    }

                });
            } else if (resp.data.data.length === 0)  {
                bill.nameCustomer = '';
                bill.diemTichLuy = 0;
                $scope.onKeyDownName = function(bill) {
                    if (event.keyCode === 13) {
                        let dataUser ={
                            email: `ziaza${bill.phoneCustomer}@gmail.com`,
                            name: bill.nameCustomer,
                            phone: bill.phoneCustomer,
                            password: "Ziaza@123",
                            retypePassword: "Ziaza@123",
                            ngaySinh: "2000-12-11T17:00:00.000Z",
                        }
                        $http.post("/register", dataUser).then(res => {
                            if (res.data.code === '200') {
                                var toastLiveExample = document.getElementById('liveToast')
                                var toast = new bootstrap.Toast(toastLiveExample)
                                toast.show();
                                $http.post("/user/get-list", formData).then(response => {
                                    if (response.data.data.length === 1) {
                                        bill.idCustomer = response.data.data[0].id;
                                    }
                                })
                            }
                        })
                    }
                };
            }
        });
        if (bill.phoneCustomer === '' || bill.phoneCustomer === null || bill.phoneCustomer === undefined) {
            bill.nameCustomer = '';
        }
    };
    $scope.formatCurrency = function (amount) {
        const formatter = new Intl.NumberFormat('vi-VN');
        return formatter.format(amount);
    };
    $scope.onChangePayment = function(bill) {
        if (bill.payCustomer === 'money') {
            console.log("Chọn phương thức thanh toán: Tiền mặt");
            // Thực hiện các hành động khác nếu cần khi chọn Tiền mặt
        } else if (bill.payCustomer === 'bank') {
            const AMOUNT = $scope.getTotalAmount(bill);
            const DESCRIPTION = encodeURIComponent('Thanh toan QR tai quay');
            const ACCOUNT_NAME = encodeURIComponent('NGUYEN THI THANH PHUONG');
            let QR_URL = `https://img.vietqr.io/image/970422-3006200466-compact.png?amount=${AMOUNT}&addInfo=${DESCRIPTION}&accountName=${ACCOUNT_NAME}`;
            bill.payQRbank = QR_URL;
        }
    };
    $scope.calculatePoints = function (bill) {
        return Math.floor(bill.totalBillLast / 1000); // Tính điểm tích lũy
    };
    $scope.payBill = function (bill) {

        bill.totalBillLast = $scope.getTotalAmount(bill)
        bill.billDiem = Math.floor(bill.totalBillLast / 1000)

        $http.post("/api/hoa-don/thanh-toan", bill).then(resp => {
            if (resp.status === 200) {
                bill.disabled = true;
                bill.codeBill = resp.data.ma;
                bill.dateBill = resp.data.ngayThanhToan;
                const billModal = new bootstrap.Modal(document.getElementById(`paymentModal-${bill.name}`), {
                    keyboard: false
                });
                let dataDiemUse = {
                    idNguoiDung: bill.idCustomer,
                    diemCanSuDung: bill.diemSuDung
                };
                let paramsUse = new URLSearchParams();
                for (let key in dataDiemUse) {
                    paramsUse.append(key, dataDiemUse[key]);
                }

                let dataDiem = {
                    idNguoiDung: bill.idCustomer,
                    diem: $scope.calculatePoints(bill)
                };
                let params = new URLSearchParams();
                for (let key in dataDiem) {
                    params.append(key, dataDiem[key]);
                }

                $http({
                    method: 'PUT',
                    url: '/api/diem-tich-luy/use-diem',
                    data: paramsUse.toString(),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then(response => {
                    console.log(response)
                    if (response.status === 200) {
                        $http({
                            method: 'PUT',
                            url: '/api/diem-tich-luy/update',
                            data: params.toString(),
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            }
                        }).then(res => {
                            if (res.status === 200) {
                                bill.diemTichLuy = res.data.diem
                            }
                        });

                    }
                }).catch(function (error) {
                    if (error.status === 500) {
                        $http({
                            method: 'PUT',
                            url: '/api/diem-tich-luy/update',
                            data: params.toString(),
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            }
                        }).then(res => {
                            if (res.status === 200) {
                                bill.diemTichLuy = res.data.diem
                            }
                        });

                    }
                });


                billModal.show();
            }
        });

    };
    $scope.clearBill = function (bill, index) {
        $scope.bills.splice(index, 1);
        // Đặt lại hóa đơn đang hoạt động (activeBill)
        if ($scope.activeBill >= $scope.bills.length) {
            $scope.activeBill = $scope.bills.length - 1; // Chuyển activeBill về hóa đơn cuối nếu vượt quá
        }
    };
    $scope.printBill = function (bill, index) {
        $scope.clearBill(bill, index);
        var innerContents = document.getElementById(`paymentModal-${bill.name}`).innerHTML;
        var popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
        popupWinindow.document.open();
        popupWinindow.document.write(`
        <html>
            <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>In hóa đơn</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 20px;
                }
                .modal-header, .modal-footer {
                    display: none;
                }
            </style>
            </head>
            <body onload="window.print()"> ${innerContents}  </html>`
        );
        popupWinindow.document.close();
    };


});
