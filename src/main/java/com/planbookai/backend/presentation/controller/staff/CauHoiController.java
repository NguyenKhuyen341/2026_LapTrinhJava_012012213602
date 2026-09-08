package com.planbookai.backend.presentation.controller.staff;

import com.planbookai.backend.application.service.CauHoiService;
import com.planbookai.backend.domain.model.assessment.MucDoKho;
import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import com.planbookai.backend.infrastructure.security.UserPrincipal;
import com.planbookai.backend.presentation.dto.question.CauHoiRequest;
import com.planbookai.backend.presentation.dto.question.CauHoiResponse;
import com.planbookai.backend.presentation.dto.question.TrangResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/questions")
public class CauHoiController {

    private final CauHoiService cauHoiService;

    public CauHoiController(CauHoiService cauHoiService) {
        this.cauHoiService = cauHoiService;
    }

    @PostMapping
    public ResponseEntity<CauHoiResponse> them(
            @Valid @RequestBody CauHoiRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        NguoiDung nguoiTao = principal == null ? null : principal.getNguoiDung();
        CauHoiResponse ketQua = cauHoiService.them(request, nguoiTao);

        return ResponseEntity
                .created(URI.create("/api/v1/questions/" + ketQua.id()))
                .body(ketQua);
    }

    @GetMapping("/{id}")
    public CauHoiResponse timTheoId(@PathVariable Long id) {
        return cauHoiService.timTheoId(id);
    }

    @GetMapping
    public TrangResponse<CauHoiResponse> timKiem(
            @RequestParam(required = false) String tuKhoa,
            @RequestParam(required = false) String chuDe,
            @RequestParam(required = false) MucDoKho mucDoKho,
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "20") int kichThuoc) {
        int trangHopLe = Math.max(trang, 0);
        int kichThuocHopLe = Math.min(Math.max(kichThuoc, 1), 100);
        Pageable pageable = PageRequest.of(
                trangHopLe,
                kichThuocHopLe,
                Sort.by(Sort.Direction.DESC, "thoiGianTao")
        );

        return cauHoiService.timKiem(tuKhoa, chuDe, mucDoKho, pageable);
    }

    @PutMapping("/{id}")
    public CauHoiResponse sua(@PathVariable Long id,
                              @Valid @RequestBody CauHoiRequest request) {
        return cauHoiService.sua(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        cauHoiService.xoa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
