package com.example.alarmemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonCancel;
    TextView tvText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    public void startAlarm(){

    }

    public void cancelAlarm(){

    }

    public void init(){
        buttonStart = findViewById(R.id.buttonStart);
        buttonCancel = findViewById(R.id.buttonCancel);
        tvText = findViewById(R.id.tvText);
    }
}
