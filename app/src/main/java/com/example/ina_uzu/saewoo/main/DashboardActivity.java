package com.example.ina_uzu.saewoo.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.fab.FabActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends FabActivity {
    TextView cal, table, letter, bucket;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setFab(this);

        cal = findViewById(R.id.main_cal);
        table = findViewById(R.id.main_table);
        letter = findViewById(R.id.main_letter);
        bucket = findViewById(R.id.main_bucket);

    }



}
