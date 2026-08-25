-- SQL Script khởi tạo cơ sở dữ liệu cho dự án PlanbookAI (PBA)
-- Đã tối ưu hóa cấu trúc thực thể, ràng buộc toàn vẹn, hiệu năng lập chỉ mục (Indexes),
-- và tương thích hoàn toàn với MySQL 8.x + Spring Data JPA.

-- 1. Tạo cơ sở dữ liệu và chuyển vùng sử dụng
CREATE DATABASE IF NOT EXISTS planbookai;
USE planbookai;

-- 2. Dọn dẹp các bảng cũ nếu đã tồn tại (Xếp theo thứ tự từ phụ thuộc cao nhất đến thấp nhất)
DROP TABLE IF EXISTS ket_qua;
DROP TABLE IF EXISTS cau_hoi_trong_de_thi;
DROP TABLE IF EXISTS giao_an;
DROP TABLE IF EXISTS de_thi;
DROP TABLE IF EXISTS hoc_sinh;
DROP TABLE IF EXISTS cau_hoi;
DROP TABLE IF EXISTS nguoi_dung;

-- ==========================================
-- 1. BẢNG: nguoi_dung (Quản lý tài khoản & Phân quyền)
-- ==========================================
CREATE TABLE nguoi_dung (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(180) NOT NULL UNIQUE COMMENT 'Địa chỉ email đăng nhập, không trùng lặp',
    mat_khau_ma_hoa VARCHAR(255) NOT NULL COMMENT 'Mật khẩu đã băm bằng thuật toán BCrypt',
    ho_ten VARCHAR(100) NOT NULL COMMENT 'Họ tên hiển thị của người dùng',
    vai_tro ENUM('ADMIN', 'MANAGER', 'STAFF', 'TEACHER') NOT NULL DEFAULT 'TEACHER' COMMENT 'Vai trò phân quyền hệ thống',
    trang_thai_hoat_dong BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Trạng thái tài khoản (True: Hoạt động, False: Bị khóa)',
    thoi_gian_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời điểm đăng ký tài khoản',
    thoi_gian_cap_nhat TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Thời điểm cập nhật thông tin gần nhất',
    INDEX idx_nguoidung_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 2. BẢNG: cau_hoi (Ngân hàng câu hỏi Hóa học)
-- ==========================================
CREATE TABLE cau_hoi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    noi_dung_cau_hoi TEXT NOT NULL COMMENT 'Nội dung đề bài, hỗ trợ mã LaTeX hiển thị công thức Hóa học',
    dap_an_a TEXT NOT NULL COMMENT 'Phương án lựa chọn A (hỗ trợ LaTeX)',
    dap_an_b TEXT NOT NULL COMMENT 'Phương án lựa chọn B (hỗ trợ LaTeX)',
    dap_an_c TEXT NOT NULL COMMENT 'Phương án lựa chọn C (hỗ trợ LaTeX)',
    dap_an_d TEXT NOT NULL COMMENT 'Phương án lựa chọn D (hỗ trợ LaTeX)',
    dap_an_dung CHAR(1) NOT NULL COMMENT 'Đáp án đúng chính xác (Chỉ nhận một trong các ký tự: A, B, C, D)',
    mon_hoc VARCHAR(50) NOT NULL DEFAULT 'Hóa học' COMMENT 'Môn học phân loại',
    chu_de VARCHAR(100) NOT NULL COMMENT 'Chủ đề kiến thức (ví dụ: Ancol, Este, Phi kim)',
    muc_do_kho ENUM('DE', 'TRUNG_BINH', 'KHO', 'RAT_KHO') NOT NULL COMMENT 'Cấp độ nhận thức',
    nguoi_tao_id BIGINT COMMENT 'ID của nhân viên Staff hoặc Giáo viên biên soạn',
    thoi_gian_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo câu hỏi',
    FOREIGN KEY (nguoi_tao_id) REFERENCES nguoi_dung(id) ON DELETE SET NULL,
    INDEX idx_cauhoi_chude_kho (chu_de, muc_do_kho),
    INDEX idx_cauhoi_mon_hoc (mon_hoc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 3. BẢNG: hoc_sinh (Hồ sơ học sinh - Giáo viên sở hữu)
-- ==========================================
CREATE TABLE hoc_sinh (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ho_ten VARCHAR(100) NOT NULL COMMENT 'Họ tên đầy đủ của học sinh',
    ma_so_hoc_sinh VARCHAR(20) NOT NULL COMMENT 'Mã số học sinh (MSSV/MSHS) dùng để đối chiếu tô tròn OMR',
    lop VARCHAR(50) NOT NULL COMMENT 'Tên lớp học hành chính',
    giao_vien_id BIGINT NOT NULL COMMENT 'ID giáo viên quản lý học sinh này',
    diem_trung_binh DOUBLE DEFAULT 0.0 COMMENT 'Điểm trung bình học tập tích lũy từ các bài thi đã làm',
    so_lan_thi INT DEFAULT 0 COMMENT 'Tổng số lần thực hiện bài thi trắc nghiệm trên hệ thống',
    thoi_gian_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời điểm tạo hồ sơ',
    FOREIGN KEY (giao_vien_id) REFERENCES nguoi_dung(id) ON DELETE CASCADE,
    UNIQUE KEY uq_giaovien_mshs (giao_vien_id, ma_so_hoc_sinh), -- Đảm bảo MSHS là duy nhất trong phạm vi của một giáo viên quản lý
    INDEX idx_hocsinh_mshs (ma_so_hoc_sinh)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 4. BẢNG: de_thi (Thông tin cấu hình đề thi trắc nghiệm)
-- ==========================================
CREATE TABLE de_thi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tieu_de VARCHAR(200) NOT NULL COMMENT 'Tiêu đề đề thi (Ví dụ: Kiểm tra Giữa kỳ I môn Hóa 12)',
    mon_hoc VARCHAR(50) NOT NULL DEFAULT 'Hóa học' COMMENT 'Môn học của đề thi',
    giao_vien_id BIGINT NOT NULL COMMENT 'ID của giáo viên tạo đề thi',
    thoi_gian_lam INT NOT NULL COMMENT 'Thời gian làm bài thi (tính bằng phút, giới hạn 15 - 180)',
    tong_diem DOUBLE NOT NULL DEFAULT 10.0 COMMENT 'Thang điểm tổng của đề thi (thường mặc định là 10.0)',
    huong_dan_lam_bai TEXT COMMENT 'Phần chữ hướng dẫn in ở đầu đề thi',
    trang_thai ENUM('NHAP', 'XUAT_BAN', 'KHOA') NOT NULL DEFAULT 'NHAP' COMMENT 'Trạng thái hoạt động của đề',
    thoi_gian_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian khởi tạo đề',
    FOREIGN KEY (giao_vien_id) REFERENCES nguoi_dung(id) ON DELETE CASCADE,
    INDEX idx_dethi_giaovien (giao_vien_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 5. BẢNG: giao_an (Nội dung giáo án giảng dạy)
-- ==========================================
CREATE TABLE giao_an (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tieu_de VARCHAR(200) NOT NULL COMMENT 'Tiêu đề bài học / Giáo án',
    muc_tieu TEXT COMMENT 'Mục tiêu bài giảng về mặt kiến thức, kỹ năng',
    noi_dung TEXT COMMENT 'Nội dung lý thuyết trọng tâm, hỗ trợ hiển thị LaTeX/HTML',
    hoat_dong TEXT COMMENT 'Tiến trình hoạt động giảng dạy trên lớp',
    danh_gia TEXT COMMENT 'Phương pháp và bài tập đánh giá kết quả',
    giao_vien_id BIGINT NOT NULL COMMENT 'Giáo viên sở hữu giáo án',
    mau_giao_an_id BIGINT DEFAULT NULL COMMENT 'ID liên kết tới Giáo án mẫu làm khung tham chiếu',
    mon_hoc VARCHAR(50) NOT NULL DEFAULT 'Hóa học' COMMENT 'Môn học giảng dạy',
    lop VARCHAR(50) COMMENT 'Khối lớp học (ví dụ: Khối 10, Khối 11, Khối 12)',
    thoi_gian_day INT COMMENT 'Thời lượng dạy học (tính bằng phút)',
    trang_thai ENUM('SO_THAO', 'CHO_DUYET', 'DA_DUYET', 'TU_CHOI') NOT NULL DEFAULT 'SO_THAO' COMMENT 'Quy trình kiểm duyệt giữa Staff/Manager',
    thoi_gian_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời điểm soạn thảo',
    thoi_gian_cap_nhat TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Thời điểm chỉnh sửa gần nhất',
    FOREIGN KEY (giao_vien_id) REFERENCES nguoi_dung(id) ON DELETE CASCADE,
    FOREIGN KEY (mau_giao_an_id) REFERENCES giao_an(id) ON DELETE SET NULL, -- Tự liên kết khóa ngoại để dùng lại giáo án mẫu
    INDEX idx_giaoan_giaovien (giao_vien_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 6. BẢNG: cau_hoi_trong_de_thi (Bảng trung gian kết nối Nhiều - Nhiều giữa Đề thi & Câu hỏi)
-- ==========================================
CREATE TABLE cau_hoi_trong_de_thi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    de_thi_id BIGINT NOT NULL COMMENT 'Mã liên kết đến đề thi',
    cau_hoi_id BIGINT NOT NULL COMMENT 'Mã liên kết đến câu hỏi',
    thu_tu INT NOT NULL COMMENT 'Thứ tự hiển thị của câu hỏi trong đề thi',
    diem DOUBLE NOT NULL COMMENT 'Trọng số điểm của câu hỏi đó trong đề thi cụ thể này',
    FOREIGN KEY (de_thi_id) REFERENCES de_thi(id) ON DELETE CASCADE,
    FOREIGN KEY (cau_hoi_id) REFERENCES cau_hoi(id) ON DELETE CASCADE,
    UNIQUE KEY uq_dethi_cauhoi (de_thi_id, cau_hoi_id) -- Đảm bảo một câu hỏi không bị thêm trùng lặp trong một đề
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==========================================
-- 7. BẢNG: ket_qua (Lịch sử làm bài thi & Kết quả chấm điểm OMR)
-- ==========================================
CREATE TABLE ket_qua (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hoc_school_id BIGINT NOT NULL COMMENT 'Mã định danh học sinh làm bài',
    de_thi_id BIGINT NOT NULL COMMENT 'Mã đề thi học sinh thực hiện',
    diem DOUBLE NOT NULL COMMENT 'Điểm số đạt được (Từ 0.0 đến 10.0)',
    so_cau_dung INT NOT NULL COMMENT 'Tổng số câu trả lời chính xác',
    tong_so_cau INT NOT NULL COMMENT 'Tổng số câu hỏi của đề thi',
    chi_tiet_dap_an TEXT COMMENT 'Chuỗi kết quả lựa chọn của học sinh lưu cách nhau bởi dấu phẩy (ví dụ: A,B,,C,D)',
    duong_dan_bai_lam VARCHAR(512) COMMENT 'URL lưu trữ ảnh chụp phiếu làm bài của học sinh trên Supabase Storage',
    trang_thai_cham ENUM('CHUA_CHAM', 'DANG_CHAM', 'DA_CHAM', 'LOI_OMR') NOT NULL DEFAULT 'CHUA_CHAM' COMMENT 'Trạng thái xử lý của module OpenCV OMR',
    thoi_gian_nop TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời điểm giáo viên tải bài làm của học sinh lên',
    thoi_gian_cham TIMESTAMP NULL DEFAULT NULL COMMENT 'Thời điểm hệ thống hoàn tất xử lý ảnh chấm bài',
    FOREIGN KEY (hoc_school_id) REFERENCES hoc_sinh(id) ON DELETE CASCADE,
    FOREIGN KEY (de_thi_id) REFERENCES de_thi(id) ON DELETE CASCADE,
    INDEX idx_ketqua_hocsinh (hoc_school_id),
    INDEX idx_ketqua_dethi (de_thi_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =========================================================================
-- DỮ LIỆU MẪU (SEED DATA) KHỞI TẠO HỆ THỐNG
-- Mật khẩu đăng nhập mặc định của tất cả các tài khoản mẫu là: password123
-- Được mã hóa băm Bcrypt: $2a$10$8.K98/W2kyg8Xq4D8OUnIuGfK5L8V4YfW.z7b0XgW/C0hY90jZp6.
-- =========================================================================

-- 1. Chèn tài khoản mẫu cho 4 vai trò cốt lõi (Admin, Manager, Staff, Teacher)
INSERT INTO nguoi_dung (email, mat_khau_ma_hoa, ho_ten, vai_tro, trang_thai_hoat_dong) VALUES
('admin@planbookai.com', '$2a$10$8.K98/W2kyg8Xq4D8OUnIuGfK5L8V4YfW.z7b0XgW/C0hY90jZp6.', 'Hệ thống Admin', 'ADMIN', true),
('manager@planbookai.com', '$2a$10$8.K98/W2kyg8Xq4D8OUnIuGfK5L8V4YfW.z7b0XgW/C0hY90jZp6.', 'Nguyễn Văn Quản Lý', 'MANAGER', true),
('staff@planbookai.com', '$2a$10$8.K98/W2kyg8Xq4D8OUnIuGfK5L8V4YfW.z7b0XgW/C0hY90jZp6.', 'Trần Thị Nhân Viên', 'STAFF', true),
('teacher@planbookai.com', '$2a$10$8.K98/W2kyg8Xq4D8OUnIuGfK5L8V4YfW.z7b0XgW/C0hY90jZp6.', 'Phạm Minh Giáo Viên', 'TEACHER', true);

-- 2. Chèn dữ liệu mẫu cho ngân hàng câu hỏi Hóa học (Do Staff hoặc Teacher tạo)
-- Chủ đề: Este - Lipit (Hóa học THPT Khối 12)
INSERT INTO cau_hoi (noi_dung_cau_hoi, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_dung, mon_hoc, chu_de, muc_do_kho, nguoi_tao_id) VALUES
('Chất nào sau đây là este của axit axetic?', '$\\text{CH}_3\\text{COOCH}_3$', '$\\text{C}_2\\text{H}_5\\text{OH}$', '$\\text{CH}_3\\text{COOH}$', '$\\text{HCOOCH}_3$', 'A', 'Hóa học', 'Este - Lipit', 'DE', 3),
('Thủy phân hoàn toàn etyl axetate trong dung dịch NaOH đun nóng thu được sản phẩm gồm những chất nào?', '$\\text{CH}_3\\text{COONa}$ và $\\text{CH}_3\\text{OH}$', '$\\text{CH}_3\\text{COONa}$ và $\\text{C}_2\\text{H}_5\\text{OH}$', '$\\text{HCOONa}$ và $\\text{C}_2\\text{H}_5\\text{OH}$', '$\\text{CH}_3\\text{COOH}$ và $\\text{C}_2\\text{H}_5\\text{OH}$', 'B', 'Hóa học', 'Este - Lipit', 'DE', 3),
('Phản ứng giữa axit cacboxylic và ancol tạo ra este được gọi là loại phản ứng nào sau đây?', 'Phản ứng xà phòng hóa', 'Phản ứng este hóa', 'Phản ứng trùng hợp', 'Phản ứng thủy phân', 'B', 'Hóa học', 'Este - Lipit', 'TRUNG_BINH', 3),
('Chất nào sau đây có nhiệt độ sôi thấp nhất trong các chất có cùng công thức phân tử C2H4O2?', 'Axit axetic', 'Metyl fomat', 'Anđehit axetic', 'Etylen glicol', 'B', 'Hóa học', 'Este - Lipit', 'KHO', 4);

-- 3. Chèn hồ sơ học sinh mẫu (Do Giáo viên quản lý)
INSERT INTO hoc_sinh (ho_ten, ma_so_hoc_sinh, lop, giao_vien_id, diem_trung_binh, so_lan_thi) VALUES
('Nguyễn Hoàng Nam', 'HS0001', '12A1', 4, 8.5, 2),
('Lê Mỹ Uyên', 'HS0002', '12A1', 4, 9.0, 2),
('Trần Quốc Bảo', 'HS0003', '12A1', 4, 6.5, 2),
('Phạm Thùy Linh', 'HS0004', '12A1', 4, 7.8, 2);

-- 4. Chèn đề thi mẫu (Do Giáo viên biên soạn)
INSERT INTO de_thi (tieu_de, mon_hoc, giao_vien_id, thoi_gian_lam, tong_diem, huong_dan_lam_bai, trang_thai) VALUES
('Đề kiểm tra chương Este - Lipit (Mã đề 101)', 'Hóa học', 4, 45, 10.0, 'Học sinh sử dụng bút chì tô kín vào ô lựa chọn đáp án đúng nhất trên phiếu trả lời.', 'XUAT_BAN');

-- 5. Liên kết các câu hỏi vào đề thi mẫu (Bảng trung gian Many-to-Many)
-- Đề thi có 4 câu hỏi, mỗi câu 2.5 điểm
INSERT INTO cau_hoi_trong_de_thi (de_thi_id, cau_hoi_id, thu_tu, diem) VALUES
(1, 1, 1, 2.5),
(1, 2, 2, 2.5),
(1, 3, 3, 2.5),
(1, 4, 4, 2.5);

-- 6. Chèn kết quả thi mẫu của học sinh (Hệ thống OpenCV đã chấm)
INSERT INTO ket_qua (hoc_sinh_id, de_thi_id, diem, so_cau_dung, tong_so_cau, chi_tiet_dap_an, duong_dan_bai_lam, trang_thai_cham) VALUES
(1, 1, 10.0, 4, 4, 'A,B,B,B', 'https://supabase.planbookai.com/storage/v1/object/public/answer-sheets/hs0001_de101.jpg', 'DA_CHAM'),
(2, 1, 7.5, 3, 4, 'A,B,B,A', 'https://supabase.planbookai.com/storage/v1/object/public/answer-sheets/hs0002_de101.jpg', 'DA_CHAM'),
(3, 1, 5.0, 2, 4, 'B,A,B,B', 'https://supabase.planbookai.com/storage/v1/object/public/answer-sheets/hs0003_de101.jpg', 'DA_CHAM'),
(4, 1, 7.5, 3, 4, 'A,B,A,B', 'https://supabase.planbookai.com/storage/v1/object/public/answer-sheets/hs0004_de101.jpg', 'DA_CHAM');

-- 7. Chèn Giáo án mẫu (Do Staff soạn sẵn làm khung tham khảo) và Giáo án của Giáo viên
INSERT INTO giao_an (tieu_de, muc_tieu, noi_dung, hoat_dong, danh_gia, giao_vien_id, mau_giao_an_id, mon_hoc, lop, thoi_gian_day, trang_thai) VALUES
-- Giáo án mẫu của Staff (Được Manager phê duyệt)
('Giáo án mẫu: Este - Lipit (Tiết 1)', 'Học sinh nắm vững định nghĩa, công thức chung và danh pháp este.', 'Nội dung lý thuyết trọng tâm về liên kết este $\\text{R-COO-R'}$.', 'Hoạt động 1: Khởi động (5 phút). Hoạt động 2: Hình thành kiến thức (30 phút).', 'Câu hỏi củng cố cuối bài.', 3, NULL, 'Hóa học', 'Khối 12', 45, 'DA_DUYET'),
-- Giáo án cá nhân của Giáo viên (Kế thừa từ giáo án mẫu trên)
('Giáo án cá nhân: Bài 1 - Este (Lớp 12A1)', 'Giúp học sinh 12A1 hiểu sâu về tính chất hóa học và cách gọi tên este.', 'Nội dung lý thuyết chi tiết từ giáo án mẫu và bổ sung các ví dụ minh họa trực quan.', 'Thực hiện các hoạt động nhóm thảo luận về este hóa.', 'Kiểm tra trắc nghiệm 10 phút trên ứng dụng.', 4, 1, 'Hóa học', 'Khối 12', 45, 'DA_DUYET');
