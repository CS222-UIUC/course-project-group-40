package com.company.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ResultActivity extends AppCompatActivity{
    String path;
    DrawView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_object_result);
        drawView = (DrawView) findViewById(R.id.imageview_bitmap);
        ImageView img = (ImageView) findViewById(R.id.imageview_bitmap);


        Intent intent = getIntent();
        String value = intent.getStringExtra("textRecognized");
        path = intent.getStringExtra("path_image");
        loadImageFromStorage(path);

        String[] arrOfStr = value.split(",", 6);
        float left, top, right, bottom;
        String lefts, tops, rights, bottoms;
        lefts = arrOfStr[0];
        tops = arrOfStr[1];
        rights = arrOfStr[2];
        bottoms = arrOfStr[3];

        left = Float.parseFloat(lefts.substring(9));
        top = Float.parseFloat(tops);
        right = Float.parseFloat(rights);
        bottom = Float.parseFloat(bottoms);

        TextView resultView = findViewById(R.id.resultRecognized);

        Canvas canvas = new Canvas();
        Paint paint = new Paint(R.id.imageview_bitmap);
        canvas.drawRect(left, top, right, bottom, paint);


        resultView.setText(arrOfStr[4].substring(2, arrOfStr[4].length() - 2));
        resultView.setMovementMethod(new ScrollingMovementMethod());

    }

    private void loadImageFromStorage(String path) {

        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = (ImageView) findViewById(R.id.imageview_bitmap);
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ImageView img = (ImageView) findViewById(R.id.imageview_bitmap);
        img.setImageBitmap(null);
    }
}