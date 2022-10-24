package mike.camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button cameraButton;
    Button galleryButton;
    static List<Bitmap> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera);

        imageView = findViewById(R.id.imageView);
        cameraButton = findViewById(R.id.button_camera);
        galleryButton = findViewById(R.id.button_gallery);

        // Request for camera runtime permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                    Manifest.permission.CAMERA
            }, 100);
        }

        // set action for camera button
        cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 100);
        });

        // set action for gallery button
        galleryButton.setOnClickListener(v -> {
            Intent startGallery = new Intent(this, GalleryActivity.class);
            startActivity(startGallery);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            assert data != null;
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
//            photoList.add(bitmap);
        }
    }

    public static List<Bitmap> getPhotos() {
        return photoList;
    }
}