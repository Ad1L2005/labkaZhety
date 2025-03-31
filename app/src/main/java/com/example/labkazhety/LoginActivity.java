package com.example.labkazhety;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edUsername, edPassword;
    private Button btnLogin, btnSignUp;
    private static final String PREFS_NAME = "UserPrefs";


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
