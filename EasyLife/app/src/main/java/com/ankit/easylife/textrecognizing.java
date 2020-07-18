package com.ankit.easylife;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.IOException;

public class textrecognizing extends AppCompatActivity {
    TextView resultTv;
    Button choose;
    ImageView imageView;
    Button copy;
    public static final int PIC_IMAGE = 134;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textrecognizing);
        ActivityCompat.requestPermissions(textrecognizing.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE},121);

        choose = findViewById(R.id.button);
        copy=findViewById(R.id.button4);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setBackground(null);
                resultTv.setText("");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PIC_IMAGE);
            }
        });
        resultTv = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultTv.getText().toString().equals(null)){
                    Toast.makeText(getApplicationContext(), "No Text To Copy", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(resultTv.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "No Text To Copy", Toast.LENGTH_SHORT).show();
                    return;
                }
                copyToClipboard(resultTv.getText().toString());

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode!=RESULT_CANCELED && data!=null){
            if(requestCode == PIC_IMAGE){
                imageView.setImageURI(data.getData());
                InputImage image;
                try {
                    image = InputImage.fromFilePath(getApplicationContext(), data.getData());
                    TextRecognizer recognizer = TextRecognition.getClient();
                    Task<Text> result =
                            recognizer.process(image)
                                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                                        @Override
                                        public void onSuccess(Text result) {
                                            // Task completed successfully
                                            // ...
                                            String resultText = result.getText();
                                            for (Text.TextBlock block : result.getTextBlocks()) {
                                                String blockText = block.getText();
                                                Point[] blockCornerPoints = block.getCornerPoints();
                                                Rect blockFrame = block.getBoundingBox();
                                                for (Text.Line line : block.getLines()) {
                                                    String lineText = line.getText();
                                                    Point[] lineCornerPoints = line.getCornerPoints();
                                                    Rect lineFrame = line.getBoundingBox();
                                                    for (Text.Element element : line.getElements()) {
                                                        String elementText = element.getText();
                                                        Point[] elementCornerPoints = element.getCornerPoints();
                                                        Rect elementFrame = element.getBoundingBox();
                                                    }
                                                }
                                            }
                                            resultTv.setText(resultText);
                                        }
                                    })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Task failed with an exception
                                                    // ...
                                                }
                                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }}
        else {
            Toast.makeText(this, "Operation Cancelled.", Toast.LENGTH_SHORT).show();
        }
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
