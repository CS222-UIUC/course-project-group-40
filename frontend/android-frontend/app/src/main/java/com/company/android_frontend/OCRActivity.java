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

        TextView resultView = findViewById(R.id.textRecognized);
        TextView probView = findViewById(R.id.probabilities);
        resultView.setText(parseResults(value)[0]);
        probView.setText(parseResults(value)[1]);
        resultView.setMovementMethod(new ScrollingMovementMethod());
        probView.setMovementMethod(new ScrollingMovementMethod());
    }

    public String[] parseResults(String message) {
        // recognized text
        String text = "";

        int wordStart = 8;
        int wordEnd = wordStart;
        int i = 0;
        for (; i < message.length() - 1; i++) {
            if (message.charAt(i) == '\'' && message.charAt(i + 1) == ',') {
                break;
            }
        }
        wordEnd = i;
        text = message.substring(wordStart, wordEnd);
        text += "\n";

        // each char with probability
        String probability = "";
        i += 4;
        int count = 0;
        int probabilityStart = i;
        for (; i < message.length() - 1; i++) {
            if (message.charAt(i) == ']' && message.charAt(i + 1) == ')') {
                probability += text.substring(count, count + 1);
                probability += " :  ";
                probability += message.substring(probabilityStart, i);
                probability += "\n";
                break;
            }
            if (message.charAt(i) == ',') {
                probability += text.substring(count, count + 1);
                probability += " :  ";
                probability += message.substring(probabilityStart, i);
                probability += "\n";
                probabilityStart = i + 2;
                count += 1;
            }
        }
        return new String[]{text, probability};
    }
}
