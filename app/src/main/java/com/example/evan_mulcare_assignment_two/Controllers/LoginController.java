package com.example.evan_mulcare_assignment_two.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.evan_mulcare_assignment_two.Models.CurrentUserSingleton;
import com.example.evan_mulcare_assignment_two.Models.DatabaseHandlerSingleton;
import com.example.evan_mulcare_assignment_two.Models.User;
import com.example.evan_mulcare_assignment_two.R;
import com.example.evan_mulcare_assignment_two.Views.LoginActivity;
import com.example.evan_mulcare_assignment_two.Views.NavigationBar;
import com.example.evan_mulcare_assignment_two.Views.RegisterActivity;

public class LoginController {
    private final LoginActivity view;
    public LoginController(LoginActivity view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.setContentView(R.layout.activity_login);
        DatabaseHandlerSingleton.getInstance(this.getContext());
        view.getUsernameEditText().setOnClickListener(v -> attemptLogin());
        view.getPasswordEditText().setOnClickListener(v -> attemptLogin());

        view.getLoginButton().setOnClickListener(v -> attemptLogin());
        view.getRegisterText().setOnClickListener(v -> navigateToRegisterActivity());
    }

    private void attemptLogin() {
        String username = view.getUsernameEditText().getText().toString();
        String password = view.getPasswordEditText().getText().toString();
        User loggedInUser = checkUserInDatabase(username, password);

        // Check if the user exists in the database
        if (loggedInUser != null) {
            // User is in the database, proceed to the next activity
            CurrentUserSingleton.getInstance().setCurrentUser(loggedInUser);
            navigateToNavigationBar();
        } else {
            Toast.makeText(getContext(), R.string.invalid_username_password, Toast.LENGTH_SHORT).show();
        }
    }

    private User checkUserInDatabase(String username, String password) {
        return DatabaseHandlerSingleton.getInstance(getContext()).getUserByUsernameAndPassword(username, password);
    }

    private void navigateToNavigationBar() {
        Intent intent = new Intent(view, NavigationBar.class);
        view.startActivity(intent);
    }

    private void navigateToRegisterActivity() {
        Intent intent = new Intent(view, RegisterActivity.class);
        view.startActivity(intent);
    }

    private Context getContext() {
        return view.getApplicationContext();
    }
}
