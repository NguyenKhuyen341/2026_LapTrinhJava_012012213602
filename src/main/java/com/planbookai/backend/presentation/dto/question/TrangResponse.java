package com.planbookai.backend.presentation.dto.question;

import org.springframework.data.domain.Page;

import java.util.List;

public record TrangResponse<T>(
        List<T> noiDung,
        int trang,
        int kichThuoc,
        long tongPhanTu,
        int tongSoTrang,
        boolean trangCuoi
) {
    public static <T> TrangResponse<T> tu(Page<T> ketQua) {
        return new TrangResponse<>(
                ketQua.getContent(),
                ketQua.getNumber(),
                ketQua.getSize(),
                ketQua.getTotalElements(),
                ketQua.getTotalPages(),
                ketQua.isLast()
        );
    }
}
