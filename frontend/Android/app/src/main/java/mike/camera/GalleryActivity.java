package mike.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.List;

import mike.camera.MainActivity;

public class GalleryActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
//        List<Bitmap> photoList = MainActivity.getPhotos();
//        imageView = findViewById(R.id.imageView);
//        if (!photoList.isEmpty()) {
//            imageView.setImageBitmap(photoList.get(0));
//        }
    }
}