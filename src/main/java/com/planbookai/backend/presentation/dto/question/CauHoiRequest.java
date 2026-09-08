package com.planbookai.backend.presentation.dto.question;

import com.planbookai.backend.domain.model.assessment.MucDoKho;
import com.planbookai.backend.domain.model.assessment.NhanLuaChon;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CauHoiRequest(
        @NotBlank(message = "Nội dung câu hỏi không được để trống")
        String noiDungCauHoi,

        @NotBlank(message = "Chủ đề không được để trống")
        @Size(max = 100, message = "Chủ đề không được vượt quá 100 ký tự")
        String chuDe,

        @NotNull(message = "Mức độ khó không được để trống")
        MucDoKho mucDoKho,

        @NotNull(message = "Đáp án đúng không được để trống")
        NhanLuaChon dapAnDung,

        @NotNull(message = "Danh sách lựa chọn không được để trống")
        @Valid
        List<LuaChonRequest> luaChon
) {
}
