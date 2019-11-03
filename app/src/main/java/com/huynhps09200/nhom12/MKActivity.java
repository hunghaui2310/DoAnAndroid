package com.huynhps09200.nhom12;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huynhps09200.nhom12.Database.NguoiDungDao;
import com.huynhps09200.nhom12.Model.NguoiDung;
import com.huynhps09200.duanmau.R;

public class MKActivity extends AppCompatActivity {
    Animation translatex,translatey;
    Button btnCancel,btnCon;
    EditText edtPass,edtUser,edtRePass,edtNewPass;
    ImageView change;
    NguoiDung nguoiDung;
    DatabaseReference mData;
    NguoiDungDao nguoiDungDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mk);
        inits();
        btnCon.startAnimation(translatey);
        btnCancel.startAnimation(translatey);
        edtUser.startAnimation(translatey);
        edtRePass.startAnimation(translatey);
        edtPass.startAnimation(translatey);
        change.startAnimation(translatex);
        edtNewPass.startAnimation(translatey);
        mData = FirebaseDatabase.getInstance().getReference("NguoiDung");
        nguoiDungDao = new NguoiDungDao(this);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MKActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                final String newpass = edtNewPass.getText().toString();
                String renewpass = edtRePass.getText().toString();
                nguoiDung = new NguoiDung(user,newpass);
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (edtUser.getText().length() != 0 && edtPass.getText().length() != 0 && edtNewPass.getText().length() != 0 && edtRePass.getText().length() != 0) {
                            if (dataSnapshot.child(edtUser.getText().toString()).exists()) {
                                NguoiDung nguoiDung = dataSnapshot.child(edtUser.getText().toString()).getValue(NguoiDung.class);
                                if (nguoiDung.Pass.equals(edtPass.getText().toString())) {
                                    if (edtNewPass.getText().toString().equals(edtRePass.getText().toString())) {
                                        Toast.makeText(MKActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        nguoiDungDao.updatepass(nguoiDung);

                                    } else {
                                        Toast.makeText(MKActivity.this, "Mật khẩu mới phải trùng ", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MKActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MKActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MKActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
                public void inits(){
        btnCancel=findViewById(R.id.btnCancel);
        btnCon=findViewById(R.id.btnConfirm);
        edtPass=findViewById(R.id.edtPass);
        edtNewPass=findViewById(R.id.edtNewPass);
        edtRePass=findViewById(R.id.edtRePass);
        edtUser=findViewById(R.id.edtUser);
        change=findViewById(R.id.changePass);
        translatex= AnimationUtils.loadAnimation(this,R.anim.translatex);
        translatey= AnimationUtils.loadAnimation(this,R.anim.translatey);

    }
}
