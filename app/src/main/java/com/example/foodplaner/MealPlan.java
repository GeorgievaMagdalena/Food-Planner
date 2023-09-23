package com.example.foodplaner;

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

        //NAPRAJ DA SE POVIKUVA VTORIOT onBindView
        mRecyclerView = (RecyclerView) findViewById(R.id.listMyMeals);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new mySecondAdapter(meals, R.layout.my_meal, this);

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        ArrayList<String> mealNames = new ArrayList<>();
        for (mySecondAdapter.Recipe recipe : meals) {
            //String mealName = ;
            mealNames.add(recipe.getName());
        }
        String MealNames = mealNames.toString();
        SendNotif(MealNames);
    }

    private NotificationCompat.Builder getNotificationBuilder(String names) {

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this);
        notifyBuilder.setContentTitle("Today's meals")
                .setContentText(names)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                // .setContentIntent(notificationPendingIntent)
                // .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    public void SendNotif(String names){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(names);
        Notification notification = notifyBuilder.build();
        mNotifManager.notify(NOTIFICATION_ID, notification);
    }
}
