package com.example.storescontrol.Url;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.storescontrol.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Untils {
    public static void initTitle(String title,final Activity activity) {

        TextView textViewtitle=activity.findViewById(R.id.tv_title);
        textViewtitle.setText(title);
        ImageButton imageButtonreturn=activity.findViewById(R.id.iv_return);
        imageButtonreturn.setVisibility(View.VISIBLE);
        imageButtonreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             activity.finish();
            }
        });
    }
    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.NO_WRAP);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }
}
