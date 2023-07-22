package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.CreditDebitCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FragmentPaymentMethodRegistrationPage extends Fragment {
    SharedPreferences sharedPreferences;
    private View rootView;
    private EditText holderEditText;
    private EditText cardNumberEditText;
    private EditText expiryDateEditText;
    private EditText securityCodeEditText;
    private Button registerButton;
    private Button cancelButton;
    private String selectedPaymentType = "Credit";

    public static FragmentPaymentMethodRegistrationPage newInstance(SharedPreferences sharedPreferences) {
        FragmentPaymentMethodRegistrationPage fragment = new FragmentPaymentMethodRegistrationPage();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.payment_method_registration_page, container, false);

        holderEditText = rootView.findViewById(R.id.edit_holder);
        cardNumberEditText = rootView.findViewById(R.id.edit_card_number);
        expiryDateEditText = rootView.findViewById(R.id.edit_expiry_date);
        securityCodeEditText = rootView.findViewById(R.id.edit_security_code);
        registerButton = rootView.findViewById(R.id.button_register);
        cancelButton = rootView.findViewById(R.id.button_cancel);

        RadioGroup paymentTypeRadioGroup = rootView.findViewById(R.id.radio_group_payment_type);
        paymentTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_credit) {
                    selectedPaymentType = "Credit";
                } else if (checkedId == R.id.radio_debit) {
                    selectedPaymentType = "Debit";
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasError = false;
                String holder = holderEditText.getText().toString().trim();
                String cardNumber = cardNumberEditText.getText().toString().trim();
                String expiryDate = expiryDateEditText.getText().toString().trim();
                String securityCode = securityCodeEditText.getText().toString().trim();

                if (holder.isEmpty()) {
                    holderEditText.setError("Please enter a card holder name");
                    holderEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && cardNumber.isEmpty()) {
                    cardNumberEditText.setError("Please enter a card number");
                    cardNumberEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && expiryDate.isEmpty()) {
                    expiryDateEditText.setError("Please enter the expiry date (MM/YY)");
                    expiryDateEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError && securityCode.isEmpty()) {
                    securityCodeEditText.setError("Please enter the security code (CVV)");
                    securityCodeEditText.requestFocus();
                    hasError = true;
                }

                if (!hasError) {
                    CreditDebitCard newCard = new CreditDebitCard(cardNumber, holder, expiryDate, securityCode, selectedPaymentType.equals("Credit"));
                    addCardToSharedPreferences(newCard);
                    clearFormField();
                    showCardRegisteredPopup();
                }
            }

            private void addCardToSharedPreferences(CreditDebitCard card) {
                // Obter a lista de cartões atual do SharedPreferences
                Set<String> cardSet = sharedPreferences.getStringSet("paymentMethodCardList", new HashSet<>());
                Gson gson = new Gson();
                List<CreditDebitCard> cardList = new ArrayList<>();

                // Converter o conjunto de cartões em uma lista
                for (String cardJson : cardSet) {
                    CreditDebitCard existingCard = gson.fromJson(cardJson, CreditDebitCard.class);
                    if (existingCard != null) {
                        cardList.add(existingCard);
                    }
                }

                // Adicionar o novo cartão à lista
                cardList.add(card);

                // Converter a lista atualizada de volta para um conjunto
                Set<String> updatedCardSet = new HashSet<>();
                for (CreditDebitCard updatedCard : cardList) {
                    String updatedCardJson = gson.toJson(updatedCard);
                    updatedCardSet.add(updatedCardJson);
                }

                // Salvar o conjunto atualizado no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("paymentMethodCardList", updatedCardSet);
                editor.apply();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) requireParentFragment();
                fragmentDynamicPage.onBackToPaymentMethodManagementPagePressed();
            }
        });
        return rootView;
    }

    // Method to clear all form fields
    private void clearFormField() {
        holderEditText.setText("");
        cardNumberEditText.setText("");
        expiryDateEditText.setText("");
        securityCodeEditText.setText("");
        // Reset the radio buttons to default selection (Credit)
        RadioGroup paymentTypeRadioGroup = rootView.findViewById(R.id.radio_group_payment_type);
        paymentTypeRadioGroup.check(R.id.radio_credit);
    }

    // Method to show the card registration confirmation pop-up
    public void showCardRegisteredPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Card Registered");
        builder.setMessage("The card has been registered successfully!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
