package com.huynhps09200.duanmau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AboutActivity extends AppCompatActivity {
    RelativeLayout about;
    ImageView back;
    Animation fadeIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        about=findViewById(R.id.about);
        back=findViewById(R.id.back);
        fadeIn= AnimationUtils.loadAnimation(this,R.anim.fadein);
        about.startAnimation(fadeIn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
