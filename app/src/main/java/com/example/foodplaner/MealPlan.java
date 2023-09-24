package com.example.foodplaner;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MealPlan extends AppCompatActivity {

    RecyclerView mRecyclerView;
    mySecondAdapter mAdapter;
    int userId;

    private static final String PRIMARY_CHANNEL_ID = "primary_notificaion_channel";
    private NotificationManager mNotifManager;
    private static final int NOTIFICATION_ID=0;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        mNotifManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        DatabaseHandler databaseHandler = new DatabaseHandler(MealPlan.this);
        userId = getIntent().getIntExtra("userId", 0);
        ArrayList<mySecondAdapter.Recipe> meals = databaseHandler.getMeals(userId);
        Log.i("PR", String.valueOf(meals));

        mRecyclerView = (RecyclerView) findViewById(R.id.listMyMeals);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new mySecondAdapter(meals, R.layout.my_meal, this);

        mRecyclerView.setAdapter(mAdapter);

        ArrayList<String> mealNames = new ArrayList<>();
        for (mySecondAdapter.Recipe recipe : meals) {
            mealNames.add(recipe.getName());
        }
        String MealNames = String.join(", ", mealNames);


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        SendNotif(MealNames, pendingIntent);
    }

    private NotificationCompat.Builder getNotificationBuilder(String names, PendingIntent pendingI) {

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this);
        notifyBuilder.setContentTitle("Today's meals")
                .setContentText(names)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                 .setContentIntent(pendingI)
                // .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    public void SendNotif(String names, PendingIntent pendingIntent){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(names,pendingIntent);
        Notification notification = notifyBuilder.build();
        mNotifManager.notify(NOTIFICATION_ID, notification);
    }
}
