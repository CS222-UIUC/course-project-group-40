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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_object_result);

        Intent intent = getIntent();
        String value = intent.getStringExtra("textRecognized");
        path = intent.getStringExtra("path_image");
        loadImageFromStorage(path);

//        String[] arrOfStr = value.split(",", 6);
//        float left, top, right, bottom;
//        String lefts, tops, rights, bottoms;
//        lefts = arrOfStr[0];
//        tops = arrOfStr[1];
//        rights = arrOfStr[2];
//        bottoms = arrOfStr[3];
//
//        left = Float.parseFloat(lefts.substring(9));
//        top = Float.parseFloat(tops);
//        right = Float.parseFloat(rights);
//        bottom = Float.parseFloat(bottoms);

        TextView resultView = findViewById(R.id.resultRecognized);
        //Bundle extras = getIntent().getExtras();
        //byte[] byteArray = extras.getByteArray("image");
        //Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        //ImageView imageview_bitmap = findViewById(R.id.imageview_bitmap);
        //imageview_bitmap.setImageBitmap(bmp);
//        Bitmap workingBitmap = Bitmap.createBitmap(bmp);
//        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        Canvas canvas = new Canvas(mutableBitmap);
//        Paint paint = new Paint(R.id.imageview_bitmap);
//        canvas.drawRect(left, top, right, bottom, paint);


//        resultView.setText(arrOfStr[4].substring(2, arrOfStr[4].length() - 2));
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
    public void onResume() {
        super.onResume();
        ImageView img = (ImageView) findViewById(R.id.imageview_bitmap);
        img.setImageBitmap(null);
        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}