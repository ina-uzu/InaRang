package com.example.ina_uzu.saewoo.bucketlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.fab.FabActivity;
import com.example.ina_uzu.saewoo.login.LoginInfo;

import java.util.List;

public class BucketListActivity extends FabActivity {
    DBBucketlistHelper db;
    ListView listView;
    ListViewAdapter listViewAdapter;
    List <BucketListItem> list;
    Button bt_add;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucketlist);

        /* DB */
        db = new DBBucketlistHelper(this);
        setFab(this);

        /* LIST VIEW */
        listView = findViewById(R.id.bucketlist);
        setListView();

        /* Add Button */
        bt_add = findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText et_cont = new EditText(BucketListActivity.this);
                et_cont.setHint("내용을 적어주세요");
                et_cont.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                AlertDialog.Builder dialog = new AlertDialog.Builder(BucketListActivity.this);
                String title = "가 우주 하고 싶은 것";
                if(LoginInfo.getWho()==LoginInfo.ina)
                    title = "이나"+title;
                else
                    title = "새우"+title;

                dialog.setTitle(title).setView(et_cont).setPositiveButton("추가쟝구", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cont = et_cont.getText().toString();

                        if( cont.length()>0){
                            db.addBucketListItem(new BucketListItem(cont));
                            setListView();  // 새로 업데이트 된 리스트를 사용해서 리스트뷰 다시 설정해줘야 함
                        }

                        else{
                            Toast.makeText(BucketListActivity.this, "아무것도 안 적으면 안되요 :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("취소쟝구", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

            }
        });

    }

    void setListView(){
        list = db.getAllBucketListItems();
        listViewAdapter  = new ListViewAdapter(getApplicationContext(), list);
        listView.setAdapter(listViewAdapter);
    }

    private class ListViewAdapter extends BaseAdapter {
        private final List<BucketListItem> list;
        private final LayoutInflater inflater;

        private ListViewAdapter(Context context, List<BucketListItem> list){
            this.list= list;
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if( convertView==null){
                convertView = inflater.inflate(R.layout.item_bucketlist,parent,false);
                holder = new ViewHolder();
                holder.cont = convertView.findViewById(R.id.cont);
                holder.checkbox = convertView.findViewById(R.id.checkbox);
                convertView.setTag(holder);
            }
            else {
                holder= (ViewHolder)convertView.getTag();
            }

            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean cheked = list.get(position).getIsChecked();
                    list.get(position).setChecked(!cheked);

                    //db table 수정 및 새로운 리스트로 출력 내용 동기화
                    db.updateBucketListItem(list.get(position));
                    setListView();
                }
            });

            holder.cont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(BucketListActivity.this);

                    String title = "삭제하시겠습니까?";
                    dialog.setTitle(title).setPositiveButton("네네쟝구", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteBucketListItem(list.get(position));
                            setListView();

                        }
                    }).setNeutralButton("취소쟝구", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
            });

            if( list.get(position).getIsChecked()) {
                holder.checkbox.setBackground(getResources().getDrawable(R.drawable.check_box_checked));
            }

            holder.cont.setText(list.get(position).getCont());

            return convertView;
        }
    }

    private class ViewHolder{
        TextView cont;
        Button checkbox;
    }

}
