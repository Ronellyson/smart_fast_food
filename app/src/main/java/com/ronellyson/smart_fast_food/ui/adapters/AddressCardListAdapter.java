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
import com.ronellyson.smart_fast_food.ui.contracts.OnAddressSelectedListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddressCardListAdapter extends RecyclerView.Adapter<AddressCardListAdapter.ViewHolder> {

    private List<Address> addressList = new ArrayList<>();
    private static Boolean showCheckBoxes;
    private static Boolean showActionButtons;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    private OnAddressSelectedListener onAddressSelectedListener;

    // Add a new constructor to receive a boolean flag indicating if the cards should have selectable checkboxes or not
    public AddressCardListAdapter(SharedPreferences sharedPreferences, Boolean showCheckBoxes, Boolean showActionButtons) {
        this.sharedPreferences = sharedPreferences;
        AddressCardListAdapter.showCheckBoxes = showCheckBoxes;
        AddressCardListAdapter.showActionButtons = showActionButtons;
        retrieveAddressList();
    }


    // Método para salvar a lista de endereços no SharedPreferences
    private void saveAddressList() {
        Set<String> addressStringSet = new HashSet<>();
        for (Address address : addressList) {
            String addressJson = gson.toJson(address);
            addressStringSet.add(addressJson);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("addressList", addressStringSet);
        editor.apply();
    }

    // Método para carregar a lista de endereços do SharedPreferences
    private void retrieveAddressList() {
        Set<String> addressStringSet = sharedPreferences.getStringSet("addressList", new HashSet<>());
        addressList.clear();
        for (String addressJson : addressStringSet) {
            Address address = gson.fromJson(addressJson, Address.class);
            if (address != null) {
                addressList.add(address);
            }
        }
    }

    // Método para remover um endereço da lista
    public void removeAddress(int position) {
        if (position >= 0 && position < addressList.size()) {
            addressList.remove(position);
            notifyItemRemoved(position);
            saveAddressList(); // Salva a lista atualizada no SharedPreferences
        }
    }

    public void setSelectedAddress(Address selectedAddress) {
        // Convert the selectedAddress object to a JSON string
        String selectedAddressJson = gson.toJson(selectedAddress);

        // Save the JSON string to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedAddress", selectedAddressJson);
        editor.apply();

        // Update the isSelected property for each address in the list
        for (Address address : addressList) {
            address.setIsSelected(address.equals(selectedAddress));
        }
        notifyDataSetChanged();
    }


    public void setOnAddressSelectedListener(OnAddressSelectedListener onAddressSelectedListener) {
        this.onAddressSelectedListener = onAddressSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressList.get(position);

        // Preencha os elementos de layout com os dados do endereço
        holder.textHolder.setText(address.getHolder());
        holder.textStreet.setText(address.getStreet());
        holder.textNumber.setText(address.getNumber());
        holder.textNeighborhood.setText(address.getNeighborhood());
        holder.textZipCode.setText(address.getZipCode());
        holder.textState.setText(address.getState());
        holder.textCountry.setText(address.getCountry());
        holder.textPhone.setText(address.getPhone());

        // Defina a visibilidade da CheckBox com base na propriedade "selected" do endereço
        holder.checkBox.setChecked(address.isSelected());

        // Set a click listener for the address card item
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCheckBoxes) {
                    // Toggle the checkbox selection for the address
                    address.setIsSelected(!address.isSelected());
                    // Update the checkbox state
                    holder.checkBox.setChecked(address.isSelected());

                    if (onAddressSelectedListener != null && address.isSelected()) {
                        onAddressSelectedListener.onAddressSelected(address);
                    }

                    // Notify the adapter of the selected address, so it can update the other checkboxes
                    setSelectedAddress(address);
                }
            }
        });

        // Add a click listener to the deleteButton
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getBindingAdapterPosition();
                removeAddress(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textHolder;
        public TextView textStreet;
        public TextView textNumber;
        public TextView textNeighborhood;
        public TextView textZipCode;
        public TextView textState;
        public TextView textCountry;
        public TextView textPhone;
        public CheckBox checkBox;
        public ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textHolder = itemView.findViewById(R.id.address_holder);
            textStreet = itemView.findViewById(R.id.address_street);
            textNumber = itemView.findViewById(R.id.address_number);
            textNeighborhood = itemView.findViewById(R.id.address_neighborhood);
            textZipCode = itemView.findViewById(R.id.address_zip_code);
            textState = itemView.findViewById(R.id.address_state);
            textCountry = itemView.findViewById(R.id.address_country);
            textPhone = itemView.findViewById(R.id.address_phone);
            checkBox = itemView.findViewById(R.id.address_selector_checkbox);
            deleteButton = itemView.findViewById(R.id.delete_button);

            // Get the LinearLayout containing the CheckBox in the card layout
            LinearLayout checkBoxLayout = itemView.findViewById(R.id.address_selector_checkbox_container);
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
