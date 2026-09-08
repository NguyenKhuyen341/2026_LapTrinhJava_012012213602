package com.planbookai.backend.domain.model.assessment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CauHoiRepository extends JpaRepository<CauHoi, Long>,
        JpaSpecificationExecutor<CauHoi> {
}
