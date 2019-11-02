package com.huynhps09200.duanmau.Database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huynhps09200.duanmau.BillManagerActivity;
import com.huynhps09200.duanmau.Model.HoaDonChiTiet;
import com.huynhps09200.duanmau.SalesActivity;

import java.util.ArrayList;

public class HoaDonChiTietDao {
    private DatabaseReference mData;
    Context context;
    String HoadonchitietID;

    //
    public HoaDonChiTietDao(Context context) {
//
        this.mData = FirebaseDatabase.getInstance().getReference("HoaDonChiTiet");
        this.context = context;

    }

    public void insert(HoaDonChiTiet hoaDonChiTiet) {
//
        HoadonchitietID = mData.push().getKey();
        mData.child(HoadonchitietID).setValue(hoaDonChiTiet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void delete(final HoaDonChiTiet hoaDonChiTiet) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maHoadon").getValue(String.class).equalsIgnoreCase(hoaDonChiTiet.maHoadon)) {
                        HoadonchitietID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + HoadonchitietID);


                        mData.child(HoadonchitietID).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

     public ArrayList<HoaDonChiTiet> getAll() {
        final ArrayList<HoaDonChiTiet> list = new ArrayList<>();
//
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDonChiTiet hoaDonChiTiet = data.getValue(HoaDonChiTiet.class);
                    list.add(hoaDonChiTiet);
                }
                ((BillManagerActivity) context).Load();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mData.addValueEventListener(listener);
        return list;
    }
    public ArrayList<HoaDonChiTiet> getAllSales() {
        final ArrayList<HoaDonChiTiet> list = new ArrayList<>();
//
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDonChiTiet hoaDonChiTiet = data.getValue(HoaDonChiTiet.class);
                    list.add(hoaDonChiTiet);
                }
                ((SalesActivity) context).Load();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mData.addValueEventListener(listener);
        return list;
    }

    public void update(final HoaDonChiTiet hoaDonChiTiet) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maHoadon").getValue(String.class).equalsIgnoreCase(hoaDonChiTiet.maHoadon)) {

                        HoadonchitietID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + HoadonchitietID);


                        mData.child(HoadonchitietID).setValue(hoaDonChiTiet)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "update thành công", Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "update thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

