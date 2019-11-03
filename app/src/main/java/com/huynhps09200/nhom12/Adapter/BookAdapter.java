package com.huynhps09200.nhom12.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huynhps09200.nhom12.Database.SachDao;
import com.huynhps09200.nhom12.Model.Sach;
import com.huynhps09200.duanmau.R;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{
    ArrayList<Sach> list;
    Context context;
    SachDao sachDao;
    BookAdapter adapter;
    DatabaseReference mData;
    String spn;
    public BookAdapter( Context context,ArrayList<Sach> list) {
        this.list = list;
        this.context = context;
        sachDao=new SachDao(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View itemView=layoutInflater.inflate(R.layout.book_lv,viewGroup,false);
        mData= FirebaseDatabase.getInstance().getReference();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Sach sach = list.get(i);
        viewHolder.TvTensach.setText(sach.tenSach);
        viewHolder.TvSL.setText("Số lượng: "+sach.soLuong);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc muốn xóa !!!");
                builder.setCancelable(false);

                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(context, "Xóa không thành công ", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Xóa thành công ", Toast.LENGTH_LONG).show();
                        delete(sach);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.book_dialog);
                dialog.setCancelable(false);
                dialog.show();
                final EditText edtIdbook=dialog.findViewById(R.id.edtIdbook);
                final EditText edttenSach=dialog.findViewById(R.id.edtNameBook);
                final Spinner edtTL=dialog.findViewById(R.id.SpTheloai);
                final EditText edtTG=dialog.findViewById(R.id.edtAuthor);
                final EditText edtNXB=dialog.findViewById(R.id.edtNXB);
                final EditText edtSL=dialog.findViewById(R.id.edtSl);
                final EditText edtGIA=dialog.findViewById(R.id.edtgia);
                Button btnConfirm=dialog.findViewById(R.id.btnConfirm);
                Button btncancel=dialog.findViewById(R.id.btnCancel);
                edtIdbook.setText(sach.maSach);
                edttenSach.setText(sach.tenSach);
                edtTG.setText(sach.tacGia);
                edtSL.setText(""+sach.soLuong);
                edtGIA.setText(""+ sach.giaBan);
                edtNXB.setText(sach.NXB);
                edtTL.setPrompt(sach.tenTheLoai);
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
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(context, R.layout.lv_tentl,R.id.TVTENTL, tenTLlist);
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
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
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

                        Sach sach=new Sach(maSach,tenTL,tenSach,tacgia,nXB,gia,SL);
                        if(edtGIA.getText().length()!=0 && edtIdbook.getText().length()!=0 && edtNXB.getText().length()!=0 && edtSL.getText().length()!=0 && edtTG.getText().length()!=0 && edttenSach.getText().length()!=0){
                            sachDao.update(sach);
                            dialog.dismiss();
                            Toast.makeText(context, "thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                        }}
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView edit,delete;
        TextView TvTensach,TvSL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edit=itemView.findViewById(R.id.editbook);
            delete=itemView.findViewById(R.id.deletebook);
            TvTensach=itemView.findViewById(R.id.Tvtenbook);
            TvSL=itemView.findViewById(R.id.TvTenSach);
        }
    }
    public void delete(Sach sach){
        sachDao.delete(sach);
        list.clear();

    }

}

