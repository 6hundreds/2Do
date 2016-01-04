package com.six_hundreds.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.six_hundreds.todo.model.ModelTask;

/**
 * Created by six_hundreds on 03.01.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "app_database";
    public static final String TABLE_NAME = "tasks";

    public static final String COLUMN_TASK_TITLE = "title";
    public static final String COLUMN_TASK_DATE = "date";
    public static final String COLUMN_TASK_PRIORITY = "priority";
    public static final String COLUMN_TASK_STATUS = "status";
    public static final String COLUMN_TASK_TIME_STAMP = "time_stamp";

    private DBQueryManager dbQueryManager;
    private DBUpdateManager dbUpdateManager;

    public static final String SELECTION_STATUS = DBHelper.COLUMN_TASK_STATUS + " = ?";

    private static final String CREATE_TABLE_SCRIPT = "CREATE TABLE " +
            TABLE_NAME + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TASK_TITLE + " TEXT NOT NULL, " +
            COLUMN_TASK_DATE + " LONG, " +
            COLUMN_TASK_PRIORITY + " INTEGER, " +
            COLUMN_TASK_STATUS + " INTEGER, " +
            COLUMN_TASK_TIME_STAMP + " LONG);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbQueryManager = new DBQueryManager(getReadableDatabase());
        dbUpdateManager = new DBUpdateManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public void saveTask (ModelTask task) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TASK_TITLE, task.getTitle());
        contentValues.put(COLUMN_TASK_DATE, task.getDate());
        contentValues.put(COLUMN_TASK_PRIORITY, task.getPriority());
        contentValues.put(COLUMN_TASK_STATUS, task.getStatus());
        contentValues.put(COLUMN_TASK_TIME_STAMP, task.getTimeStamp());

        getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public DBUpdateManager update (){
        return dbUpdateManager;
    }
    public DBQueryManager query () {
        return dbQueryManager;
    }
}
