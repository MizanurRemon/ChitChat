package com.example.chitchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splashscreen extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Animation fading_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        textView = (TextView) findViewById(R.id.textview);
        imageView = (ImageView) findViewById(R.id.imageview);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        fading_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        imageView.startAnimation(fading_in);
        textView.startAnimation(fading_in);

        long SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(splashscreen.this, MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}