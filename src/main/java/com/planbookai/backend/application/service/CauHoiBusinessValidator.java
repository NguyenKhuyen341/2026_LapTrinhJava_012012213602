package com.planbookai.backend.application.service;

import com.planbookai.backend.common.exception.ViPhamNghiepVuException;
import com.planbookai.backend.domain.model.assessment.NhanLuaChon;
import com.planbookai.backend.presentation.dto.question.CauHoiRequest;
import com.planbookai.backend.presentation.dto.question.LuaChonRequest;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CauHoiBusinessValidator {

    public void kiemTraBatBien(CauHoiRequest request) {
        List<LuaChonRequest> luaChon = request.luaChon();

        if (luaChon == null || luaChon.size() != 4) {
            throw new ViPhamNghiepVuException(
                    "Mỗi câu hỏi bắt buộc phải có đúng 4 lựa chọn A, B, C, D"
            );
        }

        Set<NhanLuaChon> nhanThucTe = new HashSet<>();
        for (LuaChonRequest item : luaChon) {
            if (item == null || item.nhan() == null) {
                throw new ViPhamNghiepVuException(
                        "Mỗi lựa chọn phải có nhãn A, B, C hoặc D"
                );
            }

            if (!nhanThucTe.add(item.nhan())) {
                throw new ViPhamNghiepVuException(
                        "Nhãn lựa chọn không được trùng nhau: " + item.nhan()
                );
            }
        }

        if (!nhanThucTe.equals(EnumSet.allOf(NhanLuaChon.class))) {
            throw new ViPhamNghiepVuException(
                    "Bốn lựa chọn phải mang đủ các nhãn A, B, C và D"
            );
        }

        if (request.dapAnDung() == null || !nhanThucTe.contains(request.dapAnDung())) {
            throw new ViPhamNghiepVuException(
                    "Đáp án đúng phải là một trong bốn lựa chọn A, B, C, D"
            );
        }
    }
}
