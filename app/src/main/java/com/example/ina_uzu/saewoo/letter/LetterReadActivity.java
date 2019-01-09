package com.example.ina_uzu.saewoo.letter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.fab.FabActivity;
import com.example.ina_uzu.saewoo.login.LoginInfo;

public class LetterReadActivity extends FabActivity {
    //DBLetterHelper db;
    ImageView img_title;
    TextView tv_date, tv_title, tv_cont;
    Button bt_prev,bt_del;
    int cur_id;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letterread_item);
        final Intent intent = getIntent();

        //db = new DBLetterHelper(this);
        setFab(this);

        img_title = findViewById(R.id.title);
        tv_date = findViewById(R.id.tv_date);
        tv_title = findViewById(R.id.tv_title);
        tv_cont = findViewById(R.id.tv_cont);
        bt_prev = findViewById(R.id.bt_prev);
        bt_del = findViewById(R.id.bt_del);

        final Intent intent2 = new Intent(LetterReadActivity.this, LetterListActivity.class);

        /* Get a Letter Item from db */
        final int id = intent.getIntExtra("id", 0);
        //final LetterListItem listItem = db.getLetterListItem(id);

        cur_id=-1;
        for(int i=0; i<LetterInfo.letterList.size(); i++){
            if( LetterInfo.letterList.get(i).getId()==id){
                cur_id=i;
                break;
            }
        }

        if( cur_id==-1) {
            Toast.makeText(LetterReadActivity.this,"편지를 읽을 수 없네요:(", Toast.LENGTH_SHORT).show();
            startActivity(intent2);
        }

        if( cur_id>0 && LetterInfo.letterList.get(cur_id).getSender()==LoginInfo.ina)
            img_title.setImageResource(R.drawable.from_ina);

        /* Show Letter Item */
        int m = LetterInfo.letterList.get(cur_id).getDate()%10000;
        String date = String.valueOf(m/100) + "/" + String.valueOf(m%100);

        tv_date.setText(date);
        tv_title.setText(LetterInfo.letterList.get(cur_id).getTitle());
        tv_cont.setText(LetterInfo.letterList.get(cur_id).getCont());

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LetterReadActivity.this);

                String title = "삭제하시겠습니까?";
                dialog.setTitle(title).setPositiveButton("네네쟝구", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //db.deleteLetterListItem(listItem);

                        //LetterDeleteRequest letterDeleteRequest = new LetterDeleteRequest(getApplicationContext(),id);
                        LetterRequest.DeleteRequest(getApplicationContext(),id);
                        LetterInfo.letterList.remove(cur_id);

                        Toast.makeText(LetterReadActivity.this, "이 편지는 영영 안녕이에요!", Toast.LENGTH_SHORT);
                        startActivity(intent2);

                    }
                }).setNeutralButton("취소쟝구", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
            }
        });
    }
}

