package com.huynhps09200.duanmau.Adapter;

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
import com.huynhps09200.duanmau.BillManagerActivity;
import com.huynhps09200.duanmau.Database.HoaDonChiTietDao;
import com.huynhps09200.duanmau.Model.HoaDonChiTiet;
import com.huynhps09200.duanmau.Model.Sach;
import com.huynhps09200.duanmau.R;

import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder>{
    ArrayList<HoaDonChiTiet> list;
    Context context;
    HoaDonChiTietDao hoaDonChiTietDao;
    HoaDonAdapter adapter;
    Dialog dialog;
    String spn;
    DatabaseReference mData;
    public HoaDonAdapter( Context context,ArrayList<HoaDonChiTiet> list) {
        this.list = list;
        this.context = context;
        hoaDonChiTietDao=new HoaDonChiTietDao(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View itemView=layoutInflater.inflate(R.layout.bill_lv,viewGroup,false);
        mData= FirebaseDatabase.getInstance().getReference();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final HoaDonChiTiet hoaDonChiTiet=list.get(i);
        viewHolder.TvmaHD.setText(hoaDonChiTiet.maHoadon);
        viewHolder.Ngay.setText(hoaDonChiTiet.ngayMua);
        viewHolder.tenSach.setText(hoaDonChiTiet.maSach);
        viewHolder.SL.setText("Số lượng: "+hoaDonChiTiet.soLuongMua);
        viewHolder.TT.setText("Thành tiền: "+hoaDonChiTiet.tongTien+" VNĐ");
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
                        delete(hoaDonChiTiet);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogHDCT();
                final EditText edtSL = dialog.findViewById(R.id.edtSL);
                final Spinner spnMaSach = dialog.findViewById(R.id.SpnmaSach);
                final TextView TvGia = dialog.findViewById(R.id.TvGia);
                final TextView TvTT = dialog.findViewById(R.id.TvTT);
                final TextView TvtenSach = dialog.findViewById(R.id.TvTenSach);
                Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                Button btncan = dialog.findViewById(R.id.btnCancel);
                edtSL.setText(""+hoaDonChiTiet.soLuongMua);
                mData.child("Sach").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final List<String> maSachlist = new ArrayList<>();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String tenTL = data.child("maSach").getValue(String.class);
                            if (tenTL != null) {
                                maSachlist.add(tenTL);
                            }
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(context, R.layout.lv_tentl, R.id.TVTENTL, maSachlist);
                        spnMaSach.setAdapter(arrayAdapter);
                        spnMaSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                spn = adapterView.getItemAtPosition(position).toString();
                                String masach = spn;
                                mData.child("Sach").orderByChild("maSach").equalTo(masach).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Sach sach = dataSnapshot.getChildren().iterator().next().getValue(Sach.class);
                                        int gia = sach.giaBan;
                                        String tenSach = sach.tenSach;
                                        TvtenSach.setText(tenSach);
                                        TvGia.setText(gia + "");
                                        if (edtSL.getText().toString().length() == 0) {
                                            TvTT.setText(gia + "");
                                        } else {
                                            try {
                                                int sl = Integer.parseInt(edtSL.getText().toString());
                                                int TT = sl * gia;
                                                TvTT.setText(TT + "");
                                            } catch (NumberFormatException ex) {

                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
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
//


                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maHD=hoaDonChiTiet.maHoadon;
                        String ngaymua=hoaDonChiTiet.ngayMua;
                        String tenSach = TvtenSach.getText().toString();
                        String gia = TvGia.getText().toString();
                        int Sl = Integer.parseInt(edtSL.getText().toString());
                        String TT = TvTT.getText().toString();
                        HoaDonChiTiet hoaDonChiTiet1 = new HoaDonChiTiet(maHD, ngaymua , tenSach, gia, TT, Sl);
                        if ( edtSL.getText().length() != 0) {
                            hoaDonChiTietDao.update(hoaDonChiTiet1);
                            dialog.dismiss();
                            Toast.makeText(context, "thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btncan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
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
        TextView TvmaHD,Ngay,tenSach,SL,TT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edit=itemView.findViewById(R.id.editbook);
            delete=itemView.findViewById(R.id.deletebook);
            TvmaHD=itemView.findViewById(R.id.TvMabill);
            Ngay=itemView.findViewById(R.id.TvNgay);
            tenSach=itemView.findViewById(R.id.TvTenSach);
            SL=itemView.findViewById(R.id.TvSL);
            TT=itemView.findViewById(R.id.TvTT);
        }
    }
    public void delete(HoaDonChiTiet hoaDonChiTiet){
        hoaDonChiTietDao.delete(hoaDonChiTiet);
        list.clear();

    }
    public void showdialogHDCT() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.hdct_dialog);
        dialog.setCancelable(false);

        dialog.show();

    }
}

