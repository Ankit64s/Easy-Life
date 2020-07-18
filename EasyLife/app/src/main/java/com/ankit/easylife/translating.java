package com.ankit.easylife;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class translating extends AppCompatActivity {
EditText editText;
TextView textView;
Button tr,clear,copy;
Button model;
Boolean isDownload=false;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translating);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView3);
        tr=findViewById(R.id.button2);
        clear=findViewById(R.id.button3);
        copy=findViewById(R.id.button4);
        model=findViewById(R.id.download);
        sharedPreferences=this.getSharedPreferences("application", Context.MODE_PRIVATE);


        // Create an English-German translator:
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.HINDI)
                        .build();
        final Translator englishGermanTranslator =
                Translation.getClient(options);


        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(translating.this, "Model Will Be Downloaded in background on Active Internet Connection.", Toast.LENGTH_SHORT).show();
                DownloadConditions conditions = new DownloadConditions.Builder()
                        //.requireWifi()
                        .build();
                englishGermanTranslator.downloadModelIfNeeded(conditions)
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void v) {
                                        // Model downloaded successfully. Okay to start translating.
                                        // (Set a flag, unhide the translation UI, etc.)
                                        final SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putBoolean("Download",true);
                                        editor.apply();
                                        Toast.makeText(translating.this, "Model Downloaded.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Model couldn’t be downloaded or other internal error.
                                        // ...
                                       // isDownload=false;

                                    }
                                });

            }
        });



        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDownload=getRoll();
                String text=editText.getText().toString();
                if(isDownload==true){
                    if(TextUtils.isEmpty(editText.getText().toString())){
                        Toast.makeText(translating.this, "Please Enter Text First", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    englishGermanTranslator.translate(text)
                            .addOnSuccessListener(
                                    new OnSuccessListener<String>() {
                                        @Override
                                        public void onSuccess(@NonNull String translatedText) {
                                            // Translation successful.
                                            textView.setText(translatedText);
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Error.
                                            // ...
                                        }
                                    });



                }
                else{
                    Toast.makeText(translating.this, "Please Download Model First.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editText.getText().clear();
               textView.setText(null);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.getText().toString().equals(null)){
                    Toast.makeText(getApplicationContext(), "No Text To Copy", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(textView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "No Text To Copy", Toast.LENGTH_SHORT).show();
                    return;
                }
                copyToClipboard(textView.getText().toString());
            }
        });
    }

    private Boolean getRoll() {
            Boolean var;
            SharedPreferences sharedPreferences=getSharedPreferences("application", Context.MODE_PRIVATE);
            var=sharedPreferences.getBoolean("Download",false);
            return var;
    }

    public void copyToClipboard(String copyText) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(copyText);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData
                    .newPlainText("Your OTP", copyText);
            clipboard.setPrimaryClip(clip);
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                "Your Text is copied", Toast.LENGTH_SHORT);
        toast.show();
        //displayAlert("Your OTP is copied");
    }
}
