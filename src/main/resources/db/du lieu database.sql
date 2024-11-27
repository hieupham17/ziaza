

--- data sql db.datn -----
INSERT INTO [chuc_nang] ([ten], [ma], [id_cha], [loai], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'Chức năng 1', N'MA001', 0, 1, 1, 1, 1),
(N'Chức năng 2', N'MA002', 1, 1, 1, 1, 1),
(N'Chức năng 3', N'MA003', 0, 2, 1, 1, 1),
(N'Chức năng 4', N'MA004', 2, 1, 1, 1, 1),
(N'Chức năng 5', N'MA005', 1, 2, 1, 1, 1);
GO

INSERT INTO [nhom_chuc_nang] ([id_nhom], [id_chuc_nang], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, 1, 1, 1, 1),
(1, 2, 1, 1, 1),
(2, 3, 1, 1, 1),
(2, 4, 1, 1, 1),
(3, 5, 1, 1, 1);
GO

INSERT INTO [nhom] ([ten], [mo_ta], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'Nhom 1', N'Mo ta Nhom 1', 1, 1, 1),
(N'Nhom 2', N'Mo ta Nhom 2', 1, 1, 1),
(N'Nhom 3', N'Mo ta Nhom 3', 1, 1, 1),
(N'Nhom 4', N'Mo ta Nhom 4', 1, 1, 1),
(N'Nhom 5', N'Mo ta Nhom 5', 1, 1, 1);
GO

