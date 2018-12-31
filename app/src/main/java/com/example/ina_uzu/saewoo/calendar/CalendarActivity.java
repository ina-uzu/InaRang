package com.example.ina_uzu.saewoo.calendar;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.ina_uzu.saewoo.R;
import com.example.ina_uzu.saewoo.fab.FabActivity;
import com.example.ina_uzu.saewoo.login.LoginInfo;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends FabActivity {
    TextView tv_month;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    List<CalendarItem> list;
    Calendar cal;
    int dateCnt=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setFab(this);

        tv_month = findViewById(R.id.tv_month);
        gridView = findViewById(R.id.calendar_view);

        /* get Date */
        long now = System.currentTimeMillis();
        final Date date = new Date(now);

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        String sMonth = curMonthFormat.format(date) + "월";
        tv_month.setText(sMonth);

        /* Grid View Setting */
        list = new ArrayList<CalendarItem>();

        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        if(dayNum<7) {
            for (int i = 0; i < dayNum; i++) {
                list.add(new CalendarItem());
                dateCnt++;
            }
        }

        setCalendarDate(cal.get(Calendar.MONTH) + 1);
        gridViewAdapter = new GridViewAdapter(getApplicationContext(), list);
        gridView.setAdapter(gridViewAdapter);
    }

    private void setCalendarDate(int month) {
        cal.set(Calendar.MONTH, month - 1);
        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            list.add(new CalendarItem(String.valueOf(i + 1)));
            dateCnt++;
        }

        for(int i=0; i<=dateCnt%7; i++)
            list.add(new CalendarItem());
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
            if (sToday.equals(getItem(position))) {
                holder.tv_date.setTextColor(getResources().getColor(R.color.main));
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

}
