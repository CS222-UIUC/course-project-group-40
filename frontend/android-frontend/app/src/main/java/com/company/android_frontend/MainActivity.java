package com.company.android_frontend;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Button button, connectButton, resultButton;
    private Socket socket;
    private static final int SERVERPORT = 1080;
    private static final String SERVER_IP = "127.0.0.1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new ClientThread()).start();

        button = findViewById(R.id.click_button);
        TextView serverIP = (TextView) findViewById(R.id.serverIP);
        TextView port = (TextView) findViewById(R.id.port);

        connectButton = findViewById(R.id.ip_button);
        resultButton = findViewById(R.id.result_button);
        resultButton.setEnabled(false);

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
                    bmp.compress(Bitmap.CompressFormat.PNG, 0, bos);

                    byte[] array = bos.toByteArray();

                    OutputStream outputStream = socket.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(outputStream);
                    dos.writeInt(array.length);
                    dos.write(array, 0 , array.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        resultButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent displayResult = new Intent(MainActivity.this, ResultActivity.class);
                // TODO: putExtra to pass the result
                displayResult.putExtra("textRecognized", "This is the recognized text");
                startActivity(displayResult);
            }
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
        requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 100);
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

    class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                resultButton.setEnabled(true);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}