package com.example.storescontrol.view.declaration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Untils;

import org.w3c.dom.Text;

public class DeclarationselectActivity extends AppCompatActivity {
     TextView textViewqualified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declarationselect);
        Untils.initTitle("完工报单",this);
        textViewqualified=findViewById(R.id.tv_qualified);
        textViewqualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeclarationselectActivity.this,DeclarationActivity.class));
            }
        });
    }
}
