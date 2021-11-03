package com.smhrd.seniorproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(TAG, "From" + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {                      //포어그라운드
            Log.v(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        } else if (remoteMessage.getData().size() > 0) {                           //백그라운드
            Log.v(TAG, "메세지가~" + remoteMessage.getData());
            if (true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
            } else {
                handleNow();
            }
        }
    }

    //기기에서 받을
    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "Channel ID";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Channel Name";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0505, notificationBuilder.build());
    }


    private void handleNow() {
        Log.v(TAG, "이게모야모야 짧은 삶을 살게된 토큰");
    }

    //새로운 토큰은 여기로~!~!~!~!
    public void onNewToken(String token) {
        Log.v(TAG, "Refreshed token: " + token); // 이 부분은 뭔데 왜 로그에 안 찍혀?
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

    }
}
