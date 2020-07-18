package com.ankit.easylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class contacting extends AppCompatActivity {
    Button fb,insta,yt;
    String facebookId,urlPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacting);
        fb=findViewById(R.id.button2);
        insta=findViewById(R.id.button3);
        yt=findViewById(R.id.button5);
        facebookId = "fb://page/102407484875881";
        urlPage = "https://www.facebook.com/appsbyani";

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId )));
                } catch (Exception e) {
                    //Open url web page.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.instagram.com/appsbyani/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });
        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL_YOUTUBE = "https://www.youtube.com/channel/UC8aoFFqxwabPIVAW-dOlL-A";
                String URL_YOUTUBE_INAPP = "vnd.youtube.com/channel/UC8aoFFqxwabPIVAW-dOlL-A";

                try{
                    //here we try to open the link in app
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(URL_YOUTUBE_INAPP));
                    startActivity(intent);
                    //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL_YOUTUBE_INAPP)))
                }catch ( Exception e) {
                    //the app isn't available: we open in browser`
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(URL_YOUTUBE));
                    startActivity(intent);
                    //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL_YOUTUBE)))
                }

            }
        });
    }
}
