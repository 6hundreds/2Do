package com.six_hundreds.todo.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.six_hundreds.todo.database.DBHelper;
import com.six_hundreds.todo.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by six_hundreds on 06.01.16.
 */
public class AlarmSetter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DBHelper dbHelper = new DBHelper(context);
        AlarmHelper.getInstance().init(context);
        AlarmHelper alarmHelper = AlarmHelper.getInstance();

        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(dbHelper.query().getTasks(DBHelper.SELECTION_STATUS + " OR " + DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)},
                DBHelper.COLUMN_TASK_DATE));

        for (ModelTask task : tasks) {
            if (task.getDate()!=0) {

            }
        }
    }
}
