package com.example.ina_uzu.saewoo.letter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ina_uzu.saewoo.R;

public class LetterMenuActivity extends Activity {
    TextView bt_send, bt_read;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);

        //LetterGetRequest letterGetRequest = new LetterGetRequest(getApplicationContext());
        LetterInfo.letterList=LetterRequest.GetRequest(getApplicationContext());

        bt_send = findViewById(R.id.bt_send);
        bt_read = findViewById(R.id.bt_read);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LetterMenuActivity.this, LetterWriteActivity.class);
                startActivity(intent);
            }
        });

        bt_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LetterMenuActivity.this, LetterListActivity.class);
                startActivity(intent);

            }
        });

    }
}
