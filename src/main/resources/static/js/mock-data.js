/**
 * Dữ liệu giả lập (Mock Data) độc lập phục vụ giao diện Front-End PlanbookAI
 */
const mockData = {
  stats: {
    totalGiaoAn: 24,
    totalDeThi: 12,
    totalHocSinh: 145,
    aiGenerated: 38
  },

  giaoAnList: [
    {
      id: 101,
      tenBaiHoc: "Bài 15: Cấu trúc điều kiện trong Java (If-Else & Switch)",
      monHoc: "Lập trình Java",
      khoiLop: "Lớp 11",
      trangThai: "PUBLISHED",
      ngayTao: "2026-09-01",
      tacGia: "ThS. Nguyễn Văn An"
    },
    {
      id: 102,
      tenBaiHoc: "Bài 16: Vòng lặp For, While và Do-While",
      monHoc: "Lập trình Java",
      khoiLop: "Lớp 11",
      trangThai: "DRAFT",
      ngayTao: "2026-09-04",
      tacGia: "ThS. Nguyễn Văn An"
    },
    {
      id: 103,
      tenBaiHoc: "Bài 08: Lập trình hướng đối tượng (OOP Concepts)",
      monHoc: "Lập trình Java",
      khoiLop: "Lớp 12",
      trangThai: "PUBLISHED",
      ngayTao: "2026-08-28",
      tacGia: "Cô Trần Thị Mai"
    },
    {
      id: 104,
      tenBaiHoc: "Bài 02: Khái niệm về Cơ sở dữ liệu quan hệ SQL",
      monHoc: "Cơ sở dữ liệu",
      khoiLop: "Lớp 10",
      trangThai: "ARCHIVED",
      ngayTao: "2026-08-15",
      tacGia: "Thầy Lê Hoàng Long"
    }
  ],

  deThiList: [
    {
      id: 201,
      tieuDe: "Đề kiểm tra 1 tiết - Cấu trúc dữ liệu & Thuật toán Java",
      thoiGian: "45 phút",
      soCauHoi: 20,
      trangThai: "DRAFT",
      ngayTao: "2026-09-05"
    },
    {
      id: 202,
      tieuDe: "Đề thi giữa kỳ môn Lập trình Java căn bản",
      thoiGian: "60 phút",
      soCauHoi: 30,
      trangThai: "APPROVED",
      ngayTao: "2026-08-30"
    },
    {
      id: 203,
      tieuDe: "Bài thu hoạch trắc nghiệm OOP & Class Diagram",
      thoiGian: "30 phút",
      soCauHoi: 15,
      trangThai: "APPROVED",
      ngayTao: "2026-09-02"
    }
  ],

  cauHoiList: [
    {
      id: 301,
      noiDung: "Tính chất nào sau đây KHÔNG phải là đặc trưng cơ bản của Lập trình hướng đối tượng (OOP)?",
      mucDo: "DE",
      loai: "Trắc nghiệm",
      dapAn: "C. Tính tuần tự (Sequencing)"
    },
    {
      id: 302,
      noiDung: "Từ khóa nào trong Java dùng để kế thừa một lớp cơ sở (Base Class)?",
      mucDo: "TRUNG_BINH",
      loai: "Trắc nghiệm",
      dapAn: "B. extends"
    },
    {
      id: 303,
      noiDung: "Viết đoạn mã Java minh họa thuật toán Sắp xếp nổi bọt (Bubble Sort) và phân tích độ phức tạp thời gian.",
      mucDo: "KHO",
      loai: "Tự luận",
      dapAn: "Độ phức tạp O(n^2), sử dụng 2 vòng lặp lồng nhau."
    }
  ],

  hocSinhList: [
    { id: 1, maHS: "HS2026001", hoTen: "Nguyễn Minh Khoa", lop: "11A1", diemTB: 8.8, xepLoai: "Giỏi" },
    { id: 2, maHS: "HS2026002", hoTen: "Phạm Thảo Linh", lop: "11A1", diemTB: 9.2, xepLoai: "Xuất sắc" },
    { id: 3, maHS: "HS2026003", hoTen: "Đàn Văn Nam", lop: "11A2", diemTB: 7.5, xepLoai: "Khá" },
    { id: 4, maHS: "HS2026004", hoTen: "Trần Bảo Ngọc", lop: "12A3", diemTB: 8.1, xepLoai: "Giỏi" }
  ]
};
