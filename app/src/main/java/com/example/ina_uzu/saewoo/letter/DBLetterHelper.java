package com.example.ina_uzu.saewoo.letter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.ina_uzu.saewoo.login.LoginInfo;

import java.util.ArrayList;
import java.util.List;

public class DBLetterHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String NAME = "LetterManager";
    private static final String TABLE_NAME = "letter";
    private static final int VERSION = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_SENDER = "sender";
    private static final String KEY_DATE = "date";  // YYYYMMDD
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";


    public DBLetterHelper(Context context){
        super(context,NAME,null,VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_SENDER + " INTEGER,"
                + KEY_DATE + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context,"NEW VERSION",Toast.LENGTH_LONG).show();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addLetterListItem(LetterListItem listItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SENDER, listItem.getSender());
        values.put(KEY_DATE, listItem.getDate());
        values.put(KEY_TITLE,listItem.getTitle());
        values.put(KEY_CONTENT, listItem.getCont());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public LetterListItem getLetterListItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,KEY_SENDER,KEY_DATE,KEY_TITLE,KEY_CONTENT}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor==null) {
            return null;
        }

        cursor.moveToFirst();
        int sender = Integer.parseInt(cursor.getString(1));
        int date = Integer.parseInt(cursor.getString(2));

        LetterListItem listItem = new LetterListItem(id,sender,date,cursor.getString(3),cursor.getString(4));
        return listItem;
    }

    public List<LetterListItem> getAllLetterListItems(){

        List<LetterListItem> list = new ArrayList<LetterListItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                LetterListItem listItem = new LetterListItem();
                listItem.setId(Integer.parseInt(cursor.getString(0)));
                listItem.setSender(Integer.parseInt(cursor.getString(1)));
                listItem.setDate(Integer.parseInt(cursor.getString(2)));
                listItem.setTitleCont(cursor.getString(3),cursor.getString(4));
                list.add(listItem);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<LetterListItem> getAllLettersToJaewoo(){
        List<LetterListItem> list = new ArrayList<LetterListItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int sender = Integer.parseInt(cursor.getString(1));
                if( sender!=LoginInfo.jaewoo)
                    continue;;
                LetterListItem listItem = new LetterListItem();
                listItem.setId(Integer.parseInt(cursor.getString(0)));
                listItem.setSender(sender);
                listItem.setDate(Integer.parseInt(cursor.getString(2)));
                listItem.setTitleCont(cursor.getString(3),cursor.getString(4));
                list.add(listItem);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<LetterListItem> getAllLettersToIna(){
        List<LetterListItem> list = new ArrayList<LetterListItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int sender = Integer.parseInt(cursor.getString(1));
                if( sender!=LoginInfo.ina)
                    continue;;
                LetterListItem listItem = new LetterListItem();
                listItem.setId(Integer.parseInt(cursor.getString(0)));
                listItem.setSender(sender);
                listItem.setDate(Integer.parseInt(cursor.getString(2)));
                listItem.setTitleCont(cursor.getString(3),cursor.getString(4));
                list.add(listItem);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int updateLetterListItem(LetterListItem listItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SENDER,listItem.getSender());
        values.put(KEY_DATE,listItem.getDate());
        values.put(KEY_TITLE,listItem.getTitle());
        values.put(KEY_CONTENT, listItem.getCont());

        return db.update(TABLE_NAME,values, KEY_ID+" =?", new String[]{ String.valueOf(listItem.getId())});
    }

    public void deleteLetterListItem(LetterListItem listItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(listItem.getId()) });
        db.close();
    }

    public int getLetterListCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}


