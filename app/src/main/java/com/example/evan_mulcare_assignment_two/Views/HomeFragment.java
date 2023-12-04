package com.example.evan_mulcare_assignment_two.Views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.example.evan_mulcare_assignment_two.Models.CurrentUserSingleton;
import com.example.evan_mulcare_assignment_two.R;
public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String username = CurrentUserSingleton.getInstance().getCurrentUser().getUsername();

        TextView welcomeText = view.findViewById(R.id.welcomeUserText);
        welcomeText.setText("Hey " + username + "...");

        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        CurrentUserSingleton.getInstance().clearCurrentUser();
    }
}
