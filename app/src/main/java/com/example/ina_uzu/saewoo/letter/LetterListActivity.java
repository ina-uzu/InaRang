package com.example.ina_uzu.saewoo.letter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.fab.FabActivity;
import com.example.ina_uzu.saewoo.login.LoginInfo;

import java.util.List;

public class LetterListActivity extends FabActivity {
    DBLetterHelper db;
    ListView listView;
    ListViewAdapter listViewAdapter;
    List <LetterListItem> list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letterread);

        db = new DBLetterHelper(this);
        setFab(this);

        listView = findViewById(R.id.lettetlist);
        setListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(LetterListActivity.this, LetterReadActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    void setListView(){
        if(LoginInfo.getWho()==LoginInfo.ina)
            list = db.getAllLettersToIna();

        else
            list =db.getAllLettersToJaewoo();

        listViewAdapter  = new ListViewAdapter(LetterListActivity.this, list);
        listView.setAdapter(listViewAdapter);
    }

    private class ListViewAdapter extends BaseAdapter {

        private final List<LetterListItem> list;
        private final LayoutInflater inflater;

        private ListViewAdapter(Context context, List<LetterListItem> list){
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
                convertView = inflater.inflate(R.layout.item_letters,parent,false);
                holder = new ViewHolder();
                holder.date = convertView.findViewById(R.id.date);
                holder.title = convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            }
            else {
                holder= (ViewHolder)convertView.getTag();
            }

            int tmp = list.get(position).getDate();
            String date_str = String.valueOf((tmp%10000)/100);
            date_str = date_str + "/" + String.valueOf(tmp%100);

            holder.date.setText(date_str);
            holder.title.setText(list.get(position).getTitle());

            return convertView;
        }
    }

    public class ViewHolder{
        TextView date;
        TextView title;
    }
}
