package com.example.ina_uzu.saewoo.bucketlist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.R;
import java.util.List;

public class BucketListActivity extends Activity {
    ListView listView;
    ListViewAdapter listViewAdapter;
    List <BucketListItem> list;
    Button bt_add;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucketlist);

        /* DB */
        DBBucketlistHelper db = new DBBucketlistHelper(this);
        db.addBucketListItem(new BucketListItem("안녕하새우 다만들기"));
        db.addBucketListItem(new BucketListItem("오늘까찌"));
        db.addBucketListItem(new BucketListItem("트리플렛 과제하기"));

        /* LIST VIEW */
        listView = findViewById(R.id.bucketlist);
        list = db.getAllBucketListItems();
        listViewAdapter  = new ListViewAdapter(getApplicationContext(), list);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BucketListActivity.this, "Push "+ String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });

        /* Add Button */
        bt_add = findViewById(R.id.bt_add);

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
        public View getView(int position, View convertView, ViewGroup parent) {
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

            if( list.get(position).getIsChecked())
                holder.checkbox.setBackground(getResources().getDrawable(R.drawable.check_box_checked));
            holder.cont.setText(list.get(position).getCont());

            return convertView;
        }
    }

    private class ViewHolder{
        TextView cont;
        Button checkbox;
    }

}
