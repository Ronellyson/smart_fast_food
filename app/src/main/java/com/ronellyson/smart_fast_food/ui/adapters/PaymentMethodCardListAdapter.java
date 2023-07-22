package com.ronellyson.smart_fast_food.ui.adapters;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Address;
import com.ronellyson.smart_fast_food.data.model.CreditDebitCard;
import com.ronellyson.smart_fast_food.ui.contracts.OnPaymentMethodSelectedListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaymentMethodCardListAdapter extends RecyclerView.Adapter<PaymentMethodCardListAdapter.ViewHolder> {

    private List<CreditDebitCard> cardList;
    private static boolean showCheckBoxes;
    private static boolean showActionButtons;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    private OnPaymentMethodSelectedListener onPaymentMethodSelectedListener;


    public PaymentMethodCardListAdapter(SharedPreferences sharedPreferences, boolean showCheckBoxes, boolean showActionButtons) {
        this.sharedPreferences = sharedPreferences;
        PaymentMethodCardListAdapter.showCheckBoxes = showCheckBoxes;
        PaymentMethodCardListAdapter.showActionButtons = showActionButtons;
        cardList = new ArrayList<>();
        retrieveCardList();
    }

    private void saveCardList() {
        Set<String> cardStringSet = new HashSet<>();
        for (CreditDebitCard card : cardList) {
            String cardJson = gson.toJson(card);
            cardStringSet.add(cardJson);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("paymentMethodCardList", cardStringSet);
        editor.apply();
    }

    private void retrieveCardList() {
        Set<String> cardStringSet = sharedPreferences.getStringSet("paymentMethodCardList", new HashSet<>());
        cardList.clear();
        for (String cardJson : cardStringSet) {
            CreditDebitCard card = gson.fromJson(cardJson, CreditDebitCard.class);
            if (card != null) {
                cardList.add(card);
            }
        }
    }

    public void removeCard(int position) {
        if (position >= 0 && position < cardList.size()) {
            cardList.remove(position);
            notifyItemRemoved(position);
            saveCardList();
        }
    }

    public void setSelectedPaymentMethod(CreditDebitCard selectedPaymentMethod) {
        // Convert the selectedPaymentMethod object to a JSON string
        String selectedPaymentMethodJson = gson.toJson(selectedPaymentMethod);

        // Save the JSON string to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedPaymentMethod", selectedPaymentMethodJson);
        editor.apply();

        // Update the isSelected property for each card in the list
        for (CreditDebitCard card : cardList) {
            card.setIsSelected(card.equals(selectedPaymentMethod));
        }
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnPaymentMethodSelectedListener onPaymentMethodSelectedListener) {
        this.onPaymentMethodSelectedListener = onPaymentMethodSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_method_card, parent, false);
        return new ViewHolder(view, showCheckBoxes, showActionButtons);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CreditDebitCard card = cardList.get(position);

        holder.textCardType.setText(card.isCredit() ? "Crédito" : "Débito");
        holder.textCardNumber.setText(card.getCardNumber());
        holder.textCardHolder.setText(card.getCardHolderName());
        holder.textExpiryDate.setText(card.getExpirationDate());
        holder.textCVV.setText(card.getSecurityCode());
        holder.checkBox.setChecked(card.isSelected());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCheckBoxes) {
                    // Toggle the checkbox selection for the address
                    card.setIsSelected(!card.isSelected());
                    // Update the checkbox state
                    holder.checkBox.setChecked(card.isSelected());

                    if (onPaymentMethodSelectedListener != null && card.isSelected()) {
                        onPaymentMethodSelectedListener.onPaymentMethodSelected(card);
                    }

                    // Notify the adapter of the selected address, so it can update the other checkboxes
                    setSelectedPaymentMethod(card);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getBindingAdapterPosition();
                removeCard(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textCardType;
        public TextView textCardNumber;
        public TextView textCardHolder;
        public TextView textExpiryDate;
        public TextView textCVV;
        public CheckBox checkBox;
        public ImageButton deleteButton;

        public ViewHolder(View itemView, boolean showCheckBoxes, boolean showActionButtons) {
            super(itemView);
            textCardType = itemView.findViewById(R.id.payment_type);
            textCardNumber = itemView.findViewById(R.id.payment_card_number);
            textCardHolder = itemView.findViewById(R.id.payment_card_holder);
            textExpiryDate = itemView.findViewById(R.id.payment_expiry_date);
            textCVV = itemView.findViewById(R.id.payment_cvv);
            checkBox = itemView.findViewById(R.id.payment_selector_checkbox);
            deleteButton = itemView.findViewById(R.id.delete_button);

            // Get the LinearLayout containing the CheckBox in the card layout
            LinearLayout checkBoxLayout = itemView.findViewById(R.id.payment_selector_checkbox_container);
            // Set the visibility of the CheckBox based on the showCheckBoxes flag
            if (showCheckBoxes) {
                checkBoxLayout.setVisibility(View.VISIBLE);
            } else {
                checkBoxLayout.setVisibility(View.GONE);
            }

            // Get the LinearLayout containing the action buttons in the card layout
            LinearLayout actionButtonsLayout = itemView.findViewById(R.id.action_buttons_container);
            // Set the visibility of the action buttons based on the showActionButtons flag
            if (showActionButtons) {
                actionButtonsLayout.setVisibility(View.VISIBLE);
            } else {
                actionButtonsLayout.setVisibility(View.GONE);
            }
        }
    }
}

