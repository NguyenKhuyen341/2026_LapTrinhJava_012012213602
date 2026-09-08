package com.planbookai.backend.presentation.dto.question;

import com.planbookai.backend.domain.model.assessment.NhanLuaChon;

public record LuaChonResponse(
        NhanLuaChon nhan,
        String noiDung
) {
}
