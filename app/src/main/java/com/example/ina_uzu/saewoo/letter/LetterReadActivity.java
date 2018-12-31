package com.example.ina_uzu.saewoo.letter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.login.LoginInfo;

public class LetterReadActivity extends Activity{
    DBLetterHelper db;
    ImageView img_title;
    TextView tv_date, tv_title, tv_cont;
    Button bt_prev;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letterread_item);
        final Intent intent = getIntent();

        db = new DBLetterHelper(this);

        img_title = findViewById(R.id.title);
        tv_date = findViewById(R.id.tv_date);
        tv_title = findViewById(R.id.tv_title);
        tv_cont = findViewById(R.id.tv_cont);
        bt_prev = findViewById(R.id.bt_prev);

        /* Get a Letter Item from db */
        int id = intent.getIntExtra("id", 0);
        LetterListItem listItem = db.getLetterListItem(id);

        if( listItem ==null) {
            Toast.makeText(LetterReadActivity.this,"편지를 읽을 수 없네요:(", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(LetterReadActivity.this, LetterListActivity.class);
            startActivity(intent2);
        }

        if( listItem.getSender()==LoginInfo.ina)
            img_title.setImageResource(R.drawable.from_ina);

        /* Show Letter Item */
        String date = String.valueOf(listItem.getDate()%10000);
        tv_date.setText(date);
        tv_title.setText(listItem.getTitle());
        tv_cont.setText(listItem.getCont());

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LetterReadActivity.this, LetterListActivity.class);
                startActivity(intent2);
            }
        });
    }
}

