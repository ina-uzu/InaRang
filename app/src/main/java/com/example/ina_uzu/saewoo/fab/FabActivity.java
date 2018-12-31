package com.example.ina_uzu.saewoo.fab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.bucketlist.BucketListActivity;
import com.example.ina_uzu.saewoo.calendar.CalendarActivity;
import com.example.ina_uzu.saewoo.letter.LetterMenuActivity;

public class FabActivity extends Activity  implements View.OnClickListener{
    Boolean openFlag = false;
    Animation fab_open, fab_close;
    FloatingActionButton fab, fab2, fab3,fab4;
    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setFab(Context context){

        fab_open = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(context,R.anim.fab_close);

        this.context=context;
        fab = findViewById(R.id.fab);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab4 = findViewById(R.id.fab4);

        //버튼 상태 초기화(닫혀있어라!)
        fab2.startAnimation(fab_close);
        fab3.startAnimation(fab_close);
        fab4.startAnimation(fab_close);

        fab2.setClickable(false);
        fab3.setClickable(false);
        fab4.setClickable(false);

        fab.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
    }

    public void anim() {

        if (openFlag) {
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);

            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);

            openFlag = false;
        } else {
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);

            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            openFlag = true;
        }
    }

    public void onClick(View v){
        int id = v.getId();
        Intent intent;

        switch (id) {
            case R.id.fab:
                anim();
                break;

            /* Calendar */
            case R.id.fab2:
                intent = new Intent(context, CalendarActivity.class);
                startActivity(intent);
                break;

            /* Letter */
            case R.id.fab3:
                intent = new Intent(context, LetterMenuActivity.class);
                startActivity(intent);
                break;

            /* Bucket List */
            case R.id.fab4:
                intent = new Intent(context, BucketListActivity.class);
                startActivity(intent);
                break;

        }
    }

}
