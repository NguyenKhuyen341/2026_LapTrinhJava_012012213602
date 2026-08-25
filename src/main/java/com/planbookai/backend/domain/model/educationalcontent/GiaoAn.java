package com.planbookai.backend.domain.model.educationalcontent;

import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "giao_an")
@Getter
@Setter
@NoArgsConstructor
public class GiaoAn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String tieuDe;

    @Column(columnDefinition = "TEXT")
    private String mucTieu;

    @Column(columnDefinition = "TEXT")
    private String noiDung;

    @Column(columnDefinition = "TEXT")
    private String hoat_dong;

    @Column(columnDefinition = "TEXT")
    private String danh_gia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giao_vien_id", nullable = false)
    private NguoiDung giaoVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mau_giao_an_id")
    private GiaoAn mauGiaoAn;

    @Column(name = "mon_hoc", nullable = false, length = 50)
    private String monHoc = "Hóa học";

    @Column(length = 50)
    private String lop;

    @Column(name = "thoi_gian_day")
    private Integer thoiGianDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrangThaiGiaoAn trangThai = TrangThaiGiaoAn.SO_THAO;

    @Column(name = "thoi_gian_tao", updatable = false)
    private LocalDateTime thoiGianTao = LocalDateTime.now();

    @Column(name = "thoi_gian_cap_nhat")
    private LocalDateTime thoiGianCapNhat = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        thoiGianCapNhat = LocalDateTime.now();
    }
}