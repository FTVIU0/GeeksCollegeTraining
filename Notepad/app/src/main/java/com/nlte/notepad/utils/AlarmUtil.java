package com.nlte.notepad.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.nlte.notepad.BootAndTimingReceiver;

import java.util.Calendar;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/8 0008 14:42
 */
public class AlarmUtil {
    private static final String TIMING_ACTION = "com.nlte.notepad.timing";
    public static final String scheduleContentName = "scheduleContent";
    public static final String ID = "id";

    /**
     * 根据定时的整点数，输出需要闹钟定时的时间
     *
     * @param remindTime 定时的整点数
     * @return
     */
    private static int getTiming(int remindTime) {
        Calendar calendar = Calendar.getInstance();
        if (remindTime < calendar.get(Calendar.HOUR)) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        calendar.set(Calendar.HOUR, remindTime);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int timing = (int) (calendar.getTimeInMillis() - System.currentTimeMillis());

        return timing;
    }

    /**
     * 添加闹钟
     *
     * @param time 定时时间
     */
    public static void addAlarm(Context context, int time, PendingIntent pendingIntent) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.MILLISECOND, getTiming(time));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);
    }

    /**
     * 删除闹钟
     */
    public static void deleteAlarm(Context context, PendingIntent pendingIntent) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    /**
     * 获取不同的PendingIntent
     *
     * @param context         上下文
     * @param scheduleContent intent携带的数据
     * @return
     */
    public static PendingIntent getPendingIntent(Context context, String scheduleContent, int requestCode) {

        Intent intent = new Intent(context, BootAndTimingReceiver.class);
        intent.setAction(TIMING_ACTION);
        intent.putExtra(scheduleContentName, scheduleContent);
        intent.putExtra(ID, requestCode);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return sender;
    }
    public static PendingIntent getPendingIntent(Context context,int requestCode) {

        Intent intent = new Intent(context, BootAndTimingReceiver.class);
        intent.setAction(TIMING_ACTION);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return sender;
    }
}
