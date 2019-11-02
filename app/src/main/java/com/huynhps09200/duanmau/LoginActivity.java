package com.huynhps09200.duanmau;

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
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huynhps09200.duanmau.Database.NguoiDungDao;
import com.huynhps09200.duanmau.Model.NguoiDung;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    ImageView res;
    TextView dangki;
    EditText edtPass, edtUser;
    Animation translate;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inits();
        btnLogin.startAnimation(translate);
        edtUser.startAnimation(translate);
        edtPass.startAnimation(translate);
        res.startAnimation(translate);
        dangki.startAnimation(translate);
        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,AddPersonalActivity.class);
                startActivity(intent);
            }
        });
        mData=FirebaseDatabase.getInstance().getReference("NguoiDung");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(edtUser.getText().length()!=0 && edtPass.getText().length()!=0){
                        if(dataSnapshot.child(edtUser.getText().toString()).exists()){
                            NguoiDung nguoiDung= dataSnapshot.child(edtUser.getText().toString()).getValue(NguoiDung.class);
                            if(nguoiDung.Pass.equals(edtPass.getText().toString())){
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(LoginActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Tài khoản và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
            }
    public void inits() {
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        edtUser = findViewById(R.id.edtUser);
        res = findViewById(R.id.res);
        dangki=findViewById(R.id.Đăngkí);
        translate = AnimationUtils.loadAnimation(this, R.anim.translatex);
    }
}

