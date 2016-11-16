package com.example.marcos.mybrotherhoodapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsGridActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_grid);

        String title = getIntent().getStringExtra("title");
        Bitmap bitmap = getIntent().getParcelableExtra("image");

        TextView titleTextView = (TextView) findViewById(R.id.gridTitle);
        titleTextView.setText(title);

        ImageView imageView = (ImageView) findViewById(R.id.gridImage);
        imageView.setImageBitmap(bitmap);
    }
}
