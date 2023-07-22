package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Address;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FragmentAddressRegistrationPage extends Fragment {
    SharedPreferences sharedPreferences;
    private EditText holderEditText;
    private EditText streetEditText;
    private EditText numberEditText;
    private EditText neighborhoodEditText;
    private EditText zipCodeEditText;
    private EditText stateEditText;
    private EditText countryEditText;
    private EditText phoneEditText;

    public static FragmentAddressRegistrationPage newInstance(SharedPreferences sharedPreferences) {
        FragmentAddressRegistrationPage fragment = new FragmentAddressRegistrationPage();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.address_registration_page, container, false);

        holderEditText = rootView.findViewById(R.id.edit_holder);
        streetEditText = rootView.findViewById(R.id.edit_street);
        numberEditText = rootView.findViewById(R.id.edit_number);
        neighborhoodEditText = rootView.findViewById(R.id.edit_neighborhood);
        zipCodeEditText = rootView.findViewById(R.id.edit_zip_code);
        stateEditText = rootView.findViewById(R.id.edit_state);
        countryEditText = rootView.findViewById(R.id.edit_country);
        phoneEditText = rootView.findViewById(R.id.edit_phone);
        Button registerButton = rootView.findViewById(R.id.button_register);
        Button cancelButton = rootView.findViewById(R.id.button_cancel);

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
                    Address newAddress = new Address(holder, street, number, neighborhood, zipCode, state, country, phone);
                    addAddressToSharedPreferences(newAddress);
                    clearFormField();
                    showAddressRegisteredPopup();
                }
            }

            private void addAddressToSharedPreferences(Address address) {
                // Obter a lista de endereços atual do SharedPreferences
                Set<String> addressesSet = sharedPreferences.getStringSet("addressList", new HashSet<>());
                Gson gson = new Gson();
                List<Address> addressList = new ArrayList<>();

                // Converter o conjunto de endereços em uma lista
                for (String addressJson : addressesSet) {
                    Address existingAddress = gson.fromJson(addressJson, Address.class);
                    if (existingAddress != null) {
                        addressList.add(existingAddress);
                    }
                }

                // Adicionar o novo endereço à lista
                addressList.add(address);

                // Converter a lista atualizada de volta para um conjunto
                Set<String> updatedAddressesSet = new HashSet<>();
                for (Address updatedAddress : addressList) {
                    String updatedAddressJson = gson.toJson(updatedAddress);
                    updatedAddressesSet.add(updatedAddressJson);
                }

                // Salvar o conjunto atualizado no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("addressList", updatedAddressesSet);
                editor.apply();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) requireParentFragment();

                fragmentDynamicPage.onBackToAddressManagementPagePressed();
            }
        });
        return rootView;
    }
    // Method to clear all form fields
    private void clearFormField() {
        holderEditText.setText("");
        streetEditText.setText("");
        numberEditText.setText("");
        neighborhoodEditText.setText("");
        zipCodeEditText.setText("");
        stateEditText.setText("");
        countryEditText.setText("");
        phoneEditText.setText("");
    }

    // Method to show the address registration confirmation pop-up
    public void showAddressRegisteredPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Endereço Registrado");
        builder.setMessage("O endereço foi registrado com sucesso!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
