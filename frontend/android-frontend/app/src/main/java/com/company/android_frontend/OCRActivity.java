package com.company.android_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OCRActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String value = intent.getStringExtra("textRecognized");

        TextView resultView = findViewById(R.id.resultRecognized);
        resultView.setText(value);
        resultView.setMovementMethod(new ScrollingMovementMethod());

    }
}

