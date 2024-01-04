package com.example.acessibilityservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button allowPermission, adguard;
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

        adguard = findViewById(R.id.adguard);
        adguard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.adguard");
                if (intent == null) { // Package not found
                    // Send user to AdGuard market page
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "com.adguard.android"));
                    startActivity(intent);
                }

                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra("ACTION", 2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyAccessibilityService.isRunning()){
            serviceStatus.setText("Running");
            serviceStatus.setTextColor(Color.parseColor("0x008000"));   // Green
        }else{
            serviceStatus.setText("Stoped");
            serviceStatus.setTextColor(Color.parseColor("0xFF0000"));   // Red
        }

    }
}