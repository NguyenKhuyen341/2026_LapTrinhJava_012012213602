package com.planbookai.backend.domain.model.assessment;

import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "cau_hoi")
@Getter
@Setter
@NoArgsConstructor
public class CauHoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "noi_dung_cau_hoi", nullable = false, columnDefinition = "TEXT")
    private String noiDungCauHoi;

    @Column(name = "dap_an_a", nullable = false, columnDefinition = "TEXT")
    private String dapAnA;

    @Column(name = "dap_an_b", nullable = false, columnDefinition = "TEXT")
    private String dapAnB;

    @Column(name = "dap_an_c", nullable = false, columnDefinition = "TEXT")
    private String dapAnC;

    @Column(name = "dap_an_d", nullable = false, columnDefinition = "TEXT")
    private String dapAnD;

    @Column(name = "dap_an_dung", nullable = false, length = 1)
    private String dapAnDung;

    @Column(name = "mon_hoc", nullable = false, length = 50)
    private String monHoc = "Hóa học";

    @Column(nullable = false, length = 100)
    private String chuDe;

    @Enumerated(EnumType.STRING)
    @Column(name = "muc_do_kho", nullable = false)
    private MucDoKho mucDoKho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_tao_id")
    private NguoiDung nguoiTao;

    @Column(name = "thoi_gian_tao", updatable = false)
    private LocalDateTime thoiGianTao = LocalDateTime.now();
}