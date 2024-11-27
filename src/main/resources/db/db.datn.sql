CREATE DATABASE [DB.DATN]
GO
USE [DB.DATN]
GO

----DROP DATABASE [DB.DATN]
--- Chuc nang
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [chuc_nang]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(100),
    [ma] NVARCHAR(255),
    [id_cha] BIGINT,
    [loai] INT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Nhom Chuc nang
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [nhom_chuc_nang]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_nhom] BIGINT,
    [id_chuc_nang] BIGINT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Nhom
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [nhom]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(200),
    [mo_ta] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Nhom nguoi dung
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE nhom_nguoi_dung
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_nhom] BIGINT,
    [id_nguoi_dung] BIGINT,
    [trang_thai] INT,
    [ngay_tao] DATETIME,
    [ngay_cap_nhat] DATETIME,
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
)ON [PRIMARY];
GO

--- Nguoi dung
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [nguoi_dung]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten_dang_nhap] NVARCHAR(200),
    [mat_khau] NVARCHAR(200) ,
    [vaitro] NVARCHAR(50),
    [loai] NVARCHAR(50),
    [xac_thuc] BIT,
    [trang_thai] NVARCHAR(50),
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Thong tin nguoi dung
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [thong_tin_ca_nhan]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_nguoi_dung] BIGINT,
    [ho_va_ten] NVARCHAR(200),
    [sdt] NVARCHAR(20),
    [avatar] NVARCHAR(255),
    [dia_chi] NVARCHAR(255),
    [gioi_tinh] NVARCHAR(10),
    [ngay_sinh] DATETIME,
    [cccd] NVARCHAR(20),
    [email] NVARCHAR(200),
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- OTP
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE otp
(
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    code CHAR(8),
    receiver VARCHAR(255),
    type VARCHAR(15),
    expired DATETIME,
    created DATETIME DEFAULT GETDATE(),
    modified DATETIME DEFAULT GETDATE()
);
GO

--- OTP
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE otp_retry
(
    id VARCHAR(255) NOT NULL,
    times BIGINT,
    expired DATETIME,
    created DATETIME DEFAULT GETDATE(),
    modified DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (id)
);

GO

--- Dia chi giao hang
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dia_chi_giao_hang]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_nguoi_dung] BIGINT,
    [ho_va_ten] NVARCHAR(200),
    [sdt] NVARCHAR(20),
    [dia_chi] NVARCHAR(MAX),
    [thanh_pho] NVARCHAR(200),
    [quoc_gia] NVARCHAR(100),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Phan hoi khach hang
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [phan_hoi]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_ho_tro] BIGINT,
    [nhan_xet] NVARCHAR(250),
    [diem] INT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Ho tro
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ho_tro]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_nguoi_dung] BIGINT,
    [yeu_cau] NVARCHAR(250) ,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- lich su Ho tro
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lich_su_ho_tro]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_ho_tro] BIGINT,
    [id_nguoi_dung] BIGINT,
    [noi_dung] NVARCHAR(250),
    [ngay_phan_hoi] DATETIME DEFAULT GETDATE(),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Diem tich luy
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [diem_tich_luy]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_nguoi_dung] BIGINT,
    [diem] INT,
    [ly_do] NVARCHAR(250),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- phuong thuc thanh toan
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [phuong_thuc_thanh_toan]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(100),
    [loai] NVARCHAR(50),
    [mo_ta] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- thanh toan
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [thanh_toan]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_hoa_don] BIGINT,
    [id_phuong_thuc_thanh_toan] BIGINT,
    [ma_giao_dich] NVARCHAR(100),
    [so_tien] DECIMAL(10,2),
    [ngay_thanh_toan] DATETIME DEFAULT GETDATE(),
    [ghi_chu] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- phuong thuc van chuyen
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [phuong_thuc_van_chuyen]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(200),
    [mo_ta] NVARCHAR(500),
    [phi_van_chuyen] DECIMAL(10,2),
    [loai] INT,
    [ghi_chu] NVARCHAR(250),
    [thoi_gian_giao_hang] NVARCHAR(100),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Hoa don
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hoa_don]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_nguoi_dung] BIGINT,
    [ma] NVARCHAR(50),
    [id_dia_chi_giao_hang] BIGINT,
    [id_phuong_thuc_van_chuyen] BIGINT,
    [ngay_dat_hang] DATETIME DEFAULT GETDATE(),
    [ngay_thanh_toan] DATETIME DEFAULT GETDATE(),
    [tong_tien] DECIMAL(10,2),
    [diem_su_dung] INT,
    [ly_do_huy] NVARCHAR(300),
    [trang_thai] INT DEFAULT 1,
    [trang_thai_doi_tra] INT ,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- chi tiet hoa don

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [chi_tiet_hoa_don]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_hoa_don] BIGINT,
    [id_san_pham_chi_tiet] BIGINT,
    [so_luong] INT,
    [gia] DECIMAL(10,2),
    [trang_thai] INT DEFAULT 1,
    [trang_thai_doi_tra] INT ,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Gio hang
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [gio_hang]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_nguoi_dung] BIGINT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- chi tiet gio hang
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [chi_tiet_gio_hang]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_gio_hang] BIGINT,
    [id_san_pham_chi_tiet] BIGINT,
    [so_luong] INT,
    [gia] DECIMAL(10,2),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Size
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [size]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(100),
    [id_danh_muc_cha] BIGINT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Mau sac
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [mau_sac]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(100),
    [id_danh_muc_cha] BIGINT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- chat lieu
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [chat_lieu]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(100),
    [id_danh_muc_cha] BIGINT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- San pham
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [san_pham]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_danh_muc] BIGINT,
    [id_thuong_hieu] BIGINT,
    [id_chat_lieu] BIGINT,
    [ten] NVARCHAR(200),
    [ma] NVARCHAR(200),
    [xuat_xu] NVARCHAR(200),
    [mo_ta] NVARCHAR(MAX),
    [gia] DECIMAL(10,2),
    [anh] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Chi tiet San pham
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [chi_tiet_san_pham]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_san_pham] BIGINT,
    [id_size] BIGINT,
    [id_mau_sac] BIGINT,
    [so_luong] INT,
    [gia] DECIMAL(10,2),
    [ghi_chu] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Thuong hieu
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [thuong_hieu]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(100),
    [mo_ta] NVARCHAR(250),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- danh muc
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [danh_muc]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_danh_muc_cha] BIGINT,
    [ten] NVARCHAR(200),
    [mo_ta] NVARCHAR(1000),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- san pham yeu thich
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [san_pham_yeu_thich]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_san_pham] BIGINT,
    [id_nguoi_dung] BIGINT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- danh gia san pham
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [danh_gia]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_san_pham_chi_tiet] BIGINT,
    [id_nguoi_dung] BIGINT,
    [id_hoa_don] BIGINT,
    [danh_gia] INT,
    [nhan_xet] NVARCHAR(1000),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- khuyen mai
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--drop table khuyen_mai
CREATE TABLE [khuyen_mai]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [ten] NVARCHAR(200),
    [ma] VARCHAR(100),
    [mo_ta] NVARCHAR(1000),
    [loai] INT,
    [gia_tri] DECIMAL(10,2),
    [ngay_bat_dau] DATETIME,
    [ngay_ket_thuc] DATETIME,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];

