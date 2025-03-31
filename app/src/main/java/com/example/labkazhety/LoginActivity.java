package com.example.labkazhety;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edUsername, edPassword;
    private Button btnLogin, btnSignUp;
    private static final String PREFS_NAME = "UserPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String usernameInput = edUsername.getText().toString().trim();
        String passwordInput = edPassword.getText().toString().trim();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(usernameInput)) {
            Toast.makeText(this, "Пользователь не найден, зарегистрируйтесь", Toast.LENGTH_SHORT).show();
            return;
        }

        String savedPassword = sharedPreferences.getString(usernameInput, "");

        if (passwordInput.equals(savedPassword)) {
            Toast.makeText(this, "Вход успешный!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Неверное имя пользователя или пароль", Toast.LENGTH_SHORT).show();
        }
    }
}
