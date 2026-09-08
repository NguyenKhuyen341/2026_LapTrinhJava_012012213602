package com.planbookai.backend.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KhongTimThayException.class)
    public ResponseEntity<ApiError> xuLyKhongTimThay(KhongTimThayException exception,
                                                     HttpServletRequest request) {
        return taoLoi(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ViPhamNghiepVuException.class)
    public ResponseEntity<ApiError> xuLyViPhamNghiepVu(ViPhamNghiepVuException exception,
                                                       HttpServletRequest request) {
        return taoLoi(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> xuLyValidation(MethodArgumentNotValidException exception,
                                                   HttpServletRequest request) {
        Map<String, String> loiTruong = new LinkedHashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            loiTruong.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return taoLoi(
                HttpStatus.BAD_REQUEST,
                "Dữ liệu đầu vào không hợp lệ",
                request,
                loiTruong
        );
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> xuLyDuLieuSaiDinhDang(Exception exception,
                                                          HttpServletRequest request) {
        return taoLoi(
                HttpStatus.BAD_REQUEST,
                "Mức độ chỉ nhận DE, TRUNG_BINH, KHO, RAT_KHO; đáp án chỉ nhận A, B, C, D",
                request,
                Map.of()
        );
    }

    private ResponseEntity<ApiError> taoLoi(HttpStatus status,
                                            String thongBao,
                                            HttpServletRequest request,
                                            Map<String, String> loiTruong) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                thongBao,
                request.getRequestURI(),
                loiTruong
        );
        return ResponseEntity.status(status).body(body);
    }
}
