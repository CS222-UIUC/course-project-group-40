package com.company.android_frontend;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Paint;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity{

    private Button button, connectButton, OCRButton, objectButton;
    private String results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.click_button);
        TextView serverIP = findViewById(R.id.serverIP);
        TextView port = findViewById(R.id.port);

        connectButton = findViewById(R.id.ip_button);
        OCRButton = findViewById(R.id.result_button);
        OCRButton.setEnabled(false);
        objectButton = findViewById(R.id.object_detect_button);
        objectButton.setEnabled(false);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                boolean pick = true;
                if (pick) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        PickImage();
                    }
                } else {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        PickImage();
                    }
                }
            }
        });
        connectButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                try {
                    ImageView imageView = (ImageView) findViewById(R.id.cropImageView);
                    Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);

                    byte[] array = bos.toByteArray();

                    // Start a new background thread to
                    // run network connection and
                    // avoid Mainthread exception in API30
                    String ipAddress = serverIP.getText().toString();
                    String p = port.getText().toString();

                    int portNumber = Integer.parseInt(p);


                    ClientThread client = new ClientThread(array, ipAddress, portNumber);
                    FutureTask<String> task = new FutureTask<String>(client);

                    Thread thread = new Thread(task);
                    thread.start();
//                    Toast.makeText(MainActivity.this, "Image Array length:" + String.valueOf(array.length) + " bytes", Toast.LENGTH_SHORT).show();

                    thread.join();
                    // Print out
                    if (task.isDone()) {
                        results = task.get();
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
//                    thread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Button "OCR Results"
        OCRButton.setOnClickListener(view -> {
            Intent displayResult = new Intent(MainActivity.this, OCRActivity.class);
            // putExtra to pass the result
            if (results == null || results.isEmpty()) {
                // Example string
                results = "OCR:[[('test中文', [0.31053, 0.96216, 0.96236, 0.98462, " +
                        "0.84231, 0.89765])]]\tObject:[[110.346924, -4.8393164, " +
                        "575.0284, 350.96216, 'bicycle'], [128.24925, 117.09711, 315.7695, " +
                        "414.1681, 'dog'], [459.9653, 4.7818418, 619.201, 50.484783, 'car']]";
            }
            displayResult.putExtra("textRecognized", results.split("\t")[0]);
            startActivity(displayResult);
        });
        // Button "Detect object"
        objectButton.setOnClickListener(view -> {
            // TODO: Draw boxes on the image currently displayed
            ImageView imageView = findViewById(R.id.cropImageView);
            imageView.setDrawingCacheEnabled(true);
            Bitmap bitmap = imageView.getDrawingCache();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent displayResult = new Intent(MainActivity.this, ResultActivity.class);
            displayResult.putExtra("image", byteArray);
            // putExtra to pass the result
            if (results == null || results.isEmpty()) {
                // Example string
                results = "OCR:[[('test中文', [0.31053, 0.96216, 0.96236, 0.98462, " +
                        "0.84231, 0.89765])]]\tObject:[[110.346924, -4.8393164, " +
                        "575.0284, 350.96216, 'bicycle'], [128.24925, 117.09711, 315.7695, " +
                        "414.1681, 'dog'], [459.9653, 4.7818418, 619.201, 50.484783, 'car']]";
            }
            displayResult.putExtra("textRecognized", results.split("\t")[1]);
            startActivity(displayResult);
        });
    }

    private void PickImage() {
        CropImage
                .activity()
                .setGuidelines(CropImageView.Guidelines.OFF)
                .start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }


    class ClientThread implements Callable<String> {
        private volatile Socket s;
        private byte[] image;

        private String serverIP;
        private int port;
        private String m;

        public ClientThread(byte[] array, String ipAddress, int portNumber) {
            image = array;

            serverIP = ipAddress;
            port = portNumber;
        }


        @Override
        public String call() {
            String result = "";
            try {
                // send image
                s = new Socket(serverIP, port);
                InputStream inputStream = s.getInputStream();
                OutputStream outputStream = s.getOutputStream();

                outputStream.write(image);
//                outputStream.flush();
                s.shutdownOutput();

                // receive result
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                s.shutdownInput();

                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        public Socket getSocket() {
            return s;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.cropImageView)).setImageURI(result.getUri());
                // enable SEE RESULT button
                OCRButton.setEnabled(true);
                objectButton.setEnabled(true);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}