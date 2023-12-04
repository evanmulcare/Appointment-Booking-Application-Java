package com.example.evan_mulcare_assignment_two.Views;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.evan_mulcare_assignment_two.Controllers.LoginController;
import com.example.evan_mulcare_assignment_two.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerText;
    private LoginController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new LoginController(this);
    }

    public EditText getUsernameEditText() {
        if (usernameEditText == null) {
            usernameEditText = findViewById(R.id.usernameEditText);
        }
        return usernameEditText;
    }

    public EditText getPasswordEditText() {
        if (passwordEditText == null) {
            passwordEditText = findViewById(R.id.passwordEditText);
        }
        return passwordEditText;
    }

    public Button getLoginButton() {
        if (loginButton == null) {
            loginButton = findViewById(R.id.loginButton);
        }
        return loginButton;
    }

    public TextView getRegisterText() {
        if (registerText == null) {
            registerText = findViewById(R.id.RegisterTextView);
        }
        return registerText;
    }
}