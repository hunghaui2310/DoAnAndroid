package com.doan.nhom12;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.doan.nhom12.Adapter.BookAdapter;
import com.doan.nhom12.Database.SachDao;
import com.doan.nhom12.MainActivity;
import com.doan.nhom12.Model.Sach;
import com.doan.nhom12.Model.TheLoai;
import com.doan.nhom12.R;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {
    Button btnAdd;
    Dialog dialog;
    ImageView back,book;
    Animation translatex,translatey;
    RecyclerView LV;
    Context context;
    BookAdapter adapter;
    Sach sach;
    TheLoai theLoai;
    SachDao sachDao;
    String spn;
    DatabaseReference mData;
    ArrayList<Sach> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        inits();
        book.startAnimation(translatex);
        back.startAnimation(translatex);
        btnAdd.startAnimation(translatey);
        sachDao=new SachDao(this);
        LV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AddBookActivity.this,LinearLayoutManager.VERTICAL,false);
        LV.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
        list= sachDao.getAll();
        mData= FirebaseDatabase.getInstance().getReference();
        adapter=new BookAdapter(AddBookActivity.this,list);
        LV.setAdapter(adapter);
        Load();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddBookActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
                final EditText edtIdbook=dialog.findViewById(R.id.edtIdbook);
                final EditText edttenSach=dialog.findViewById(R.id.edtNameBook);
                final Spinner edtTL=dialog.findViewById(R.id.SpTheloai);
                final EditText edtTG=dialog.findViewById(R.id.edtAuthor);
                final EditText edtNXB=dialog.findViewById(R.id.edtNXB);
                final EditText edtSL=dialog.findViewById(R.id.edtSl);
                final EditText edtGIA=dialog.findViewById(R.id.edtgia);
                Button btnConfirm=dialog.findViewById(R.id.btnConfirm);
                Button btncancel=dialog.findViewById(R.id.btnCancel);
                mData.child("TheLoai").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final List<String> tenTLlist = new ArrayList<>();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String tenTL = data.child("tenTheLoai").getValue(String.class);
                            if (tenTL != null) {
                                tenTLlist.add(tenTL);
                            }
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(AddBookActivity.this, R.layout.lv_tentl,R.id.TVTENTL, tenTLlist);
                        edtTL.setAdapter(arrayAdapter);
                        edtTL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                spn = adapterView.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maSach=edtIdbook.getText().toString();
                        String tenTL=spn;
                        String tenSach=edttenSach.getText().toString();
                        String tacgia=edtTG.getText().toString();
                        String nXB=edtNXB.getText().toString();

                        int SL= Integer.parseInt(edtSL.getText().toString());
                        int gia= Integer.parseInt(edtGIA.getText().toString());

                        sach=new Sach(maSach,tenTL,tenSach,tacgia,nXB,gia,SL);
                        if(edtGIA.getText().length()!=0 && edtIdbook.getText().length()!=0 && edtNXB.getText().length()!=0 && edtSL.getText().length()!=0 && edtTG.getText().length()!=0 && edttenSach.getText().length()!=0){
                                sachDao.insert(sach);
                                dialog.dismiss();
                                Toast.makeText(AddBookActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                            }else {
                            Toast.makeText(AddBookActivity.this, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                        }}
                });
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }
    public void showdialog(){
        dialog=new Dialog(AddBookActivity.this);
        dialog.setContentView(R.layout.book_dialog);
        dialog.setCancelable(false);
        dialog.show();

    }
    public void inits(){
        btnAdd=findViewById(R.id.btnAddbook);
        book=findViewById(R.id.book);
        back=findViewById(R.id.back);
        LV=findViewById(R.id.containerbook);
        translatex= AnimationUtils.loadAnimation(this,R.anim.translatex);
        translatey= AnimationUtils.loadAnimation(this,R.anim.translatey);
    }
    public void Load(){
        adapter.notifyDataSetChanged();
    }

}
