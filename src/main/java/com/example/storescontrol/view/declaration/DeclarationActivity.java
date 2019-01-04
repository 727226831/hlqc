package com.example.storescontrol.view.declaration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Untils;

public class DeclarationActivity extends AppCompatActivity {
    Button buttonmaterial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration);
        Untils.initTitle("完工报单",this);
        buttonmaterial=findViewById(R.id.b_material);
        buttonmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeclarationActivity.this,MaterialActivity.class));
            }
        });

    }
}
