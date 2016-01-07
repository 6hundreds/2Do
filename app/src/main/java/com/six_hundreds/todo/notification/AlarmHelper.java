package com.six_hundreds.todo.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.six_hundreds.todo.model.ModelTask;

/**
 * Created by six_hundreds on 06.01.16.
 */
public class AlarmHelper {

    private static AlarmHelper instance;
    private Context context;
    private AlarmManager alarmManager;

    public static AlarmHelper getInstance() {
        if (instance == null) {
            instance = new AlarmHelper();

        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);


    }
     public void setAlarm (ModelTask task) {
         Intent intent = new Intent(context, AlarmReceiver.class);
         intent.putExtra("title", task.getTitle());
         intent.putExtra("timeStamp", task.getTimeStamp());
         intent.putExtra("color", task.getPriorityColor());

         PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                 (int) task.getTimeStamp(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
         alarmManager.set(AlarmManager.RTC_WAKEUP, task.getDate(),pendingIntent);
     }

    public void removeAlarm (long taskTimeStamp){
        Intent intent = new Intent(context,AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) taskTimeStamp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);

    }
}
