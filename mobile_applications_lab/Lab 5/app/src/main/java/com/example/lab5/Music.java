package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Music extends AppCompatActivity {

    Button mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mPlayButton = (Button) findViewById(R.id.play);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                File audio = new File(Environment.getExternalStorageDirectory().getPath() + "/Music/Lab5.mp3");
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(audio).toString());
                String mimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                Uri uri = FileProvider.getUriForFile(Music.this, BuildConfig.APPLICATION_ID + ".provider",audio);
                i.setDataAndType(uri, mimeType);

                if (audio != null) {
                    Toast.makeText(getApplicationContext(), Uri.fromFile(audio).toString(), Toast.LENGTH_SHORT).show();
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "suck", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}