GO

--- ap dung khuyen mai
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--drop table [ap_dung_khuyen_mai]
CREATE TABLE [ap_dung_khuyen_mai]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_khuyen_mai] BIGINT,
    [id_san_pham] BIGINT,
    [id_nguoi_dung] BIGINT,
    [gia_tri_giam] DECIMAL(10,2),
    [ngay_ap_dung] DATETIME,
    [trang_thai] INT DEFAULT 1,
    [da_su_dung] BIT,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- hinh anh
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [hinh_anh]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_san_pham] BIGINT,
    [id_yeu_cau_doi_tra] BIGINT,
    [anh] NVARCHAR(255),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Yeu cau doi tra
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [yeu_cau_doi_tra]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_hoa_don] BIGINT,
    [id_nguoi_dung] BIGINT,
    [loai_yeu_cau] NVARCHAR(50),
    [ly_do] NVARCHAR(1000),
    [trang_thai] INT DEFAULT 1,
    [ngay_yeu_cau] DATETIME DEFAULT GETDATE(),
    [ngay_hoan_tat] DATETIME DEFAULT GETDATE(),
    [ghi_chu] NVARCHAR(1000),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
) ON [PRIMARY];
GO

--- Chi tiet Yeu cau doi tra
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [yeu_cau_doi_tra_chi_tiet]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_yeu_cau_doi_tra] BIGINT,
    [id_san_pham_chi_tiet] BIGINT,
    [so_luong] INT,
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- san pham doi tra
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [san_pham_doi_tra]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_san_pham_chi_tiet] BIGINT, 
    [id_yeu_cau_doi_tra] BIGINT, 
    [so_luong] INT, 
    [ly_do] NVARCHAR(1000), 
    [loai_yeu_cau] NVARCHAR(50),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT, 
    [nguoi_cap_nhat] BIGINT 
) ON [PRIMARY];
GO

--- Blog
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [blog]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [tac_gia] NVARCHAR(255),
    [tieu_de] NVARCHAR(255),
    [mo_ta] NVARCHAR(MAX),
    [hinh_anh] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- Blog chi tiet
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [blog_chi_tiet]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_blog] BIGINT,
    [thu_tu] INT,
    [tieu_de] NVARCHAR(255),
    [noi_dung] NVARCHAR(MAX),
    [loai] VARCHAR(20),
    [hinh_anh] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
    UNIQUE (id_blog, thu_tu) -- Mỗi bài viết không trùng thứ tự phần

) ON [PRIMARY];
GO

--public enum LoaiNoiDung {
--    TEXT,
--    IMAGE,
--    VIDEO
--}

--- Bình luận
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [binh_luan]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_blog] BIGINT,
    [id_nguoi_dung] BIGINT ,
    [content] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO

--- phan hoi Bình luận
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [rep_binh_luan]
(
    [id] BIGINT IDENTITY(1,1) PRIMARY KEY CLUSTERED
    (
        [id] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    [id_comment] BIGINT ,
    [id_nguoi_dung] BIGINT ,
    [content] NVARCHAR(MAX),
    [trang_thai] INT DEFAULT 1,
    [ngay_tao] DATETIME DEFAULT GETDATE(),
    [ngay_cap_nhat] DATETIME DEFAULT GETDATE(),
    [nguoi_tao] BIGINT,
    [nguoi_cap_nhat] BIGINT
) ON [PRIMARY];
GO


