package Notifications;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import Assessments.AssessmentChoose;
import com.zybooks.wgu.R;
import com.zybooks.wgu.SQLdatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Assessment_Notification_Service4 extends BroadcastReceiver {

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

            SQLdatabase database = new SQLdatabase(context);

            String todaysDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/dd/yyyy"));

            if (database.readPerformanceEndDates(todaysDate)) {

                int draw = R.drawable.baseline_emoji_people_24;

                Intent intent2 = new Intent(context, AssessmentChoose.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_IMMUTABLE);

                NotificationChannel channel = new NotificationChannel("notification", "notification", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = getSystemService(context, NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification")
                        .setSmallIcon(draw)
                        .setContentTitle("Assessment Alert")
                        .setContentText("You have a Performance assessment that ends today !")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat manager = NotificationManagerCompat.from(context);
                manager.notify(1, builder.build());
            }

        }

    }

