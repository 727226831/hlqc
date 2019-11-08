package com.example.storescontrol.view;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.storescontrol.service.DemoIntentService;
import com.example.storescontrol.service.DemoPushService;
import com.igexin.sdk.PushManager;
import com.tencent.bugly.Bugly;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BaseApplication extends Application {

    private static DemoHandler handler;
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();

         // 初始化Bugly
        Bugly.init(getApplicationContext(), "8a29361815", false);
        if (handler == null) {
            handler = new DemoHandler();
        }
        PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);

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
    public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    public static class DemoHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    payloadData.append((String) msg.obj);
                    payloadData.append("\n");

                    break;

                case 1:

                    break;
            }
        }
    }

}
