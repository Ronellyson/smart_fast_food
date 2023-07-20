package com.ronellyson.smart_fast_food.ui.adapters;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Address;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddressCardListAdapter extends RecyclerView.Adapter<AddressCardListAdapter.ViewHolder> {

    private List<Address> addressList = new ArrayList<>();
    private static Boolean showCheckBoxes;

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    // Add a new constructor to receive a boolean flag indicating if the cards should have selectable checkboxes or not
    public AddressCardListAdapter(SharedPreferences sharedPreferences, boolean showCheckBoxes) {
        this.sharedPreferences = sharedPreferences;
        this.showCheckBoxes = showCheckBoxes;
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

            // Get the LinearLayout containing the CheckBox in the card layout
            LinearLayout checkBoxLayout = itemView.findViewById(R.id.address_selector_checkbox_container);
            // Set the visibility of the CheckBox based on the showCheckBoxes flag
            if (showCheckBoxes) {
                checkBoxLayout.setVisibility(View.VISIBLE);
            } else {
                checkBoxLayout.setVisibility(View.GONE);
            }
        }
    }
}
