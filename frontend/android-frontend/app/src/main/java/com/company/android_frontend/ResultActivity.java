package com.company.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_object_result);

        ResultActivity = this;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                // Obtain x y coordinates
                String value = intent.getStringExtra("textRecognized");
                TextView resultView = findViewById(R.id.resultRecognized);
                resultView.setText(value);
                resultView.setMovementMethod(new ScrollingMovementMethod());

                // Obtain the cropped image
                path = intent.getStringExtra("path_image");
                android.net.Uri p = android.net.Uri.parse(path);
                ((ImageView) findViewById(R.id.imageview_bitmap)).setImageURI(p);

                // Process the obtained image
                ImageView img = (ImageView) findViewById(R.id.imageview_bitmap);
                Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

                // Set the image with colored boxes
//                img.setImageBitmap(bitmap);
            }
        });


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


    }



//    private void loadImageFromStorage() {
//
//        try {
//            Intent intent = getIntent();
//            path = intent.getStringExtra("path_image");
//            File f = new File(path);
//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            ImageView img = (ImageView) findViewById(R.id.imageview_bitmap);
//            img.setImageBitmap(b);
//            img.invalidate();
//            img.forceLayout();
//            img.requestLayout();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }



//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
////
//    @Override
//    public void onPause() {
//        super.onPause();
//        finish();
//    }

}