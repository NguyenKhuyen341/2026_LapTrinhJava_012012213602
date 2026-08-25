package com.planbookai.backend.domain.model.studentdata;

import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoc_sinh", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"giao_vien_id", "ma_so_hoc_sinh"})
})
@Getter
@Setter
@NoArgsConstructor
public class HocSinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "ma_so_hoc_sinh", nullable = false, length = 20)
    private String maSoHocSinh;

    @Column(nullable = false, length = 50)
    private String lop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giao_vien_id", nullable = false)
    private NguoiDung giaoVien;

    @Column(name = "diem_trung_binh")
    private Double diemTrungBinh = 0.0;

    @Column(name = "so_lan_thi")
    private Integer soLanThi = 0;

    @Column(name = "thoi_gian_tao", updatable = false)
    private LocalDateTime thoiGianTao = LocalDateTime.now();
}