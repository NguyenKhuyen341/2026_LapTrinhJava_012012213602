package com.planbookai.backend.presentation.dto.student;

import com.planbookai.backend.domain.model.studentdata.HocSinh;

import java.time.LocalDateTime;

public class HocSinhResponse {

    private Long id;
    private String hoTen;
    private String maSoHocSinh;
    private String lop;
    private Long giaoVienId;
    private String giaoVienTen;
    private Double diemTrungBinh;
    private Integer soLanThi;
    private LocalDateTime thoiGianTao;

    public HocSinhResponse(HocSinh hocSinh) {
        this.id = hocSinh.getId();
        this.hoTen = hocSinh.getHoTen();
        this.maSoHocSinh = hocSinh.getMaSoHocSinh();
        this.lop = hocSinh.getLop();

        if (hocSinh.getGiaoVien() != null) {
            this.giaoVienId = hocSinh.getGiaoVien().getId();
            this.giaoVienTen = hocSinh.getGiaoVien().getHoTen();
        }

        this.diemTrungBinh = hocSinh.getDiemTrungBinh();
        this.soLanThi = hocSinh.getSoLanThi();
        this.thoiGianTao = hocSinh.getThoiGianTao();
    }

    public Long getId() {
        return id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getMaSoHocSinh() {
        return maSoHocSinh;
    }

    public String getLop() {
        return lop;
    }

    public Long getGiaoVienId() {
        return giaoVienId;
    }

    public String getGiaoVienTen() {
        return giaoVienTen;
    }

    public Double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public Integer getSoLanThi() {
        return soLanThi;
    }

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }
}