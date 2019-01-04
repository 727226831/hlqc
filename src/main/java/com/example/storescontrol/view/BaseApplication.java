package com.example.storescontrol.view;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

         // 初始化Bugly
       // CrashReport.initCrashReport(context, "8a29361815", true, strategy);
        Bugly.init(getApplicationContext(), "8a29361815", false);

    }
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
