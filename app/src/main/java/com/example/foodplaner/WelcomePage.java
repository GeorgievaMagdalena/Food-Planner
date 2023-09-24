package com.example.foodplaner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage  extends AppCompatActivity {

    private TextView userName;
    String userEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        userName = findViewById(R.id.nameUser);
        userEmail = getIntent().getStringExtra("userEmail");
        DatabaseHandler databaseHandler = new DatabaseHandler(WelcomePage.this);
        String firstName = databaseHandler.returnName(userEmail);
        Log.i("IME", firstName);

        if (firstName != null) {
            userName.setText("Welcome, " + firstName);
        } else {
            userName.setText("Welcome!");
        }

        Button button = (Button)findViewById(R.id.proceedButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChoiceRecipe.class);
                intent.putExtra("userId", databaseHandler.returnId(userEmail));
                startActivity(intent);
            }
        });
    }

    public void ToSurvey(View view) {
        Button button = (Button) findViewById(R.id.takesurveyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey.class);
                intent.putExtra("email", userEmail);
                startActivity(intent);
            }
        });
    }



}

