package com.example.storescontrol.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.storescontrol.R;
import com.example.storescontrol.bean.DetailsBean;


public class BaseActivity extends AppCompatActivity {
    public static   String acccode,usercode;
    public  AlertDialog dialog;
    //type 1客户 2供应商
    public static  int type=1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AlertDialog.Builder builder=new AlertDialog.Builder(this).setView(R.layout.layout_progress);
        dialog=builder.create();

    }

    public void clearCheckdata() {
        SharedPreferences   sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("checklist","").commit();
        sharedPreferences.edit().putString("checkscan","").commit();
        sharedPreferences.edit().putString("detailsBean","").commit();
    }


}
