package com.example.alarmemanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmeReceiver extends BroadcastReceiver {

    private static final String TAG = "MyBroadCastReceiver";
    //
// Constants for the notification actions buttons.
    private static final String ACTION_UPDATE_NOTIFICATION = "com.android.example.notifyme.ACTION_UPDATE_NOTIFICATION";
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;
    //

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private NotificationCompat.Builder getNotificationBuilder(Context context) {

        // Set up the pending intent that is delivered when the notification is clicked.
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
//      String txtNotificacao = "Teste BroadCastReceiver " + Calendar.getInstance().get(Calendar.HOUR)+":"+Calendar.getInstance().get(Calendar.MINUTE) +":"+ Calendar.getInstance().get(Calendar.SECOND);
        String txtNotificacao = "Teste BroadCastReceiver "
                + Calendar.getInstance().get(Calendar.HOUR) + ":"
                + Calendar.getInstance().get(Calendar.MINUTE) + ":"
                + Calendar.getInstance().get(Calendar.SECOND);
        Log.d(TAG, txtNotificacao);


        // Build the notification with all of the parameters.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle("Chegou a hora de ir para casa!")
                .setContentText(txtNotificacao)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }
    public void sendNotification(Context context) {
        Log.d(TAG, "sendNotification");
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        // Build the notification with all of the parameters using helper method
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(context);
        // Add the action button using the pending intent.
        //definir a imagem da notificacao
        notifyBuilder.addAction(R.drawable.ic_launcher_background, "Teste de Broadcast", updatePendingIntent);
        // Deliver the notification.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }
    public void createNotificationChannel(Context context) {
        Log.d(TAG, "createNotificationChannel");
        // Create a notification manager object.
        mNotifyManager =(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Teste de Broadcast",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Teste de Broadcast");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
