package com.nlte.joke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvPostDate;
    private TextView tvPostContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvPostDate = (TextView) findViewById(R.id.tvPostDate);
        tvPostContent = (TextView) findViewById(R.id.tvPostContent);

        Intent intent = getIntent();
        String postTitle = intent.getStringExtra("postTitle");
        String postDate = intent.getStringExtra("postDate");
        String postContent = intent.getStringExtra("postContent");

        tvTitle.setText(Html.fromHtml(postTitle));
        tvPostDate.setText(Html.fromHtml(postDate));
        tvPostContent.setText(Html.fromHtml(postContent));

    }
}
