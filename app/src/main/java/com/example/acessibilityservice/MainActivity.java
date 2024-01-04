package com.example.acessibilityservice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button allowPermission;
    private TextView serviceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceStatus = findViewById(R.id.serviceStatus);

        allowPermission = findViewById(R.id.allowPermission);
        allowPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyAccessibilityService.isRunning()){
            serviceStatus.setText("Running");
            serviceStatus.setTextColor(Color.parseColor("#008000"));   // Green
        }else{
            serviceStatus.setText("Stoped");
            serviceStatus.setTextColor(Color.parseColor("#FF0000"));   // Red
        }

    }
}