package com.example.evan_mulcare_assignment_two.Controllers;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.evan_mulcare_assignment_two.Models.DatabaseHandlerSingleton;
import com.example.evan_mulcare_assignment_two.Models.User;
import com.example.evan_mulcare_assignment_two.R;
import com.example.evan_mulcare_assignment_two.Views.LoginActivity;
import com.example.evan_mulcare_assignment_two.Views.RegisterActivity;

public class RegisterController {
    private final RegisterActivity view;
    public RegisterController(RegisterActivity view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.setContentView(R.layout.activity_register);

        view.getBackButton().setOnClickListener(v -> navigateToLoginActivity());

        view.getRegisterButton().setOnClickListener(v -> {
            String username = view.getUsernameEditText().getText().toString();
            String password = view.getPasswordEditText().getText().toString();
            String confirmPassword = view.getConfirmPasswordEditText().getText().toString();

            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && password.equals(confirmPassword)) {
                registerUser(username, password);
            } else {
                Toast.makeText(view, R.string.registration_failed_credential_mismatch, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(String username, String password) {
        User newUser = new User(0, username, password);
        boolean userAdded = DatabaseHandlerSingleton.getInstance(view).addUser(newUser);
        if (userAdded) {
            Toast.makeText(view, R.string.registration_successful, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(view, R.string.registration_failed_try_again, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(view, LoginActivity.class);
        view.startActivity(intent);
    }
}