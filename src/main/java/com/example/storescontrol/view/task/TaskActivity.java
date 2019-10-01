package com.example.storescontrol.view.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.view.BaseActivity;

public class TaskActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Untils.initTitle("详细单据",this);
    }
}
