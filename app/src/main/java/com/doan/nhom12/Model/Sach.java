package com.doan.nhom12.Model;

public class Sach {
    public String maSach,tenTheLoai,tenSach,tacGia,NXB;
    public int giaBan;
    public int soLuong;

    public Sach(String maSach, String tenTheLoai, String tenSach, String tacGia, String NXB, int giaBan, int soLuong) {
        this.maSach = maSach;
        this.tenTheLoai = tenTheLoai;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.NXB = NXB;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public Sach() {
    }
}
