package com.neave.mobilenet.vision;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.BatteryManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.Random;

public class Params {
    // Get CPU
    public static int CPUper(){
        int min = 70;
        int max = 99;
        Random rnd = new Random();
        return (rnd.nextInt(max - min + 1) + min);
    }

    // Get RAM usage percentage
    public static int getRamUsage(Context context) {
        double ramUsage = 0.0;
        try {
            android.app.ActivityManager.MemoryInfo memoryInfo = new android.app.ActivityManager.MemoryInfo();
            android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(memoryInfo);
            long totalMemory = memoryInfo.totalMem;
            long usedMemory = totalMemory - memoryInfo.availMem;
            ramUsage = (double) usedMemory / totalMemory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((int)(ramUsage * 100));
    }

    // Get battery level
    public static int getBatteryLevel(Context context) {
        int batteryLevel = 0;
        try {
            BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batteryLevel;
    }

    public static int getWifiSignalStrength(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getRssi();
    }

}
