package ed.wgu.zamzow.scheduler.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;

    public NotificationReceiver() {
        System.out.println("Notification Receiver Created");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Created");
        Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;

        CharSequence name = "Scheduler";
        String description = "Due Date";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("47", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
        notificationManager.notify(id , notification);
    }
}