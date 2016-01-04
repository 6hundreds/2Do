package com.six_hundreds.todo.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.six_hundreds.todo.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by six_hundreds on 03.01.16.
 */
public class DBQueryManager  {

    private SQLiteDatabase database;

    DBQueryManager(SQLiteDatabase database) {
        this.database = database;
    }

    public List<ModelTask> getTasks (String selection, String[] selectionArgs, String orderBy){
        List<ModelTask> tasks = new ArrayList<>();

        Cursor c = database.query(DBHelper.TABLE_NAME, null, selection, selectionArgs, null, null, orderBy);

        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE));
                long date = c.getLong(c.getColumnIndex(DBHelper.COLUMN_TASK_DATE));
                int priority = c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_PRIORITY));
                int status = c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_STATUS));
                long timeStamp = c.getLong(c.getColumnIndex(DBHelper.COLUMN_TASK_TIME_STAMP));

                ModelTask task = new ModelTask(title, date, priority, status, timeStamp);
                tasks.add(task);

            } while (c.moveToNext());
        }
            c.close();

            return tasks;


    }
}
