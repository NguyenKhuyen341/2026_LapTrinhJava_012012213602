package com.planbookai.backend.presentation.dto.question;

import com.planbookai.backend.domain.model.assessment.NhanLuaChon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LuaChonRequest(
        @NotNull(message = "Nhãn lựa chọn không được để trống")
        NhanLuaChon nhan,

        @NotBlank(message = "Nội dung lựa chọn không được để trống")
        String noiDung
) {
}
