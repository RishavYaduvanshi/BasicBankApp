package com.rishavyaduvanshi.banking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplachScreen extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);

        imageView=findViewById(R.id.i1);
        textView=findViewById(R.id.t1);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation top = AnimationUtils.loadAnimation(this,R.anim.logofirst);
        Animation down = AnimationUtils.loadAnimation(this,R.anim.textanim);

        imageView.setAnimation(top);
        textView.setAnimation(down);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplachScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}