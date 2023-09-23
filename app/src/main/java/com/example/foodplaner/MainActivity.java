package com.example.foodplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ToLogin(View view){
         Button login = (Button)(findViewById(R.id.loginPage));
         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getApplicationContext(), Login.class);
                 startActivity(intent);
             }
         });

    }

    public void ToRegistration(View view){
        Button register = (Button)(findViewById(R.id.registerPage));
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });

    }



}