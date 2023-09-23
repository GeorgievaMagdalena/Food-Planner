package com.example.foodplaner;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DirectionsView extends AppCompatActivity {

    TextView textView;
    String directions;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        directions = getIntent().getStringExtra("directions");
        textView = findViewById(R.id.directions);
        textView.setText(directions);
    }

}
