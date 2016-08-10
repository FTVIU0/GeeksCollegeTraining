package com.nlte.simplephotobrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SimplePhotoBrowserActivity extends AppCompatActivity {
    private ImageView ivPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_photo_browser);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        ivPicture = (ImageView) findViewById(R.id.ivPicture);
        ivPicture.setImageURI(uri);
    }
}
