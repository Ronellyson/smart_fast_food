package com.ronellyson.smart_fast_food.ui.adapters;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.ProductCartItem;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ProductCartItemAdapter extends RecyclerView.Adapter<ProductCartItemAdapter.ViewHolder> {

    private List<ProductCartItem> productCartItems = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    private Button continueButton;
    private boolean continueButtonClicked = false;

    public ProductCartItemAdapter(SharedPreferences sharedPreferences, Button continueButton) {
        this.sharedPreferences = sharedPreferences;
        this.continueButton = continueButton;
        retrieveProductCartItems();
        updateContinueButtonState();
    }

    private void retrieveProductCartItems() {
        String productCartItemsJson = sharedPreferences.getString("productCartItems", null);
        if (productCartItemsJson != null) {
            productCartItems = gson.fromJson(productCartItemsJson, new TypeToken<List<ProductCartItem>>() {}.getType());
        } else {
            productCartItems = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCartItem item = productCartItems.get(position);

        if (item.getProduct() != null) {
            // Set the item data to the views inside the ViewHolder
            holder.productCartItemTitle.setText(item.getProduct().getName());
            holder.productCartItemPrice.setText("R$ " + item.getProduct().getPrice().setScale(2, RoundingMode.HALF_UP).toString());
            holder.productCartItemQuantityText.setText(String.valueOf(item.getProductCartItemQuantity()));
            // Load the image using Glide
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(item.getProduct().getUrlImage()))
                    .into(holder.productCartItemImage);

            // Set the click listener for the minus button
            holder.minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    decreaseQuantity(item);
                }
            });

            // Set the click listener for the plus button
            holder.plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increaseQuantity(item);
                }
            });

            // Set the click listener for the delete button
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeProductCartItemFromList(item.getProduct().getId());
                }
            });
        }
    }

    private void decreaseQuantity(ProductCartItem item) {
        int quantity = item.getProductCartItemQuantity();
        if (quantity > 1) {
            item.setProductCartItemQuantity(quantity - 1);
            updateQuantityInSharedPreferences(item);
            notifyDataSetChanged();
        }
    }

    private void increaseQuantity(ProductCartItem item) {
        int quantity = item.getProductCartItemQuantity();
        item.setProductCartItemQuantity(quantity + 1);
        updateQuantityInSharedPreferences(item);
        notifyDataSetChanged();
    }

    private void removeProductCartItemFromList(String productId) {
        for (int i = 0; i < productCartItems.size(); i++) {
            ProductCartItem item = productCartItems.get(i);
            if (item.getProduct().getId().equals(productId)) {
                productCartItems.remove(i);
                updateProductCartItemsInSharedPreferences();
                notifyDataSetChanged();
                updateContinueButtonState();
                break;
            }
        }
    }

    private void updateQuantityInSharedPreferences(ProductCartItem item) {
        String productCartItemsJson = gson.toJson(productCartItems);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productCartItems", productCartItemsJson);
        editor.apply();
    }

    private void updateProductCartItemsInSharedPreferences() {
        String productCartItemsJson = gson.toJson(productCartItems);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productCartItems", productCartItemsJson);
        editor.apply();
    }

    private void updateContinueButtonState() {
        if (productCartItems.isEmpty()) {
            continueButton.setEnabled(false);
        } else {
            continueButton.setEnabled(true);
        }
    }

    public void setContinueButtonClicked(boolean clicked) {
        continueButtonClicked = clicked;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("continueButtonClicked", continueButtonClicked);
        editor.apply();
        Log.d("setContinueButtonClicked", sharedPreferences.getAll().toString());
    }

    @Override
    public int getItemCount() {
        return productCartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productCartItemImage;
        TextView productCartItemTitle;
        TextView productCartItemPrice;
        Button minusButton;
        Button plusButton;
        TextView productCartItemQuantityText;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productCartItemImage = itemView.findViewById(R.id.product_cart_item_image);
            productCartItemTitle = itemView.findViewById(R.id.product_cart_item_title);
            productCartItemPrice = itemView.findViewById(R.id.product_cart_item_price);
            minusButton = itemView.findViewById(R.id.minus_button);
            plusButton = itemView.findViewById(R.id.plus_button);
            productCartItemQuantityText = itemView.findViewById(R.id.product_cart_item_quantity_text);
            deleteButton = itemView.findViewById(R.id.product_cart_item_delete_button);
        }
    }
}
