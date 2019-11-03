package com.doan.nhom12.Model;

public class HoaDonChiTiet {
    public String maHoadon,ngayMua;
    public String maSach;
    public String gia;
    public String tongTien;
    public int soLuongMua;

    public HoaDonChiTiet(String maHoadon, String ngayMua, String maSach, String gia, String tongTien, int soLuongMua) {
        this.maHoadon = maHoadon;
        this.ngayMua = ngayMua;
        this.maSach = maSach;
        this.gia = gia;
        this.tongTien = tongTien;
        this.soLuongMua = soLuongMua;
    }

    public HoaDonChiTiet() {
    }
}
