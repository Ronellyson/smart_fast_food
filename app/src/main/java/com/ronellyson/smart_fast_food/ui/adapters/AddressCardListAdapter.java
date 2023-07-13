package com.ronellyson.smart_fast_food.ui.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Address;

import java.util.List;

public class AddressCardListAdapter extends RecyclerView.Adapter<AddressCardListAdapter.ViewHolder> {

    private List<Address> addressList;

    public AddressCardListAdapter(List<Address> addressList) {
        this.addressList = addressList;
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
        if (address.isSelected()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
        }
    }
}