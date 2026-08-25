package com.planbookai.backend.domain.model.assessment;

import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "de_thi")
@Getter
@Setter
@NoArgsConstructor
public class DeThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String tieuDe;

    @Column(name = "mon_hoc", nullable = false, length = 50)
    private String monHoc = "Hóa học";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giao_vien_id", nullable = false)
    private NguoiDung giaoVien;

    @Column(name = "thoi_gian_lam", nullable = false)
    private Integer thoiGianLam;

    @Column(name = "tong_diem", nullable = false)
    private Double tongDiem = 10.0;

    @Column(name = "huong_dan_lam_bai", columnDefinition = "TEXT")
    private String huongDanLamBai;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrangThaiDeThi trangThai = TrangThaiDeThi.NHAP;

    @OneToMany(mappedBy = "deThi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CauHoiTrongDeThi> danhSachCauHoi = new ArrayList<>();

    @Column(name = "thoi_gian_tao", updatable = false)
    private LocalDateTime thoiGianTao = LocalDateTime.now();
}