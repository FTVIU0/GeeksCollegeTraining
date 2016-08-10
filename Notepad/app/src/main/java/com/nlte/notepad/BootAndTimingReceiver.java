package com.nlte.notepad;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.nlte.notepad.utils.AlarmUtil;
import com.nlte.notepad.utils.ToastUtil;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/8 0008 15:14
 */
public class BootAndTimingReceiver extends BroadcastReceiver {
    private static final String TIMING_ACTION = "com.nlte.notepad.timing";//定时action

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {

            case Intent.ACTION_BOOT_COMPLETED:
                Intent serviceIntent = new Intent(context, RemindService.class);
                context.startService(serviceIntent);
                ToastUtil.showMsg(context, context.getString(R.string.show));
                break;
            case Intent.ACTION_TIME_CHANGED:
                for (int i = 0; i < 1; i++) {

                    Intent service2Intent = new Intent(context, RemindService.class);
                    context.startService(service2Intent);
                }
                ToastUtil.showMsg(context, context.getString(R.string.show));
                break;

            case TIMING_ACTION:
                showNotification(context, intent.getStringExtra(AlarmUtil.scheduleContentName),
                        intent.getIntExtra(AlarmUtil.ID, 0));
                break;
        }
    }

    /**
     * 显示通知
     *
     * @param context     上下文
     * @param contentText 通知的内容
     */
    private void showNotification(Context context, String contentText, int notifyId) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.content_title))
                .setContentText(contentText)
                .setContentIntent(pendingIntent);//点击notification打开activity
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(notifyId, notification);
    }
}