INSERT INTO [nhom_nguoi_dung] ([id_nhom], [id_nguoi_dung], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, 1, 1, GETDATE(), GETDATE(), 1, 1),
(1, 2, 1, GETDATE(), GETDATE(), 1, 1),
(2, 3, 1, GETDATE(), GETDATE(), 1, 1),
(2, 4, 1, GETDATE(), GETDATE(), 1, 1),
(3, 5, 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [nguoi_dung] ([ten_dang_nhap], [mat_khau], [vaitro], [loai], [xac_thuc], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'nguoidung1', N'password1', N'USER', N'FB', 1, N'ACTIVE', GETDATE(), GETDATE(), 1, 1),
(N'nguoidung2', N'password2', N'ADMIN', N'GG', 1, N'LOCKED', GETDATE(), GETDATE(), 1, 1),
(N'nguoidung3', N'password3', N'USER', N'NORMAL', 1, N'ACTIVE', GETDATE(), GETDATE(), 1, 1),
(N'nguoidung4', N'password4', N'CLIENT', N'FB', 1, N'LOCKED', GETDATE(), GETDATE(), 1, 1),
(N'nguoidung5', N'password5', N'USER', N'GG', 1, N'ACTIVE', GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [thong_tin_ca_nhan] ([id_nguoi_dung], [ho_va_ten], [sdt], [avatar], [dia_chi], [gioi_tinh], [ngay_sinh], [cccd], [email], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, N'Nguyễn Văn A', N'0123456789', N'avatar1.jpg', N'Số 1, Đường ABC, Quận 1', N'NAM', '1990-01-01', N'123456789', N'nguyenvana@example.com', GETDATE(), GETDATE(), 1, 1),
(2, N'Nguyễn Thị B', N'0123456790', N'avatar2.jpg', N'Số 2, Đường XYZ, Quận 2', N'NU', '1992-05-05', N'234567890', N'nguyenb@example.com', GETDATE(), GETDATE(), 1, 1),
(3, N'Phạm Minh C', N'0123456791', N'avatar3.jpg', N'Số 3, Đường PQR, Quận 3', N'NAM', '1993-07-15', N'345678901', N'phamminhc@example.com', GETDATE(), GETDATE(), 1, 1),
(4, N'Nguyễn Thị D', N'0123456792', N'avatar4.jpg', N'Số 4, Đường STU, Quận 4', N'NU', '1988-10-20', N'456789012', N'nguyend@example.com', GETDATE(), GETDATE(), 1, 1),
(5, N'Phạm Văn E', N'0123456793', N'avatar5.jpg', N'Số 5, Đường VWX, Quận 5', N'NAM', '1995-12-25', N'567890123', N'phamvane@example.com', GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [dia_chi_giao_hang] ([id_nguoi_dung], [ho_va_ten], [sdt], [dia_chi], [thanh_pho], [quoc_gia], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, N'Nguyễn Văn A', N'0123456789', N'Số 1, Đường ABC, Quận 1', N'Hà Nội', N'Việt Nam', 1, GETDATE(), GETDATE(), 1, 1),
(2, N'Nguyễn Thị B', N'0123456790', N'Số 2, Đường XYZ, Quận 2', N'Hồ Chí Minh', N'Việt Nam', 1, GETDATE(), GETDATE(), 1, 1),
(3, N'Phạm Minh C', N'0123456791', N'Số 3, Đường PQR, Quận 3', N'Đà Nẵng', N'Việt Nam', 1, GETDATE(), GETDATE(), 1, 1),
(4, N'Nguyễn Thị D', N'0123456792', N'Số 4, Đường STU, Quận 4', N'Hải Phòng', N'Việt Nam', 1, GETDATE(), GETDATE(), 1, 1),
(5, N'Phạm Văn E', N'0123456793', N'Số 5, Đường VWX, Quận 5', N'Bình Dương', N'Việt Nam', 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [phan_hoi] ([id_ho_tro], [nhan_xet], [diem], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, N'Rất hài lòng về dịch vụ', 5, 1, GETDATE(), GETDATE(), 1, 1),
(2, N'Sản phẩm tốt nhưng giao hàng hơi chậm', 4, 1, GETDATE(), GETDATE(), 1, 1),
(3, N'Chất lượng sản phẩm không như mong đợi', 2, 1, GETDATE(), GETDATE(), 1, 1),
(4, N'Giá cả hợp lý, dịch vụ tốt', 5, 1, GETDATE(), GETDATE(), 1, 1),
(5, N'Khá hài lòng, sẽ mua tiếp', 4, 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [ho_tro] ([id_nguoi_dung], [yeu_cau], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, N'Yêu cầu hỗ trợ về sản phẩm', 1, GETDATE(), GETDATE(), 1, 1),
(2, N'Yêu cầu đổi trả sản phẩm', 1, GETDATE(), GETDATE(), 1, 1),
(3, N'Yêu cầu tư vấn về dịch vụ', 1, GETDATE(), GETDATE(), 1, 1),
(4, N'Yêu cầu hỗ trợ kỹ thuật', 1, GETDATE(), GETDATE(), 1, 1),
(5, N'Yêu cầu hướng dẫn sử dụng', 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [lich_su_ho_tro] ([id_ho_tro], [id_nguoi_dung], [noi_dung], [ngay_phan_hoi], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, 1, N'Hỗ trợ sản phẩm bị lỗi', GETDATE(), 1, GETDATE(), GETDATE(), 1, 1),
(2, 2, N'Đã đổi sản phẩm cho khách', GETDATE(), 1, GETDATE(), GETDATE(), 1, 1),
(3, 3, N'Giải đáp về dịch vụ chăm sóc khách hàng', GETDATE(), 1, GETDATE(), GETDATE(), 1, 1),
(4, 4, N'Khắc phục sự cố kỹ thuật nhanh chóng', GETDATE(), 1, GETDATE(), GETDATE(), 1, 1),
(5, 5, N'Hướng dẫn sử dụng sản phẩm', GETDATE(), 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [diem_tich_luy] ([id_nguoi_dung], [diem], [ly_do], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, 100, N'Mua sắm lần đầu', 1, GETDATE(), GETDATE(), 1, 1),
(2, 200, N'Giới thiệu bạn bè', 1, GETDATE(), GETDATE(), 1, 1),
(3, 150, N'Mua sắm trong tháng', 1, GETDATE(), GETDATE(), 1, 1),
(4, 50, N'Thưởng khi đánh giá sản phẩm', 1, GETDATE(), GETDATE(), 1, 1),
(5, 300, N'Mua sắm nhiều lần', 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [phuong_thuc_thanh_toan] ([ten], [loai], [mo_ta], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'Tiền mặt', N'CASH', N'Thanh toán bằng tiền mặt', 1, GETDATE(), GETDATE(), 1, 1),
(N'VNPAY', N'VNPAY', N'Thanh toán qua cổng VNPAY', 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [thanh_toan] ([id_hoa_don], [id_phuong_thuc_thanh_toan], [ma_giao_dich], [so_tien], [ngay_thanh_toan], [ghi_chu], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, 1, N'GD123456', 150000, GETDATE(), N'Thanh toán thành công', 1, GETDATE(), GETDATE(), 1, 1),
(2, 2, N'GD123457', 200000, GETDATE(), N'Thanh toán qua VNPAY', 1, GETDATE(), GETDATE(), 1, 1),
(3, 1, N'GD123458', 50000, GETDATE(), N'Thanh toán bằng tiền mặt', 0, GETDATE(), GETDATE(), 1, 1),
(4, 2, N'GD123459', 100000, GETDATE(), N'VNPAY đang xử lý', 3, GETDATE(), GETDATE(), 1, 1),
(5, 1, N'GD123460', 75000, GETDATE(), N'Hoàn tiền do lỗi', 4, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [phuong_thuc_van_chuyen] ([ten], [mo_ta], [phi_van_chuyen], [loai], [ghi_chu], [thoi_gian_giao_hang], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'Vận chuyển qua bưu điện', N'Dịch vụ vận chuyển qua bưu điện, giao hàng nhanh chóng và tiện lợi.', 30000, 1, N'Phí vận chuyển tùy theo địa chỉ giao hàng', N'2-3 ngày', 1, GETDATE(), GETDATE(), 1, 1),
(N'Vận chuyển nội địa', N'Dịch vụ vận chuyển qua xe tải, chuyển hàng nội địa trong cùng thành phố.', 50000, 2, N'Phí vận chuyển tính theo khoảng cách và trọng lượng.', N'1-2 ngày', 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [hoa_don] ([id_nguoi_dung], [ma], [id_dia_chi_giao_hang], [id_phuong_thuc_van_chuyen], [ngay_dat_hang], [ngay_thanh_toan], [tong_tien], [diem_su_dung], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, N'HĐ12383', 1, 1, GETDATE(), GETDATE(), 500000, 0, 0, GETDATE(), GETDATE(), 1, 1),
(2, N'HĐ12346', 2, 2, GETDATE(), GETDATE(), 1000000, 100, 1, GETDATE(), GETDATE(), 1, 1),
(3, N'HĐ12347', 3, 1, GETDATE(), GETDATE(), 200000, 0, 2, GETDATE(), GETDATE(), 1, 1),
(4, N'HĐ12348', 4, 2, GETDATE(), GETDATE(), 700000, 50, 3, GETDATE(), GETDATE(), 1, 1),
(5, N'HĐ12349', 5, 1, GETDATE(), GETDATE(), 800000, 0, 4, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [chi_tiet_hoa_don] ([id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(4, 1, 2, 200000, 1, GETDATE(), GETDATE(), 1, 1),
(2, 2, 5, 200000, 1, GETDATE(), GETDATE(), 1, 1),
(3, 3, 1, 200000, 1, GETDATE(), GETDATE(), 1, 1),
(4, 4, 3, 150000, 1, GETDATE(), GETDATE(), 1, 1),
(5, 5, 4, 200000, 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [gio_hang] ([id_nguoi_dung], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, 0, GETDATE(), GETDATE(), 1, 1), 
(2, 1, GETDATE(), GETDATE(), 1, 1),  
(3, 2, GETDATE(), GETDATE(), 1, 1),  
(4, 3, GETDATE(), GETDATE(), 1, 1),  
(5, 4, GETDATE(), GETDATE(), 1, 1);  
GO

INSERT INTO [chi_tiet_gio_hang] ([id_gio_hang], [id_san_pham_chi_tiet], [so_luong], [gia], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(1, 1, 2, 200000, 1, GETDATE(), GETDATE(), 1, 1),  
(2, 2, 3, 300000, 1, GETDATE(), GETDATE(), 1, 1), 
(3, 3, 1, 150000, 1, GETDATE(), GETDATE(), 1, 1),  
(4, 4, 4, 500000, 1, GETDATE(), GETDATE(), 1, 1), 
(5, 5, 2, 1000000, 1, GETDATE(), GETDATE(), 1, 1);  
GO

INSERT INTO [chat_lieu] ([ten], [id_danh_muc_cha], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'Cotton', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'Jean', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'Len', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'Thể thao', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'Vải dạ', 1, 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [mau_sac] ([ten], [id_danh_muc_cha], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'Đỏ', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'Xanh', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'Đen', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'Trắng', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'Xám', 1, 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [size] ([ten], [id_danh_muc_cha], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'S', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'M', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'L', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'XL', 1, 1, GETDATE(), GETDATE(), 1, 1),
(N'XXL', 1, 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [san_pham] 
([id_danh_muc], [id_thuong_hieu], [id_chat_lieu], [ten], [ma], [xuat_xu], [mo_ta], [gia], [anh], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(1, 1, 1, N'Áo thun nam', N'ATN001', N'Trung Quốc', N'Áo thun nam chất lượng cao', 400000, N'path_to_image1.jpg', 1, GETDATE(), GETDATE(), 1, 1),
(1, 1, 2, N'Quần jean nam', N'QJN001', N'Việt Nam', N'Quần jean nam, bền và thời trang', 200000, N'path_to_image2.jpg', 1, GETDATE(), GETDATE(), 1, 1),
(2, 2, 1, N'Áo sơ mi nữ', N'ASN001', N'Việt Nam', N'Áo sơ mi nữ thanh lịch, phù hợp với công sở', 350000, N'path_to_image3.jpg', 1, GETDATE(), GETDATE(), 1, 1),
(2, 3, 2, N'Áo khoác nam', N'AKN001', N'Hàn Quốc', N'Áo khoác nam dày, ấm áp cho mùa đông', 500000, N'path_to_image4.jpg', 1, GETDATE(), GETDATE(), 1, 1),
(3, 1, 3, N'Váy đầm nữ', N'VDN001', N'Thái Lan', N'Váy đầm nữ đẹp, nhẹ nhàng cho mùa hè', 600000, N'path_to_image5.jpg', 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [chi_tiet_san_pham] 
([id_san_pham], [id_size], [id_mau_sac], [so_luong], [gia], [ghi_chu], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(1, 1, 1, 500, 400000, N'Áo thun màu đỏ, kích thước S', 1, GETDATE(), GETDATE(), 1, 1),
(1, 2, 2, 300, 200000, N'Áo thun màu xanh, kích thước M', 1, GETDATE(), GETDATE(), 1, 1),
(2, 3, 3, 400, 350000, N'Quần jean màu đen, kích thước L', 1, GETDATE(), GETDATE(), 1, 1),
(3, 2, 4, 200, 500000, N'Áo sơ mi nữ màu trắng, kích thước M', 1, GETDATE(), GETDATE(), 1, 1),
(4, 3, 5, 100, 600000, N'Áo khoác nam màu xanh, kích thước XL', 1, GETDATE(), GETDATE(), 1, 1);
GO

INSERT INTO [thuong_hieu] ([ten], [mo_ta], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(N'Nike', N'Chuyên sản xuất giày thể thao và quần áo thể thao', 1, 1, 1),
(N'Adidas', N'Chuyên cung cấp các sản phẩm thể thao cao cấp', 1, 1, 1),
(N'Reebok', N'Cung cấp giày và thiết bị thể thao', 1, 1, 1),
(N'Puma', N'Thương hiệu thể thao nổi tiếng với thiết kế sáng tạo', 1, 1, 1),
(N'Under Armour', N'Chuyên sản xuất đồ thể thao chất lượng cao', 1, 1, 1);
GO

INSERT INTO [danh_muc] ([id_danh_muc_cha], [ten], [mo_ta], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(1, N'Giày thể thao', N'Danh mục giày thể thao', 1, 1, 1),
(1, N'Thời trang nam', N'Danh mục quần áo nam', 1, 1, 1),
(2, N'Thời trang nữ', N'Danh mục quần áo nữ', 1, 1, 1),
(2, N'Trẻ em', N'Danh mục dành cho trẻ em', 1, 1, 1),
(3, N'Phụ kiện', N'Danh mục phụ kiện thời trang', 1, 1, 1);
GO

INSERT INTO [san_pham_yeu_thich] ([id_san_pham], [id_nguoi_dung], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(1, 1, 1, 1, 1),
(2, 1, 1, 1, 1),
(3, 2, 1, 1, 1),
(4, 3, 1, 1, 1),
(5, 4, 1, 1, 1);
GO

INSERT INTO [danh_gia] ([id_san_pham_chi_tiet], [id_nguoi_dung], [id_hoa_don], [danh_gia], [nhan_xet], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(1, 1, 1, 5, N'Sản phẩm tuyệt vời, tôi rất hài lòng!', 1, 1, 1),
(2, 2, 2, 4, N'Chất lượng tốt, nhưng màu sắc chưa đúng với ảnh', 1, 1, 1),
(3, 3, 3, 3, N'Sản phẩm ổn, nhưng kích thước hơi nhỏ', 1, 1, 1),
(4, 4, 4, 2, N'Chất liệu không như mong đợi', 1, 1, 1),
(5, 5, 5, 5, N'Giá trị tuyệt vời cho sản phẩm, sẽ mua lại!', 1, 1, 1);
GO

INSERT INTO [khuyen_mai] ([ten], [ma], [mo_ta], [loai], [gia_tri], [ngay_bat_dau], [ngay_ket_thuc], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(N'Giảm giá mùa thu', N'KM001', N'Miễn phí vận chuyển cho đơn hàng trên 500k', 1, 50.00, '2024-11-01', '2024-11-30', 1, 1, 1),
(N'Khuyến mãi Black Friday', N'KM002', N'Tặng 20% cho các sản phẩm trong danh mục', 2, 20.00, '2024-11-20', '2024-11-25', 1, 1, 1),
(N'Tặng quà cho khách hàng mới', N'KM003', N'Tặng quà cho đơn hàng đầu tiên', 3, 100.00, '2024-11-10', '2024-11-15', 1, 1, 1),
(N'Mua 1 tặng 1', N'KM004',N'Mua 1 sản phẩm tặng 1 sản phẩm khác', 1, 0.00, '2024-12-01', '2024-12-10', 1, 1, 1),
(N'Tặng voucher 10%', N'KM005', N'Tặng voucher giảm giá cho lần mua tiếp theo', 3, 10.00, '2024-11-05', '2024-11-10', 1, 1, 1);
GO

INSERT INTO [ap_dung_khuyen_mai] ([id_khuyen_mai], [id_san_pham], [id_nguoi_dung],
[gia_tri_giam], [ngay_ap_dung], [trang_thai], [da_su_dung], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(1, 1, 1, 50.00, '2024-11-01', 1, 0, 1, 1),
(2, 2, 2, 20.00, '2024-11-20', 1, 0, 1, 1),
(3, 3, 3, 100.00, '2024-11-10', 1, 0, 1, 1),
(4, 4, 4, 0.00, '2024-12-01', 1, 0, 1, 1),
(5, 5, 5, 10.00, '2024-11-05', 1, 0, 1, 1);
GO

INSERT INTO [blog] ([tac_gia], [title], [content], [hinh_anh], [content_2], [hinh_anh_2], [content_3], [hinh_anh_3], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(N'Tác giả 1', N'Tiêu đề Blog 1', N'Nội dung Blog 1', N'images/blog1.jpg', N'Nội dung phụ 1', N'images/blog1_2.jpg', N'Nội dung phụ 2', N'images/blog1_3.jpg', 1, 1, 1),
(N'Tác giả 2', N'Tiêu đề Blog 2', N'Nội dung Blog 2', N'images/blog2.jpg', N'Nội dung phụ 1', N'images/blog2_2.jpg', N'Nội dung phụ 2', N'images/blog2_3.jpg', 1, 1, 1),
(N'Tác giả 3', N'Tiêu đề Blog 3', N'Nội dung Blog 3', N'images/blog3.jpg', N'Nội dung phụ 1', N'images/blog3_2.jpg', N'Nội dung phụ 2', N'images/blog3_3.jpg', 1, 1, 1),
(N'Tác giả 4', N'Tiêu đề Blog 4', N'Nội dung Blog 4', N'images/blog4.jpg', N'Nội dung phụ 1', N'images/blog4_2.jpg', N'Nội dung phụ 2', N'images/blog4_3.jpg', 1, 1, 1),
(N'Tác giả 5', N'Tiêu đề Blog 5', N'Nội dung Blog 5', N'images/blog5.jpg', N'Nội dung phụ 1', N'images/blog5_2.jpg', N'Nội dung phụ 2', N'images/blog5_3.jpg', 1, 1, 1);
GO

INSERT INTO [binh_luan] ([id_blog], [id_nguoi_dung], [content], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(1, 1, N'Bình luận 1 cho Blog 1', 1, 1, 1),
(1, 2, N'Bình luận 2 cho Blog 1', 1, 1, 1),
(2, 1, N'Bình luận 1 cho Blog 2', 1, 1, 1),
(3, 3, N'Bình luận 1 cho Blog 3', 1, 1, 1),
(4, 2, N'Bình luận 1 cho Blog 4', 1, 1, 1);
GO

INSERT INTO [rep_binh_luan] ([id_comment], [id_nguoi_dung], [content], [trang_thai], [nguoi_tao], [nguoi_cap_nhat])
VALUES
(1, 2, N'Phản hồi Bình luận 1.1', 1, 1, 1),
(2, 1, N'Phản hồi Bình luận 2.1', 1, 1, 1),
(3, 1, N'Phản hồi Bình luận 3.1', 1, 1, 1),
(4, 3, N'Phản hồi Bình luận 4.1', 1, 1, 1),
(5, 2, N'Phản hồi Bình luận 5.1', 1, 1, 1);
GO

INSERT INTO yeu_cau_doi_tra (id_hoa_don, id_nguoi_dung, loai_yeu_cau, ly_do, trang_thai, ngay_yeu_cau, ngay_hoan_tat, ghi_chu, nguoi_tao, nguoi_cap_nhat, ngay_tao, ngay_cap_nhat)
VALUES
(1, 1, 'EXCHANGE', N'Sản phẩm lỗi, cần đổi mới', 0, GETDATE(), NULL, N'Đang chờ xử lý', 1, 1, GETDATE(), GETDATE()),
(2, 1, 'EXCHANGE', N'Sản phẩm không đúng màu sắc', 0, GETDATE(), NULL, N'Đang xử lý', 2, 2, GETDATE(), GETDATE()),
(3, 1, 'RETURN', N'Sản phẩm không vừa size', 0, GETDATE(), NULL, N'Đã hoàn tất', 3, 3, GETDATE(), GETDATE()),
(4, 1, 'RETURN', N'Sản phẩm bị hỏng trong quá trình giao hàng', 0, GETDATE(), NULL, N'Đang xử lý', 4, 4, GETDATE(), GETDATE()),
(5, 1, 'EXCHANGE', N'Sản phẩm có lỗi nhỏ, muốn đổi sản phẩm khác', 0, GETDATE(), NULL, N'Chờ phê duyệt', 5, 5, GETDATE(), GETDATE());

INSERT INTO yeu_cau_doi_tra_chi_tiet (id_yeu_cau_doi_tra, id_san_pham_chi_tiet, so_luong, trang_thai, ngay_tao, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat)
VALUES
(1, 23, 1, 0, GETDATE(), GETDATE(), 1, 1),
(2, 23, 1, 0, GETDATE(), GETDATE(), 1, 1),
(3, 2, 1, 0, GETDATE(), GETDATE(), 1, 1),
(4, 14, 1, 0, GETDATE(), GETDATE(), 1, 1),
(5, 1, 1, 0, GETDATE(), GETDATE(), 1, 1);

update yeu_cau_doi_tra_chi_tiet set trang_thai = 0
select * from yeu_cau_doi_tra_chi_tiet
select * from yeu_cau_doi_tra
select * from san_pham_doi_tra
select * from thong_tin_ca_nhan
select * from gio_hang
--select * from danh_muc 
select * from size where id =1 
select * from mau_sac 
select * from chat_lieu 
--select * from chuc_nang 
--select * from nhom 
--select * from nhom_chuc_nang 
--select * from chi_tiet_san_pham 
--select * from hinh_anh 
select * from khuyen_mai 
select * from ap_dung_khuyen_mai 
select * from chi_tiet_hoa_don 
select * from hoa_don 
select * from nguoi_dung 
select * from phuong_thuc_van_chuyen
select * from chi_tiet_gio_hang
INSERT INTO [chi_tiet_hoa_don] ([id_hoa_don], [id_san_pham_chi_tiet], [so_luong], [gia], [trang_thai], [ngay_tao], [ngay_cap_nhat], [nguoi_tao], [nguoi_cap_nhat])
VALUES 
(7, 1, 2, 200000, 1, GETDATE(), GETDATE(), 1, 1),
(7, 2, 5, 200000, 1, GETDATE(), GETDATE(), 1, 1),
(7, 1, 1, 200000, 1, GETDATE(), GETDATE(), 1, 1),
(7, 2, 3, 150000, 1, GETDATE(), GETDATE(), 1, 1),
(7, 1, 4, 200000, 1, GETDATE(), GETDATE(), 1, 1);
GO


SELECT * FROM [san_pham];
SELECT * FROM [chi_tiet_san_pham];
SELECT * FROM [thuong_hieu];
SELECT * FROM [danh_muc];
SELECT * FROM [san_pham_yeu_thich];
SELECT * FROM [danh_gia];
SELECT * FROM [khuyen_mai];
SELECT * FROM [ap_dung_khuyen_mai];
SELECT * FROM [hinh_anh];
SELECT * FROM [yeu_cau_doi_tra];
SELECT * FROM [yeu_cau_doi_tra_chi_tiet];
SELECT * FROM [blog];
SELECT * FROM [binh_luan];
SELECT * FROM [rep_binh_luan];
select * from nhom
select * from chi_tiet_hoa_don 
select * from hoa_don 

update  danh_muc set ngay_tao = GETDATE() where id =1
update  nguoi_dung set mat_khau = 'Chuchu11@' where id =1
update  hoa_don  set id_phuong_thuc_van_chuyen = 1 where id = 12

DELETE FROM hoa_don WHERE id = 10;

SELECT * FROM chi_tiet_hoa_don  WHERE id_hoa_don=1

drop database [DB.DATN]
SELECT * FROM danh_muc WHERE id = 1; -- hoặc điều kiện khác
SELECT * FROM hinh_anh
INSERT INTO hinh_anh (id_san_pham, anh, trang_thai, ngay_tao, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat)
VALUES 
(3,'meme.png',1, '2024-10-20', '2024-10-20', 1, 1),
(3,'mangto.png',1, '2024-10-20', '2024-10-20', 1, 1),
(3,'avatar.png',1, '2024-10-20', '2024-10-20', 1, 1)

