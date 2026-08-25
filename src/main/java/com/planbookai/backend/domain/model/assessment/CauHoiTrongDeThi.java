package com.planbookai.backend.domain.model.assessment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cau_hoi_trong_de_thi", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"de_thi_id", "cau_hoi_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class CauHoiTrongDeThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "de_thi_id", nullable = false)
    private DeThi deThi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cau_hoi_id", nullable = false)
    private CauHoi id_cauhoi;

    @Column(nullable = false)
    private Integer thuTu;

    @Column(nullable = false)
    private Double diem;
}