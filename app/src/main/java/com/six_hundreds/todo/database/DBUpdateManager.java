package com.six_hundreds.todo.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.six_hundreds.todo.model.ModelTask;

/**
 * Created by six_hundreds on 03.01.16.
 */
public class DBUpdateManager {

    private SQLiteDatabase database;

    public DBUpdateManager(SQLiteDatabase database) {
        this.database = database;
    }

    public void title(long timeStamp, String title) {
        update(DBHelper.COLUMN_TASK_TITLE, timeStamp, title);
    }

    public void date(long timeStamp, long date) {

        update(DBHelper.COLUMN_TASK_DATE, timeStamp, date);
    }

    public void priority(long timeStamp, int priority) {
        update(DBHelper.COLUMN_TASK_PRIORITY, timeStamp, priority);
    }

    public void status(long timeStamp, int status) {
        update(DBHelper.COLUMN_TASK_STATUS, timeStamp, status);
    }

    public void task(ModelTask task) {
        title(task.getTimeStamp(), task.getTitle());
        date(task.getTimeStamp(), task.getDate());
        priority(task.getTimeStamp(), task.getPriority());
        status(task.getTimeStamp(), task.getStatus());
    }


    private void update(String column, long key, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        database.update(DBHelper.TABLE_NAME, contentValues, DBHelper.COLUMN_TASK_TIME_STAMP + " = " + key, null);
    }

    private void update(String column, long key, long value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        database.update(DBHelper.TABLE_NAME, contentValues, DBHelper.COLUMN_TASK_TIME_STAMP + " = " + key, null);
    }
}
