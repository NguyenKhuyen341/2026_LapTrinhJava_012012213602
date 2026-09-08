package com.planbookai.backend.application.service;

import com.planbookai.backend.common.exception.ViPhamNghiepVuException;
import com.planbookai.backend.domain.model.assessment.MucDoKho;
import com.planbookai.backend.domain.model.assessment.NhanLuaChon;
import com.planbookai.backend.presentation.dto.question.CauHoiRequest;
import com.planbookai.backend.presentation.dto.question.LuaChonRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CauHoiBusinessValidatorTest {

    private final CauHoiBusinessValidator validator = new CauHoiBusinessValidator();

    @Test
    void chapNhanKhiCoDuBonLuaChonABCD() {
        assertThatCode(() -> validator.kiemTraBatBien(taoRequest(List.of(
                luaChon(NhanLuaChon.A),
                luaChon(NhanLuaChon.B),
                luaChon(NhanLuaChon.C),
                luaChon(NhanLuaChon.D)
        )))).doesNotThrowAnyException();
    }

    @Test
    void tuChoiKhiChiCoBaLuaChon() {
        CauHoiRequest request = taoRequest(List.of(
                luaChon(NhanLuaChon.A),
                luaChon(NhanLuaChon.B),
                luaChon(NhanLuaChon.C)
        ));

        assertThatThrownBy(() -> validator.kiemTraBatBien(request))
                .isInstanceOf(ViPhamNghiepVuException.class)
                .hasMessageContaining("đúng 4 lựa chọn");
    }

    @Test
    void tuChoiKhiTrungNhanLuaChon() {
        CauHoiRequest request = taoRequest(List.of(
                luaChon(NhanLuaChon.A),
                luaChon(NhanLuaChon.B),
                luaChon(NhanLuaChon.C),
                luaChon(NhanLuaChon.C)
        ));

        assertThatThrownBy(() -> validator.kiemTraBatBien(request))
                .isInstanceOf(ViPhamNghiepVuException.class)
                .hasMessageContaining("không được trùng");
    }

    private CauHoiRequest taoRequest(List<LuaChonRequest> luaChon) {
        return new CauHoiRequest(
                "Chất nào làm quỳ tím hóa đỏ?",
                "Axit - Bazơ",
                MucDoKho.DE,
                NhanLuaChon.A,
                luaChon
        );
    }

    private LuaChonRequest luaChon(NhanLuaChon nhan) {
        return new LuaChonRequest(nhan, "Nội dung " + nhan);
    }
}
