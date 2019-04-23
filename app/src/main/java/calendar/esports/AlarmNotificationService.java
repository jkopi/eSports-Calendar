
package calendar.esports;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

public class AlarmNotificationService extends IntentService {
    private final String CHANNEL_ID = "estream_notifications";
    public static int MY_NOTIFICATION_ID;
    //    public static int MY_NOTIFICATION_ID;// = NotificationID.getID();
    private NotificationManager notificationManager;

    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String gameIcon           = intent.getStringExtra("gameIcon");
        Serializable serializable = intent.getSerializableExtra("match");
        if(serializable instanceof Match) {
            Match match           = (Match) serializable;
            MY_NOTIFICATION_ID    = match.getId();

            Log.d("matchName", "onReceive Match: " + match.getName());
            Log.d("gameIcon", "onReceive: " + gameIcon);
            sendNotification("Match is Starting", gameIcon, match);
            createNotificationChannel();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "EStream Notifications";
            String description = "All EStream notifications";
            int importance = notificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }


    private void sendNotification(String msg, String gameIcon, Match match) {
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        String timeOfEvent = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault()).format(match.getBegin_at());
        String defLogo = "game_logo1" ;



        switch (gameIcon) {
            case "csgo":
                defLogo = defLogo.replace("1", "2");
                break;            case "lol":
                defLogo = defLogo.replace("1", "5");
                break;
            case "ow":
                defLogo = defLogo.replace("1", "4");
                break;
            case "dota2":
                defLogo = defLogo.replace("1", "3");
                break;
        }

        int gameIdentifier = this.getResources().getIdentifier(defLogo, "drawable",
                this.getPackageName());

        String message = ("You've set a notification for " + match.getName() + "\n" + "Match starts at: "
                + timeOfEvent);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification myNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(match.getLeague().getName().toString())
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                //.setDefaults(Notification.DEFAULT_SOUND)
                //.setAutoCancel(true)
                .setSmallIcon(gameIdentifier)
                .build();

        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    }
}
