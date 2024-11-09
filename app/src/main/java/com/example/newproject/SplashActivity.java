package com.example.newproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

    Button loginButton, signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginButton = findViewById(R.id.btn_login);
        signInButton = findViewById(R.id.btn_signin);

        // On login button click, navigate to LoginActivity
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // On sign-in button click, navigate to SignUpActivity
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
