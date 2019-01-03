package com.example.ina_uzu.saewoo.worktable;

import android.os.Bundle;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.fab.FabActivity;

public class WorktableActivity extends FabActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worktable);

        setFab(this);


    }
}
