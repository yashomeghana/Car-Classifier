package com.example.newproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    TextView textName, textPhone, textEmail, textAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find views by ID
        textName = view.findViewById(R.id.text_name);
        textPhone = view.findViewById(R.id.text_phone);
        textEmail = view.findViewById(R.id.text_email);
        textAddress = view.findViewById(R.id.text_address);

        // Load user data from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", getContext().MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "N/A");
        String phone = sharedPreferences.getString("phone", "N/A");
        String email = sharedPreferences.getString("email", "N/A");
        String address = sharedPreferences.getString("address", "N/A");

        // Set the user data to the TextViews
        textName.setText("Name: " + name);
        textPhone.setText("Phone: " + phone);
        textEmail.setText("Email: " + email);
        textAddress.setText("Address: " + address);

        return view;
    }
}
