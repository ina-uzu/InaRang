package com.example.ina_uzu.saewoo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.bucketlist.BucketGetRequest;
import com.example.ina_uzu.saewoo.bucketlist.BucketListActivity;
import com.example.ina_uzu.saewoo.calendar.CalendarActivity;
import com.example.ina_uzu.saewoo.letter.LetterMenuActivity;
import com.example.ina_uzu.saewoo.worktable.WorktableActivity;

public class MainActivity extends Activity {

    TextView bt_letter, bt_bucket, bt_cal,bt_worktable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BucketGetRequest bucketGetRequest = new BucketGetRequest(getApplicationContext());

        bt_cal = findViewById(R.id.bt_cal);
        bt_worktable = findViewById(R.id.bt_worktable);
        bt_letter = findViewById(R.id.bt_letter);
        bt_bucket = findViewById(R.id.bt_bucket);

        bt_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        bt_worktable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WorktableActivity.class);
                startActivity(intent);
            }
        });

        bt_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LetterMenuActivity.class);
                startActivity(intent);

            }
        });

        bt_bucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BucketListActivity.class);
                startActivity(intent);
            }
        });
    }
}
