package com.planbookai.backend.application.service;

import com.planbookai.backend.common.exception.KhongTimThayException;
import com.planbookai.backend.domain.model.assessment.CauHoi;
import com.planbookai.backend.domain.model.assessment.CauHoiRepository;
import com.planbookai.backend.domain.model.assessment.CauHoiSpecifications;
import com.planbookai.backend.domain.model.assessment.MucDoKho;
import com.planbookai.backend.domain.model.assessment.NhanLuaChon;
import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import com.planbookai.backend.presentation.dto.question.CauHoiRequest;
import com.planbookai.backend.presentation.dto.question.CauHoiResponse;
import com.planbookai.backend.presentation.dto.question.LuaChonRequest;
import com.planbookai.backend.presentation.dto.question.LuaChonResponse;
import com.planbookai.backend.presentation.dto.question.TrangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CauHoiService {

    private final CauHoiRepository cauHoiRepository;
    private final CauHoiBusinessValidator validator;

    public CauHoiService(CauHoiRepository cauHoiRepository,
                         CauHoiBusinessValidator validator) {
        this.cauHoiRepository = cauHoiRepository;
        this.validator = validator;
    }

    public CauHoiResponse them(CauHoiRequest request, NguoiDung nguoiTao) {
        validator.kiemTraBatBien(request);

        CauHoi cauHoi = new CauHoi();
        capNhatDuLieu(cauHoi, request);
        cauHoi.setNguoiTao(nguoiTao);

        return chuyenSangResponse(cauHoiRepository.save(cauHoi));
    }

    @Transactional(readOnly = true)
    public CauHoiResponse timTheoId(Long id) {
        return chuyenSangResponse(timEntity(id));
    }

    @Transactional(readOnly = true)
    public TrangResponse<CauHoiResponse> timKiem(String tuKhoa,
                                                 String chuDe,
                                                 MucDoKho mucDoKho,
                                                 Pageable pageable) {
        Specification<CauHoi> dieuKien = Specification
                .where(CauHoiSpecifications.coTuKhoa(tuKhoa))
                .and(CauHoiSpecifications.thuocChuDe(chuDe))
                .and(CauHoiSpecifications.coMucDo(mucDoKho));

        Page<CauHoiResponse> ketQua = cauHoiRepository.findAll(dieuKien, pageable)
                .map(this::chuyenSangResponse);
        return TrangResponse.tu(ketQua);
    }

    public CauHoiResponse sua(Long id, CauHoiRequest request) {
        validator.kiemTraBatBien(request);

        CauHoi cauHoi = timEntity(id);
        capNhatDuLieu(cauHoi, request);

        return chuyenSangResponse(cauHoiRepository.save(cauHoi));
    }

    public void xoa(Long id) {
        CauHoi cauHoi = timEntity(id);
        cauHoiRepository.delete(cauHoi);
    }

    private CauHoi timEntity(Long id) {
        return cauHoiRepository.findById(id)
                .orElseThrow(() -> new KhongTimThayException(
                        "Không tìm thấy câu hỏi có id = " + id
                ));
    }

    private void capNhatDuLieu(CauHoi cauHoi, CauHoiRequest request) {
        Map<NhanLuaChon, String> noiDungTheoNhan = request.luaChon().stream()
                .collect(Collectors.toMap(
                        LuaChonRequest::nhan,
                        luaChon -> luaChon.noiDung().trim()
                ));

        cauHoi.setNoiDungCauHoi(request.noiDungCauHoi().trim());
        cauHoi.setMonHoc("Hóa học");
        cauHoi.setChuDe(request.chuDe().trim());
        cauHoi.setMucDoKho(request.mucDoKho());
        cauHoi.setDapAnDung(request.dapAnDung());
        cauHoi.setDapAnA(noiDungTheoNhan.get(NhanLuaChon.A));
        cauHoi.setDapAnB(noiDungTheoNhan.get(NhanLuaChon.B));
        cauHoi.setDapAnC(noiDungTheoNhan.get(NhanLuaChon.C));
        cauHoi.setDapAnD(noiDungTheoNhan.get(NhanLuaChon.D));
    }

    private CauHoiResponse chuyenSangResponse(CauHoi cauHoi) {
        List<LuaChonResponse> luaChon = List.of(
                new LuaChonResponse(NhanLuaChon.A, cauHoi.getDapAnA()),
                new LuaChonResponse(NhanLuaChon.B, cauHoi.getDapAnB()),
                new LuaChonResponse(NhanLuaChon.C, cauHoi.getDapAnC()),
                new LuaChonResponse(NhanLuaChon.D, cauHoi.getDapAnD())
        ).stream().sorted(Comparator.comparing(LuaChonResponse::nhan)).toList();

        return new CauHoiResponse(
                cauHoi.getId(),
                cauHoi.getNoiDungCauHoi(),
                cauHoi.getMonHoc(),
                cauHoi.getChuDe(),
                cauHoi.getMucDoKho(),
                cauHoi.getDapAnDung(),
                luaChon,
                cauHoi.getThoiGianTao()
        );
    }
}
