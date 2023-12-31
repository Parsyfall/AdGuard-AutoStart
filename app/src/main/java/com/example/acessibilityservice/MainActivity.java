package com.example.acessibilityservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button allowPermission, adguard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                try {
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra("ACTION", 2);
                    startActivity(intent);
                    // TODO: Start protection with/without app

                }catch(ActivityNotFoundException e){
                    e.printStackTrace();
                    // Send user to AdGuard market page
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "com.adguard.android"));
                    startActivity(intent);
                }

            }
        });
    }
}