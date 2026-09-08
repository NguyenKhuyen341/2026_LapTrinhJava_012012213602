package com.planbookai.backend.presentation.dto.question;

import com.planbookai.backend.domain.model.assessment.MucDoKho;
import com.planbookai.backend.domain.model.assessment.NhanLuaChon;

import java.time.LocalDateTime;
import java.util.List;

public record CauHoiResponse(
        Long id,
        String noiDungCauHoi,
        String monHoc,
        String chuDe,
        MucDoKho mucDoKho,
        NhanLuaChon dapAnDung,
        List<LuaChonResponse> luaChon,
        LocalDateTime thoiGianTao
) {
}
