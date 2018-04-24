package com.heinika.imagetestdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String imageSrc = getIntent().getStringExtra("imageSrc");
        ImageView imageView = findViewById(R.id.imageView_full);
        Glide.with(this).load(imageSrc).into(imageView);
    }
}
