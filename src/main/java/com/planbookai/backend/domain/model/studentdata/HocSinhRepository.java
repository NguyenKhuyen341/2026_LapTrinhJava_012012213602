package com.planbookai.backend.domain.model.studentdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HocSinhRepository extends JpaRepository<HocSinh, Long> {

    List<HocSinh> findByGiaoVienId(Long giaoVienId);

    Optional<HocSinh> findByIdAndGiaoVienId(Long id, Long giaoVienId);

    boolean existsByMaSoHocSinhAndGiaoVienId(
            String maSoHocSinh,
            Long giaoVienId
    );
}