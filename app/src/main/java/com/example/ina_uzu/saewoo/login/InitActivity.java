package com.example.ina_uzu.saewoo.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ina_uzu.saewoo.R;

public class InitActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        Button bt_init = findViewById(R.id.bt_init);

        bt_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_bucket = "bucketlist";
                String name_letter = "letter";


            }
        });

    }
}
