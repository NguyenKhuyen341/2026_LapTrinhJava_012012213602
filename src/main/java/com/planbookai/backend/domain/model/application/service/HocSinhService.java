package com.planbookai.backend.application.service;

import com.planbookai.backend.domain.model.studentdata.HocSinh;
import com.planbookai.backend.domain.model.studentdata.HocSinhRepository;
import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import com.planbookai.backend.presentation.dto.student.HocSinhRequest;
import com.planbookai.backend.presentation.dto.student.HocSinhResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HocSinhService {

    private final HocSinhRepository hocSinhRepository;

    public HocSinhService(HocSinhRepository hocSinhRepository) {
        this.hocSinhRepository = hocSinhRepository;
    }

    @Transactional(readOnly = true)
    public List<HocSinhResponse> getAllByTeacher(Long teacherId) {
        return hocSinhRepository.findByGiaoVienId(teacherId)
                .stream()
                .map(HocSinhResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public HocSinhResponse getById(Long id, Long teacherId) {
        HocSinh hocSinh = hocSinhRepository
                .findByIdAndGiaoVienId(id, teacherId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy học sinh"));

        return new HocSinhResponse(hocSinh);
    }

    public HocSinhResponse create(
            HocSinhRequest request,
            NguoiDung teacher
    ) {

        if (hocSinhRepository.existsByMaSoHocSinhAndGiaoVienId(
                request.getMaSoHocSinh(),
                teacher.getId())) {

            throw new RuntimeException(
                    "Mã số học sinh đã tồn tại trong danh sách của giáo viên"
            );
        }

        HocSinh hocSinh = new HocSinh();

        hocSinh.setHoTen(request.getHoTen());
        hocSinh.setMaSoHocSinh(request.getMaSoHocSinh());
        hocSinh.setLop(request.getLop());
        hocSinh.setGiaoVien(teacher);

        if (request.getDiemTrungBinh() != null) {
            hocSinh.setDiemTrungBinh(request.getDiemTrungBinh());
        }

        if (request.getSoLanThi() != null) {
            hocSinh.setSoLanThi(request.getSoLanThi());
        }

        return new HocSinhResponse(
                hocSinhRepository.save(hocSinh)
        );
    }

    public HocSinhResponse update(
            Long id,
            HocSinhRequest request,
            Long teacherId
    ) {

        HocSinh hocSinh = hocSinhRepository
                .findByIdAndGiaoVienId(id, teacherId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy học sinh"));

        hocSinh.setHoTen(request.getHoTen());
        hocSinh.setMaSoHocSinh(request.getMaSoHocSinh());
        hocSinh.setLop(request.getLop());

        if (request.getDiemTrungBinh() != null) {
            hocSinh.setDiemTrungBinh(request.getDiemTrungBinh());
        }

        if (request.getSoLanThi() != null) {
            hocSinh.setSoLanThi(request.getSoLanThi());
        }

        return new HocSinhResponse(
                hocSinhRepository.save(hocSinh)
        );
    }

    public void delete(Long id, Long teacherId) {

        HocSinh hocSinh = hocSinhRepository
                .findByIdAndGiaoVienId(id, teacherId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy học sinh"));

        hocSinhRepository.delete(hocSinh);
    }
}