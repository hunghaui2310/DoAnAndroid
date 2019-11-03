package com.huynhps09200.nhom12.Model;

public class NguoiDung {
    public String User,Pass,Fullname,Phone;

    public NguoiDung(String user, String pass, String fullname, String phone) {
        this.User = user;
        this.Pass = pass;
        this.Fullname = fullname;
        this.Phone = phone;
    }

    public NguoiDung() {
    }

    public NguoiDung(String user, String pass) {
    }
}
