package com.example.newproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText editName, editPhone, editEmail, editAddress, editPassword;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        editEmail = findViewById(R.id.edit_email);
        editAddress = findViewById(R.id.edit_address);
        editPassword = findViewById(R.id.edit_password);
        signUpButton = findViewById(R.id.btn_sign_up);

        // Handle sign-up button click
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String email = editEmail.getText().toString();
                String address = editAddress.getText().toString();
                String password = editPassword.getText().toString();

                // For now, store data in SharedPreferences
                if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !address.isEmpty() && !password.isEmpty()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("phone", phone);
                    editor.putString("email", email);
                    editor.putString("address", address);
                    editor.putString("password", password);
                    editor.apply();

                    // Navigate to HomeActivity after successful sign-up
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close SignUpActivity
                    // Close SignUpActivity
                } else {
                    Toast.makeText(SignUpActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
