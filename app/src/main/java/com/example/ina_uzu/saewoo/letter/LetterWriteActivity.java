package com.example.ina_uzu.saewoo.letter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.fab.FabActivity;
import com.example.ina_uzu.saewoo.login.LoginInfo;
import com.example.ina_uzu.saewoo.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LetterWriteActivity extends FabActivity {
    EditText et_title, et_cont;
    Button bt_send;
    //DBLetterHelper db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letterwrite);

        //db = new DBLetterHelper(this);
        setFab(this);

        et_title = findViewById(R.id.et_title);
        et_cont = findViewById(R.id.et_cont);
        bt_send = findViewById(R.id.bt_send);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = et_title.getText().toString();
                String cont = et_cont.getText().toString();

                if( title.length()==0 || cont.length()==0){
                    Toast.makeText(LetterWriteActivity.this, "아무 것도 안 적으면 안되요:(",Toast.LENGTH_SHORT).show();
                }

                else{
                    String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
                    LetterListItem listItem = new LetterListItem(LoginInfo.getWho(),Integer.parseInt(date), title, cont );
                    //db.addLetterListItem(listItem);
                    //LetterPostRequest postRequest = new LetterPostRequest(getApplicationContext(), LoginInfo.getWho(), date, title,cont);

                    LetterRequest.PostRequest(getApplicationContext(), LoginInfo.getWho(), date, title,cont);
                    Toast.makeText(LetterWriteActivity.this, date + " 우주의 편지가 보내졌어요:)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LetterWriteActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}
