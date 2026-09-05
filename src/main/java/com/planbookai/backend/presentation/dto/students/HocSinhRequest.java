package com.planbookai.backend.presentation.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HocSinhRequest {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100, message = "Họ tên không được quá 100 ký tự")
    private String hoTen;

    @NotBlank(message = "Mã số học sinh không được để trống")
    @Size(max = 20, message = "Mã số học sinh không được quá 20 ký tự")
    private String maSoHocSinh;

    @NotBlank(message = "Lớp không được để trống")
    @Size(max = 50, message = "Tên lớp không được quá 50 ký tự")
    private String lop;

    private Double diemTrungBinh;

    private Integer soLanThi;

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaSoHocSinh() {
        return maSoHocSinh;
    }

    public void setMaSoHocSinh(String maSoHocSinh) {
        this.maSoHocSinh = maSoHocSinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public Double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(Double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public Integer getSoLanThi() {
        return soLanThi;
    }

    public void setSoLanThi(Integer soLanThi) {
        this.soLanThi = soLanThi;
    }
}