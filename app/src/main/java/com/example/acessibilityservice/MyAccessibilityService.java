package com.example.acessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.List;

// TODO: Run service independently in background (bound or run in a separate thread or in foreground?)
public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";
    private static boolean isRunning = false;
    private boolean isAdGuardRunning = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "onAccessibilityEvent: ");
        String packageName = event.getPackageName().toString();
        if (packageName.equals("com.google.android.googlequicksearchbox") && !isAdGuardRunning) {
            startAdGuard();
            isAdGuardRunning = true;
        }
        Log.e(TAG, "app name is : " + packageName);

    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt: something went wrong");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        isRunning = true;
        Log.e(TAG, "onServiceConnected: ");
    }

    public static boolean isRunning() {
        return isRunning;
    }

    private void startAdGuard() {
        Intent intent = this.getPackageManager().getLaunchIntentForPackage("com.adguard.android");
        if (intent == null) {
            Log.e(TAG, "startAdGuard: Package not found");
            // Send user to AdGuard market page
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.adguard.android"));
            startActivity(intent);
            return;
        }
        // Start AdGuard
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra("ACTION", 2);
        startActivity(intent);
        Log.e(TAG, "startAdGuard: Activity started");
    }

}
