package com.planbookai.backend.presentation.controller.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planbookai.backend.domain.model.assessment.CauHoiRepository;
import com.planbookai.backend.domain.model.assessment.MucDoKho;
import com.planbookai.backend.domain.model.assessment.NhanLuaChon;
import com.planbookai.backend.presentation.dto.question.CauHoiRequest;
import com.planbookai.backend.presentation.dto.question.LuaChonRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = "STAFF")
class CauHoiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CauHoiRepository cauHoiRepository;

    @BeforeEach
    void xoaDuLieuCu() {
        cauHoiRepository.deleteAll();
    }

    @Test
    void thucHienDuocDayDuCrud() throws Exception {
        String json = mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestHopLe())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/api/v1/questions/")))
                .andExpect(jsonPath("$.luaChon.length()").value(4))
                .andExpect(jsonPath("$.dapAnDung").value("A"))
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(json).get("id").asLong();

        mockMvc.perform(get("/questions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        CauHoiRequest requestSua = new CauHoiRequest(
                "Dung dịch nào có pH lớn hơn 7?",
                "Axit - Bazơ",
                MucDoKho.TRUNG_BINH,
                NhanLuaChon.B,
                List.of(
                        new LuaChonRequest(NhanLuaChon.A, "HCl"),
                        new LuaChonRequest(NhanLuaChon.B, "NaOH"),
                        new LuaChonRequest(NhanLuaChon.C, "NaCl"),
                        new LuaChonRequest(NhanLuaChon.D, "H2SO4")
                )
        );

        mockMvc.perform(put("/questions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestSua)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mucDoKho").value("TRUNG_BINH"))
                .andExpect(jsonPath("$.dapAnDung").value("B"));

        mockMvc.perform(delete("/questions/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/questions/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void tuChoiCauHoiKhongDuBonLuaChon() throws Exception {
        CauHoiRequest requestSai = new CauHoiRequest(
                "Câu hỏi không hợp lệ",
                "Hóa học",
                MucDoKho.KHO,
                NhanLuaChon.A,
                List.of(
                        new LuaChonRequest(NhanLuaChon.A, "Một"),
                        new LuaChonRequest(NhanLuaChon.B, "Hai"),
                        new LuaChonRequest(NhanLuaChon.C, "Ba")
                )
        );

        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestSai)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.thongBao").value(
                        "Mỗi câu hỏi bắt buộc phải có đúng 4 lựa chọn A, B, C, D"));
    }

    @Test
    void timKiemTheoTuKhoaChuDeVaMucDo() throws Exception {
        themCauHoi(requestHopLe());
        themCauHoi(new CauHoiRequest(
                "Số oxi hóa của lưu huỳnh trong H2SO4 là bao nhiêu?",
                "Oxi hóa - Khử",
                MucDoKho.KHO,
                NhanLuaChon.D,
                List.of(
                        new LuaChonRequest(NhanLuaChon.A, "+2"),
                        new LuaChonRequest(NhanLuaChon.B, "+3"),
                        new LuaChonRequest(NhanLuaChon.C, "+4"),
                        new LuaChonRequest(NhanLuaChon.D, "+6")
                )
        ));

        mockMvc.perform(get("/questions")
                        .param("tuKhoa", "oxi hóa")
                        .param("chuDe", "Oxi hóa - Khử")
                        .param("mucDoKho", "KHO")
                        .param("trang", "0")
                        .param("kichThuoc", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongPhanTu").value(1))
                .andExpect(jsonPath("$.noiDung[0].mucDoKho").value("KHO"));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void tuChoiVaiTroKhongPhaiStaff() throws Exception {
        mockMvc.perform(get("/questions"))
                .andExpect(status().isForbidden());
    }

    private void themCauHoi(CauHoiRequest request) throws Exception {
        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    private CauHoiRequest requestHopLe() {
        return new CauHoiRequest(
                "Chất nào làm quỳ tím hóa đỏ?",
                "Axit - Bazơ",
                MucDoKho.DE,
                NhanLuaChon.A,
                List.of(
                        new LuaChonRequest(NhanLuaChon.A, "HCl"),
                        new LuaChonRequest(NhanLuaChon.B, "NaOH"),
                        new LuaChonRequest(NhanLuaChon.C, "NaCl"),
                        new LuaChonRequest(NhanLuaChon.D, "H2O")
                )
        );
    }
}
