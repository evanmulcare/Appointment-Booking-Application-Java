package com.example.evan_mulcare_assignment_two.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.evan_mulcare_assignment_two.Controllers.RegisterController;

import com.example.evan_mulcare_assignment_two.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView backButton;
    private Button registerButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private RegisterController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new RegisterController(this);
    }

    public ImageView getBackButton() {
        if (backButton == null) {
            backButton = findViewById(R.id.BackButton);
        }
        return backButton;
    }

    public Button getRegisterButton() {
        if (registerButton == null) {
            registerButton = findViewById(R.id.RegisterButton);
        }
        return registerButton;
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

    public EditText getConfirmPasswordEditText() {
        if (confirmPasswordEditText == null) {
            confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        }
        return confirmPasswordEditText;
    }
}