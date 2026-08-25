package com.planbookai.backend.domain.model.assessment;

import com.planbookai.backend.domain.model.studentdata.HocSinh;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ket_qua")
@Getter
@Setter
@NoArgsConstructor
public class KetQua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hoc_sinh_id", nullable = false)
    private HocSinh hocSinh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "de_thi_id", nullable = false)
    private DeThi deThi;

    @Column(nullable = false)
    private Double diem;

    @Column(name = "so_cau_dung", nullable = false)
    private Integer soCauDung;

    @Column(name = "tong_so_cau", nullable = false)
    private Integer tongSoCau;

    @Column(name = "chi_tiet_dap_an", columnDefinition = "TEXT")
    private String chiTietDapAn;

    @Column(name = "duong_dan_bai_lam", length = 512)
    private String duongDanBaiLam;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai_cham", nullable = false)
    private TrangThaiCham trangThaiCham = TrangThaiCham.CHUA_CHAM;

    @Column(name = "thoi_gian_nop", updatable = false)
    private LocalDateTime thoiGianNop = LocalDateTime.now();

    @Column(name = "thoi_gian_cham")
    private LocalDateTime thoiGianCham;
}