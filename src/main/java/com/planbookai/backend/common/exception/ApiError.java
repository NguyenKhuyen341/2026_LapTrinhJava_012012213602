package com.planbookai.backend.common.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        LocalDateTime thoiGian,
        int trangThai,
        String loi,
        String thongBao,
        String duongDan,
        Map<String, String> loiTruong
) {
}
