package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ronellyson.smart_fast_food.R;

public class FragmentAddressRegistrationPage extends Fragment {
    SharedPreferences sharedPreferences;

    public static FragmentAddressRegistrationPage newInstance(SharedPreferences sharedPreferences) {
        FragmentAddressRegistrationPage fragment = new FragmentAddressRegistrationPage();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.address_registration_page, container, false);
        ImageButton backButton = rootView.findViewById(R.id.address_registration_page_back_button);
        EditText holderEditText = rootView.findViewById(R.id.edit_holder);
        EditText streetEditText = rootView.findViewById(R.id.edit_street);
        EditText numberEditText = rootView.findViewById(R.id.edit_number);
        EditText neighborhoodEditText = rootView.findViewById(R.id.edit_neighborhood);
        EditText zipCodeEditText = rootView.findViewById(R.id.edit_zip_code);
        EditText stateEditText = rootView.findViewById(R.id.edit_state);
        EditText countryEditText = rootView.findViewById(R.id.edit_country);
        EditText phoneEditText = rootView.findViewById(R.id.edit_phone);
        Button registerButton = rootView.findViewById(R.id.button_register);
        Button cancelButton = rootView.findViewById(R.id.button_cancel);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) requireParentFragment();
                fragmentDynamicPage.onBackToAddressManagementPagePressed();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasError = false;
                String holder = holderEditText.getText().toString().trim();
                String street = streetEditText.getText().toString().trim();
                String number = numberEditText.getText().toString().trim();
                String neighborhood = neighborhoodEditText.getText().toString().trim();
                String zipCode = zipCodeEditText.getText().toString().trim();
                String state = stateEditText.getText().toString().trim();
                String country = countryEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                if (holder.isEmpty()) {
                    holderEditText.setError("Please enter a holder name");
                    holderEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && street.isEmpty()) {
                    streetEditText.setError("Please enter a street");
                    streetEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && number.isEmpty()) {
                    numberEditText.setError("Please enter a number");
                    numberEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && neighborhood.isEmpty()) {
                    neighborhoodEditText.setError("Please enter a neighborhood");
                    neighborhoodEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && zipCode.isEmpty()) {
                    zipCodeEditText.setError("Please enter a zip code");
                    zipCodeEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && state.isEmpty()) {
                    stateEditText.setError("Please enter a state");
                    stateEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && country.isEmpty()) {
                    countryEditText.setError("Please enter a country");
                    countryEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && phone.isEmpty()) {
                    phoneEditText.setError("Please enter a phone number");
                    phoneEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError) {
                    // Perform registration logic here
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform cancel logic here
            }
        });

        return rootView;
    }
}
