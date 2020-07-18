package com.ankit.easylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
ImageView imageView;
TextView textView;
Animation zoomin,middle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(1024,1024);
        //getSupportActionBar().hide();
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        zoomin= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.topanimation);
        //textView.setVisibility(View.VISIBLE);
        middle=AnimationUtils.loadAnimation(this,R.anim.middle);
        imageView.setAnimation(middle);
        textView.setAnimation(zoomin);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),second.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

}
