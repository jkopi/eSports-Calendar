package calendar.esports;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AlarmNotificationService extends IntentService {

    public static int MY_NOTIFICATION_ID;
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
            sendNotification("Match Start", gameIcon, match);
        }
    }


    private void sendNotification(String msg, String gameIcon, Match match) {
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String timeOfEvent = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.ENGLISH).format(match.getBegin_at());
        String defLogo = "game_logo1" ;

        if (gameIcon.equals("csgo")) {
            defLogo = defLogo.replace("1", "2");
        } else if (gameIcon.equals("lol")) {
            defLogo = defLogo.replace("1", "5");
        } else if (gameIcon.equals("ow")) {
            defLogo = defLogo.replace("1", "4");
        } else if (gameIcon.equals("dota2")) {
            defLogo = defLogo.replace("1", "3");
        }

        int gameIdentifier = this.getResources().getIdentifier(defLogo, "drawable",
                this.getPackageName());

        String message = ("You've set a notification for " + match.getName() + "\n" + "Match starts at: "
                + timeOfEvent);

        Notification myNotification = new NotificationCompat.Builder(this)
                .setContentTitle(match.getLeague().getName().toString())
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                //                            .setDefaults(Notification.DEFAULT_SOUND)
                //                            .setAutoCancel(true)
                .setSmallIcon(gameIdentifier)
                .build();

        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    }
}

//public class AlarmNotificationService extends IntentService {
//private static final String ACTION_START = "ACTION_START";
//    private static final String ACTION_DELETE = "ACTION_DELETE";

//    public static Intent createIntentStartNotification(Context context){
//        Intent intent = new Intent(context, AlarmNotificationService.class);
//        intent.setAction(ACTION_START);
//        return intent;
//    }
//    public static Intent createIntentDeleteNotification(Context context) {
//        Intent intent = new Intent(context, AlarmNotificationService.class);
//        intent.setAction(ACTION_DELETE);
//        return intent;
//    }
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        try {
//            String action = intent.getAction();
//            if (ACTION_START.equals(action)) {
//                processStartNotification(intent);
//            }
//            if (ACTION_DELETE.equals(action)) {
//                processDeleteNotification(intent);
//            }
//        } finally {
//            WakefulBroadcastReceiver.completeWakefulIntent(intent);
//        }
//    }
//    private void processDeleteNotification(Intent intent) { }
//    private void processStartNotification(Intent intent) {
//        String gameIcon           = intent.getStringExtra("gameIcon");
//        Serializable serializable = intent.getSerializableExtra("match");
//        if(serializable instanceof Match) {
//            Match match           = (Match) serializable;
//            MY_NOTIFICATION_ID    = match.getId();
//
//            Log.d("matchName", "onReceive Match: " + match.getName());
//            Log.d("gameIcon", "onReceive: " + gameIcon);
//            sendNotification("Match Start", gameIcon, match);
//        }
//    }

//    private void sendNotification(String msg, String gameIcon, Match match) {
//
//    final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setContentTitle("\uD83D\uDD14 " + match.getLeague().getName().toString())
//                .setContentText(message)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setTicker("Notification!")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(gameIdentifier);
//
//                PendingIntent contentIntent = PendingIntent.getActivity(this, MY_NOTIFICATION_ID,
//                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//
//        builder.setContentIntent(contentIntent);
//        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));
//
//
//        notificationManager.notify(MY_NOTIFICATION_ID, builder.build());
//        }
//}

