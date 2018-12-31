package com.example.ina_uzu.saewoo.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.login.LoginInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBCalendarHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String NAME = "CalendarManager";
    private static final String TABLE_NAME = "calendar";
    private static final int VERSION = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_WHO = "who";
    private static final String KEY_YEAR = "year";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DATE = "date";
    private static final String KEY_CONTENT = "content";


    public DBCalendarHelper(Context context){
        super(context,NAME,null,VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_WHO + " INTEGER,"
                + KEY_YEAR + " INTEGER,"
                + KEY_MONTH + " INTEGER,"
                + KEY_DATE + " INTEGER,"
                + KEY_CONTENT + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context,"NEW VERSION",Toast.LENGTH_LONG).show();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addCalendarItem(CalendarInfo listItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WHO, listItem.getWho());
        values.put(KEY_YEAR, listItem.getYear());
        values.put(KEY_MONTH, listItem.getMonth());
        values.put(KEY_DATE, listItem.getDate());
        values.put(KEY_CONTENT, listItem.getSched());

        Log.d("ADDDDDDDDDDDDDD_DB", String.valueOf(listItem.getDate()));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public CalendarInfo getCalendarItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,KEY_WHO,KEY_YEAR,KEY_MONTH,KEY_DATE,KEY_CONTENT}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor==null) {
            return null;
        }

        cursor.moveToFirst();
        int who = Integer.parseInt(cursor.getString(1));
        int year = Integer.parseInt(cursor.getString(2));
        int month = Integer.parseInt(cursor.getString(3));
        int date = Integer.parseInt(cursor.getString(4));

        CalendarInfo listItem = new CalendarInfo(who,year,month,date,cursor.getString(5));
        return listItem;
    }

    public List<CalendarInfo> getAllCalendarItems(){

        List<CalendarInfo> list = new ArrayList<CalendarInfo>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CalendarInfo listItem = new CalendarInfo();
                listItem.setId(Integer.parseInt(cursor.getString(0)));
                listItem.setWho(Integer.parseInt(cursor.getString(1)));
                listItem.setYear(Integer.parseInt(cursor.getString(2)));
                listItem.setMonth(Integer.parseInt(cursor.getString(3)));
                listItem.setDate(Integer.parseInt(cursor.getString(4)));
                listItem.setSched(cursor.getString(5));
                list.add(listItem);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int updateCalendarItem(CalendarInfo listItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WHO,listItem.getWho());
        values.put(KEY_YEAR,listItem.getYear());
        values.put(KEY_MONTH,listItem.getMonth());
        values.put(KEY_DATE,listItem.getDate());
        values.put(KEY_CONTENT, listItem.getSched());

        return db.update(TABLE_NAME,values, KEY_ID+" =?", new String[]{ String.valueOf(listItem.getId())});
    }

    public void deleteCalendarItem(CalendarInfo listItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(listItem.getId()) });
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    public int getCalendarCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    /* 날짜 별로 Sched 모아 리스트로 */
    public void addSchedList(int year, int month, int date, CalendarItem calendarItem){
        boolean ina=false, jaewoo=false;
        List<ScheduleItem> list= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = KEY_YEAR+"=? OR " + KEY_MONTH + "=? OR " + KEY_DATE + "=?";

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,KEY_WHO,KEY_YEAR,KEY_MONTH,KEY_DATE,KEY_CONTENT}, whereClause,
                new String[] {String.valueOf(year), String.valueOf(month), String.valueOf(date)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ScheduleItem listItem = new ScheduleItem(Integer.parseInt(cursor.getString(1)), cursor.getString(5));
                list.add(listItem);

                if(cursor.getString(1).equals("0"))
                    ina=true;
                else
                    jaewoo=true;
            } while (cursor.moveToNext());
        }

        calendarItem.schedList = list;
        calendarItem.ina = ina;
        calendarItem.jaewoo=jaewoo;
    }

    public void addSchedList2(int year, int month, int date, CalendarItem calendarItem){
        boolean ina=false, jaewoo=false;
        List<ScheduleItem> list= new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("INA----", String.valueOf(date));

        int y,m,d;
        if (cursor.moveToFirst()) {
            do {
                y = Integer.parseInt(cursor.getString(2));
                m = Integer.parseInt(cursor.getString(3));
                d = Integer.parseInt(cursor.getString(4));
                Log.d("INA----In LOOP", String.valueOf(d) + " and  "  + String.valueOf(date));

                if( y==year && m==month && d==date) {
                    Log.d("ADDDDDDDDD_ACTURALLY", String.valueOf(d) + " and  "  + String.valueOf(date));
                    ScheduleItem listItem = new ScheduleItem(Integer.parseInt(cursor.getString(1)), cursor.getString(5));
                    list.add(listItem);

                    if (cursor.getString(1).equals("0"))
                        ina = true;
                    else
                        jaewoo = true;
                }
            } while (cursor.moveToNext());
        }

        calendarItem.schedList = list;
        calendarItem.ina = ina;
        calendarItem.jaewoo=jaewoo;
    }

    public List<CalendarItem> contstructCalendarList(int year, int month , int last){
        List<CalendarItem> list = new ArrayList<>();

        for(int i=1; i<=last; i++){
            CalendarItem calendarItem= new CalendarItem(year, month, i, String.valueOf(i));
            addSchedList2(year,month,i,calendarItem);
            list.add(calendarItem);
        }

        return list;
    }
}

