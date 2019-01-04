package com.example.storescontrol.Url;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.storescontrol.R;

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
}
