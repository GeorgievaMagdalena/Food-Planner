package com.example.foodplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Registration  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void RegisterUser(View view){

        EditText firstname = findViewById(R.id.firstname);
        EditText lastname = findViewById(R.id.lastname);
        EditText email = findViewById(R.id.emailNew);
        EditText password = findViewById(R.id.passwordNew);

        Button button_add=(Button)findViewById(R.id.registerButton);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler databaseHandler = new DatabaseHandler(Registration.this);

                String emailToCheck = email.getText().toString();
                if (databaseHandler.checkEmail(emailToCheck)) {
                    Toast.makeText(Registration.this, "This account already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    UserModel userModel;

                    try {
                        userModel = new UserModel(-1, firstname.getText().toString(), lastname.getText().toString(), email.getText().toString(),
                                password.getText().toString(), null, null, false);
                        Toast.makeText(Registration.this, "User added!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), WelcomePage.class);
                        intent.putExtra("userEmail", emailToCheck);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(Registration.this, "Error creating user", Toast.LENGTH_SHORT).show();
                        userModel = new UserModel(-1, "error", "error", "error", "error", "error", "error", false);
                    }


                    boolean success = databaseHandler.RegisterUser(userModel);
                    Toast.makeText(Registration.this, "Success= " + success, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void ToLogin(View view) {
        TextView text= (TextView)findViewById(R.id.accountLink);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivityIntent = new Intent(Registration.this, Login.class);
                startActivity(newActivityIntent);
            }
        });

    }
}
