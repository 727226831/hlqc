package com.example.storescontrol.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;


public class PortActivity extends BaseActivity{
    TextView titleTv;
    private Button buttonok;
    private EditText editText;
    private ImageButton imageButton;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port);
        titleTv=findViewById(R.id.tv_title);
        titleTv.setText("设置");
        buttonok=findViewById(R.id.b_ok);
        editText=findViewById(R.id.et_port);
        imageButton=findViewById(R.id.iv_return);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         sharedPreferences= getSharedPreferences("sp", Context.MODE_PRIVATE);

         editText.setText(sharedPreferences.getString("port",""));

        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")&& Patterns.WEB_URL.matcher(editText.getText().toString()).matches()){
                    Request.URL=editText.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putString("port",editText.getText().toString());
                    editor.commit();
                    Toast.makeText(PortActivity.this,"地址已改变",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(PortActivity.this,"地址不正确",Toast.LENGTH_LONG).show();
                }


            }
        });
    }

}
