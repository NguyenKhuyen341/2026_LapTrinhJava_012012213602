package com.planbookai.backend.presentation.controller.teacher;

import com.planbookai.backend.application.service.HocSinhService;
import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import com.planbookai.backend.infrastructure.security.UserPrincipal;
import com.planbookai.backend.presentation.dto.student.HocSinhRequest;
import com.planbookai.backend.presentation.dto.student.HocSinhResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher/students")
public class HocSinhController {

    private final HocSinhService hocSinhService;

    public HocSinhController(HocSinhService hocSinhService) {
        this.hocSinhService = hocSinhService;
    }

    @GetMapping
    public ResponseEntity<List<HocSinhResponse>> getAllStudents(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        NguoiDung teacher = userPrincipal.getNguoiDung();

        return ResponseEntity.ok(
                hocSinhService.getAllByTeacher(teacher.getId())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<HocSinhResponse> getStudent(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        NguoiDung teacher = userPrincipal.getNguoiDung();

        return ResponseEntity.ok(
                hocSinhService.getById(id, teacher.getId())
        );
    }

    @PostMapping
    public ResponseEntity<HocSinhResponse> createStudent(
            @Valid @RequestBody HocSinhRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        NguoiDung teacher = userPrincipal.getNguoiDung();

        HocSinhResponse response =
                hocSinhService.create(request, teacher);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HocSinhResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody HocSinhRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        NguoiDung teacher = userPrincipal.getNguoiDung();

        return ResponseEntity.ok(
                hocSinhService.update(
                        id,
                        request,
                        teacher.getId()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        NguoiDung teacher = userPrincipal.getNguoiDung();

        hocSinhService.delete(
                id,
                teacher.getId()
        );

        return ResponseEntity.noContent().build();
    }
}