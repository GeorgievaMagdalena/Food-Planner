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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseHandler DB;
        DB = new DatabaseHandler(this);

        EditText emailOb = (EditText) findViewById(R.id.email);
        EditText passOb= (EditText) findViewById(R.id.password);

        Button button= (Button)findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String email = emailOb.getText().toString().trim();
                String pass= passOb.getText().toString().trim();

                if (email.matches("")||pass.matches("")) {
                    Toast.makeText(Login.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;

                }
                else{
                    Boolean checkemail=DB.checkEmail(email);
                    if(checkemail==false) {
                        Toast.makeText(Login.this, "You don't have an account. Please click on the button below to register!", Toast.LENGTH_SHORT).show();
                    }else{
                        Boolean checkemail_password=DB.checkEmailPassword(email, pass);
                        if(checkemail_password==false){
                            Toast.makeText(Login.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent=new Intent(getApplicationContext(), WelcomePage.class);
                            intent.putExtra("userEmail", email);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    public void ToRegistration(View view) {
        TextView text = (TextView) findViewById(R.id.newuserLink);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivityIntent = new Intent(Login.this, Registration.class);
                startActivity(newActivityIntent);
            }
        });
    }
}

