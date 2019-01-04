package com.example.storescontrol.view.declaration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Untils;

public class MaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        Untils.initTitle("投料明细",this);
    }
}
