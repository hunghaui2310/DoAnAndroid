package com.huynhps09200.duanmau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Start extends AppCompatActivity {
    ImageView ongvang,fpt;
    Animation fadeIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ongvang=findViewById(R.id.ongvang);
        fpt=findViewById(R.id.img);
        fadeIn= AnimationUtils.loadAnimation(this,R.anim.fadein);
        ongvang.startAnimation(fadeIn);
        fpt.startAnimation(fadeIn);
        Thread bamgio=new Thread(){
            public void run()
            {
                try {
                    sleep(2000);
                } catch (Exception e) {

                }
                finally
                {
                    Intent i=new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                }
            }
        };
        bamgio.start();
    }
    //sau khi chuyển sang màn hình chính, kết thúc màn hình chào
    protected void onPause(){
        super.onPause();
        finish();
    }
    }

