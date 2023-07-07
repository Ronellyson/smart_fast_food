package com.ronellyson.smart_fast_food.ui.adapters;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Map;

public class ProductCartItemAdapter extends RecyclerView.Adapter<ProductCartItemAdapter.ViewHolder> {

    private List<ProductCartItem> productCartItems = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public ProductCartItemAdapter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        retrieveProductCartItems();
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
            // Configurar os dados do item nos elementos de visualização (TextViews, ImageViews, etc.) dentro do ViewHolder
            holder.productCartItemTitle.setText(item.getProduct().getName());
            holder.productCartItemPrice.setText("R$ " + item.getProduct().getPrice().setScale(2, RoundingMode.HALF_UP).toString());
            holder.productCartItemQuantityText.setText(String.valueOf(item.getProductCartItemQuantity()));
            // Carregar a imagem usando Glide
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(item.getProduct().getUrlImage()))
                    .into(holder.productCartItemImage);

            // Definir o clique do botão de subtração
            holder.minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    decreaseQuantity(item);
                }
            });

            // Definir o clique do botão de adição
            holder.plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increaseQuantity(item);
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

    private void updateQuantityInSharedPreferences(ProductCartItem item) {
        String productCartItemsJson = gson.toJson(productCartItems);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productCartItems", productCartItemsJson);
        editor.apply();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productCartItemImage = itemView.findViewById(R.id.product_cart_item_image);
            productCartItemTitle = itemView.findViewById(R.id.product_cart_item_title);
            productCartItemPrice = itemView.findViewById(R.id.product_cart_item_price);
            minusButton = itemView.findViewById(R.id.minus_button);
            plusButton = itemView.findViewById(R.id.plus_button);
            productCartItemQuantityText = itemView.findViewById(R.id.product_cart_item_quantity_text);
        }
    }
}
