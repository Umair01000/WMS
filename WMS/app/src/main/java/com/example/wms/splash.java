package com.example.wms;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;


public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //Delay for a few second before starting sign up activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splash.this, choice.class);
                startActivity(intent);
                finish();
            }
        },2000);


    }
}