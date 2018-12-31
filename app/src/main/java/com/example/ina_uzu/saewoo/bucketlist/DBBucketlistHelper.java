package com.example.ina_uzu.saewoo.bucketlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBBucketlistHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String NAME = "BucketListManager";
    private static final String TABLE_NAME = "bucketlist";
    private static final int VERSION = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DONE = "done";


    public DBBucketlistHelper(Context context){
        super(context,NAME,null,VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CONTENT + " TEXT,"
                + KEY_DONE + " INTEGER" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context,"NEW VERSION",Toast.LENGTH_LONG).show();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBucketListItem(BucketListItem listItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CONTENT, listItem.getCont());

        if(listItem.getIsChecked())
            values.put(KEY_DONE, 1);

        else
            values.put(KEY_DONE, 0);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public BucketListItem getBucketListItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,KEY_CONTENT,KEY_DONE}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if( cursor!=null)
            cursor.moveToFirst();

        boolean isChecked = false;
        if( Integer.parseInt(cursor.getString(2))==1){
            isChecked=true;
        }
        BucketListItem bucketListItem = new BucketListItem(cursor.getString(1), isChecked);
        return bucketListItem;
    }

    public List<BucketListItem> getAllBucketListItems(){

        List<BucketListItem> list = new ArrayList<BucketListItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BucketListItem listItem = new BucketListItem();
                listItem.setId(Integer.parseInt(cursor.getString(0)));
                listItem.setCont(cursor.getString(1));
                boolean checked= false;
                if(Integer.parseInt(cursor.getString(2))==1)
                    checked=true;
                listItem.setChecked(checked);

                // Adding contact to list
                list.add(listItem);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public int updateBucketListItem(BucketListItem listItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, listItem.getCont());

        if(listItem.getIsChecked())
            values.put(KEY_DONE, 1);

        else
            values.put(KEY_DONE, 0);

        return db.update(TABLE_NAME,values, KEY_ID+" =?", new String[]{ String.valueOf(listItem.getId())});
    }

    public void deleteBucketListItem(BucketListItem listItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(listItem.getId()) });
        db.close();
    }

    public int getBucketListCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}

