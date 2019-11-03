package com.doan.nhom12.Database;

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
import com.doan.nhom12.AddPersonalActivity;
import com.doan.nhom12.Model.NguoiDung;

import java.util.ArrayList;

public class NguoiDungDao {
    private DatabaseReference mData;
    Context context;
    String nguoiDungID;

    //
    public NguoiDungDao(Context context) {
//
        this.mData = FirebaseDatabase.getInstance().getReference("NguoiDung");
        this.context = context;

    }

    public void insert(final NguoiDung nguoiDung) {
//
        nguoiDungID=(nguoiDung.User);
        mData.child(nguoiDungID).setValue(nguoiDung).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void delete(final NguoiDung nguoiDung) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("User").getValue(String.class).equalsIgnoreCase(nguoiDung.User)) {
                        nguoiDungID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + nguoiDungID);


                        mData.child(nguoiDungID).setValue(null)
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

    public ArrayList<NguoiDung> getAll() {
        final ArrayList<NguoiDung> list = new ArrayList<>();
//
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    NguoiDung nguoiDung = data.getValue(NguoiDung.class);
                    list.add(nguoiDung);
                }
                ((AddPersonalActivity) context).Load();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mData.addValueEventListener(listener);
        return list;
    }

    public void update(final NguoiDung nguoiDung) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("User").getValue(String.class).equalsIgnoreCase(nguoiDung.User)) {
                        if (data.child("Pass").getValue(String.class).equalsIgnoreCase(nguoiDung.Pass)) {
                            nguoiDungID = data.getKey();
                            Log.d("getKey", "onCreate: key :" + nguoiDungID);


                            mData.child(nguoiDungID).setValue(nguoiDung)
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updatepass(final NguoiDung nguoiDung) {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nguoiDungID=nguoiDung.User;
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("User").getValue(String.class).equalsIgnoreCase(nguoiDung.User)) {
                            nguoiDungID = data.getKey();
                            Log.d("getKey", "onCreate: key :" + nguoiDungID);
                            mData.child(nguoiDungID).setValue(nguoiDung)
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