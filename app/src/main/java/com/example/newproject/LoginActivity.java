package com.example.newproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

public class LoginActivity extends AppCompatActivity {

    EditText editUsername, editPassword;
    Button loginButton;

    // Placeholder credentials (you can replace this with actual verification logic later)
    String correctUsername = "Meghana";
    String correctPassword = "Meghana";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.btn_login);

        // Handle login button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = editUsername.getText().toString();
                String inputPassword = editPassword.getText().toString();

                // Verify credentials
                if (inputUsername.equals(correctUsername) && inputPassword.equals(correctPassword)) {
                    // Navigate to HomeActivity if credentials are correct
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close LoginActivity
                    // Close LoginActivity
                } else {
                    // Show error popup if credentials are incorrect
                    showErrorDialog();
                }
            }
        });
    }

    // Method to show error popup
    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Failed");
        builder.setMessage("Incorrect username or password");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
