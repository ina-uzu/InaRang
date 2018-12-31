package com.example.ina_uzu.saewoo.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.fab.FabActivity;
import com.example.ina_uzu.saewoo.login.LoginInfo;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class CalendarActivity extends FabActivity {
    DBCalendarHelper db;
    TextView tv_month;
    GridView gridView;

    ListView listView;
    ListViewAdapter listViewAdapter;

    GridViewAdapter gridViewAdapter;
    List<CalendarItem> list;
    Calendar cal;
    int selectedItem, prevSelectedItem ;
    int dateCnt=0,y, m, d;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        db = new DBCalendarHelper(this);
        setFab(this);

        tv_month = findViewById(R.id.tv_month);
        gridView = findViewById(R.id.calendar_view);
        listView = findViewById(R.id.schedule_view);

        /* get Date */
        long now = System.currentTimeMillis();
        final Date date = new Date(now);

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        y = Integer.parseInt(curYearFormat.format(date));
        m = Integer.parseInt(curMonthFormat.format(date));
        d = Integer.parseInt(curDayFormat.format(date));

        String sMonth = curMonthFormat.format(date) + "월";
        tv_month.setText(sMonth);

        /* Grid View Setting */
        list = new ArrayList<CalendarItem>();
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        selectedItem=d-1;
        if(dayNum<7) {
            for (int i = 0; i < dayNum; i++) {
                list.add(new CalendarItem());
                selectedItem++;
            }
        }

        list = db.contstructCalendarList(y,m,cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        gridViewAdapter = new GridViewAdapter(getApplicationContext(), list);
        gridView.setAdapter(gridViewAdapter);

        /* Show SchedList */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* Schedule View Setting */
                prevSelectedItem=selectedItem;
                selectedItem=position;

                if( selectedItem==prevSelectedItem)
                    prevSelectedItem=-1;

                gridViewAdapter.notifyDataSetChanged();

                if(list.get(position).schedList!=null && list.get(position).schedList.size()!=0)
                    listViewAdapter = new ListViewAdapter(getApplicationContext(), list.get(position).schedList);

                else {
                    List<ScheduleItem> tmpList = new ArrayList<ScheduleItem>();
                    listViewAdapter = new ListViewAdapter(getApplicationContext(), tmpList);
                }
                listView.setAdapter(listViewAdapter);

            }
        });


        /* Add a schedule */
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final EditText et_cont = new EditText(CalendarActivity.this);
                et_cont.setHint("무엇을 하나요?");
                et_cont.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


                AlertDialog.Builder dialog = new AlertDialog.Builder(CalendarActivity.this);
                dialog.setTitle("일정을 추가해요!").setView(et_cont).setPositiveButton("추가쟝구", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cont = et_cont.getText().toString();
                        int y = list.get(position).y;
                        int m = list.get(position).m;
                        int d = list.get(position).d;

                        if( cont.length()>0){
                            db.addCalendarItem(new CalendarInfo(LoginInfo.getWho(),y,m,d,cont));
                            Log.d("ADDDDDDD", String.valueOf(d) + " " +cont);

                            /* New List */
                            setViews(position);
                        }

                        else{
                            Toast.makeText(CalendarActivity.this, "아무것도 안 적으면 안되요 :(", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("취소쟝구", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

                return false;
            }
        });
    }

    void setViews(int position){
        list = db.contstructCalendarList(y,m,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        gridViewAdapter = new GridViewAdapter(getApplicationContext(), list);
        gridView.setAdapter(gridViewAdapter);

        listViewAdapter = new ListViewAdapter(getApplicationContext(), list.get(position).schedList);
        listView.setAdapter(listViewAdapter);
    }

    public class GridViewAdapter extends BaseAdapter {
        private final List<CalendarItem> list;
        private final LayoutInflater inflater;

        public GridViewAdapter(Context context, List<CalendarItem> list) {
            this.list = list;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

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


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar, parent, false);
                holder = new ViewHolder();
                holder.tv_date = convertView.findViewById(R.id.date);
                holder.tv_ina = convertView.findViewById(R.id.ina);
                holder.tv_jaewoo = convertView.findViewById(R.id.jaewoo);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_date.setText(list.get(position).date);

            cal = Calendar.getInstance();
            Integer today = cal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);

            //오늘 day 텍스트 컬러 변경
            if (sToday.equals(list.get(position).date)){
                holder.tv_date.setTextColor(getResources().getColor(R.color.main));
            }

            if( position == selectedItem){
                convertView.setBackgroundColor(getResources().getColor(R.color.main3));
            }

            if( prevSelectedItem>=0 && position == prevSelectedItem){
                convertView.setBackgroundColor(getResources().getColor(R.color.white));
            }

            if( list.get(position).jaewoo)
                holder.tv_jaewoo.setBackgroundColor(getResources().getColor(R.color.jaewoo));

            if(list.get(position).ina)
                holder.tv_ina.setBackgroundColor(getResources().getColor(R.color.ina));

            return convertView;
        }
    }

    private class ViewHolder {
        TextView tv_date;
        TextView tv_ina;
        TextView tv_jaewoo;
    }

    private class ListViewAdapter extends BaseAdapter {

        private final List<ScheduleItem> list;
        private final LayoutInflater inflater;

        private ListViewAdapter(Context context, List<ScheduleItem> list){
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
            SchedViewHolder holder = null;
            if( convertView==null){
                convertView = inflater.inflate(R.layout.item_schedules,parent,false);
                holder = new SchedViewHolder();
                holder.cont= convertView.findViewById(R.id.cont);
                holder.who=convertView.findViewById(R.id.who);
                convertView.setTag(holder);
            }
            else {
                holder= (SchedViewHolder)convertView.getTag();
            }

            holder.cont.setText(list.get(position).cont);

            if(list.get(position).who==LoginInfo.ina){
                holder.who.setBackgroundColor(getResources().getColor(R.color.ina));
            }

            else{
                holder.who.setBackgroundColor(getResources().getColor(R.color.jaewoo));
            }

            holder.cont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CalendarActivity.this);

                    String title = "삭제하시겠습니까?";
                    dialog.setTitle(title).setPositiveButton("네네쟝구", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteCalendarItemById(list.get(position).getId());
                            setViews(position);

                        }
                    }).setNeutralButton("취소쟝구", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
            });

            return convertView;
        }
    }

    public class SchedViewHolder{
        TextView cont;
        TextView who;
    }

}
