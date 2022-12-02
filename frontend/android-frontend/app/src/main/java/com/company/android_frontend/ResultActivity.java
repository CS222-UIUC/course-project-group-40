package com.company.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

public class ResultActivity extends AppCompatActivity{
    public static ResultActivity ResultActivity;
    String path;
    String value;
    Bitmap bitmap;
    TextView resultView;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_object_result);
        img = findViewById(R.id.imageview_bitmap);


        ResultActivity = this;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                // Obtain x y coordinates
                value = intent.getStringExtra("textRecognized");
                resultView = findViewById(R.id.resultRecognized);
                resultView.setMovementMethod(new ScrollingMovementMethod());

                // Obtain the cropped image
                path = intent.getStringExtra("path_image");
                android.net.Uri p = android.net.Uri.parse(path);
                ((ImageView) findViewById(R.id.imageview_bitmap)).setImageURI(p);

                // Process the obtained image
                ImageView img = (ImageView) findViewById(R.id.imageview_bitmap);
                bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

                // Draw lines
                String[] arrOfStr = value.split(",", 6);
                float left, top, right, bottom;
                String lefts, tops, rights, bottoms, tag;
                lefts = arrOfStr[0];
                tops = arrOfStr[1];
                rights = arrOfStr[2];
                bottoms = arrOfStr[3];

                left = Float.parseFloat(lefts.substring(9));
                top = Float.parseFloat(tops);
                right = Float.parseFloat(rights);
                bottom = Float.parseFloat(bottoms);

                resultView.setText(arrOfStr[4].substring(2, arrOfStr[4].length() - 3));

                Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
                Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Canvas canvas = new Canvas(mutableBitmap);

                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLUE);
                paint.setStrokeWidth(3);
                canvas.drawRect(left, top, right, bottom, paint);
                img.setImageBitmap(mutableBitmap);

            }
        });






    }



}