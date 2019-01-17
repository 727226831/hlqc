package com.example.storescontrol.view.declaration;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.databinding.ActivityReportBinding;
import com.example.storescontrol.view.BaseActivity;

/**
 * 完工报单
 */
public class ReportActivity extends BaseActivity {
    ActivityReportBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_report);
        Untils.initTitle("完工报单",this);
        binding.bMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this,MaterialActivity.class));
            }
        });

          binding.etPeople.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

              }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {

              }

              @Override
              public void afterTextChanged(Editable s) {
                  if(handler.hasMessages(0)){
                      handler.removeMessages(0);
                  }
                  handler.sendEmptyMessageDelayed(0,1000);


              }
          });


    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            binding.etPeople.setText(binding.etPeople.getText().toString()+",");
            handler.removeMessages(0);
            binding.etPeople.requestFocus();
            binding.etPeople.setSelection(binding.etPeople.getText().length());

        }
    };


}
