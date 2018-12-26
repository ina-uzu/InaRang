package com.example.ina_uzu.saewoo.letter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.R;

import java.util.ArrayList;
import java.util.List;

public class LetterReadActivity extends Activity {
    ListView listView;
    ListViewAdapter listViewAdapter;
    ArrayList <LetterListItem> list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letterread);

        listView = findViewById(R.id.lettetlist);
        list = new ArrayList<>();
        list.add(new LetterListItem(12,26,"안녕하새우 다 만들기"));
        list.add(new LetterListItem(12,27,"내일 일찍 일어나기"));

        listViewAdapter  = new ListViewAdapter(LetterReadActivity.this, list);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LetterReadActivity.this, "Push "+ String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    private class ListViewAdapter extends BaseAdapter {

        private final List<LetterListItem> list;
        private final LayoutInflater inflater;

        private ListViewAdapter(Context context, ArrayList<LetterListItem> list){
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

            String date_str = String.valueOf(list.get(position).month ) + " / " +String.valueOf(list.get(position).day);
            holder.date.setText(date_str);
            holder.title.setText(list.get(position).title);

            return convertView;
        }
    }

    public class ViewHolder{
        TextView date;
        TextView title;
    }
}
