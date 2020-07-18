package com.ankit.easylife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class second extends AppCompatActivity {
Button button,textr,brcode,prr,cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button =findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),translating.class);
                startActivity(intent);
            }
        });
        textr=findViewById(R.id.button5);
        textr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),textrecognizing.class);
                startActivity(intent);
            }
        });
        brcode=findViewById(R.id.button6);
        brcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),barcoding.class);
                startActivity(intent);
            }
        });
        prr=findViewById(R.id.button7);
        prr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),privacing.class);
                startActivity(intent);
            }
        });
        cnt=findViewById(R.id.button8);
        cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),contacting.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(second.this)
                .setTitle("Alert")
                .setMessage("Do you want to exit App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
