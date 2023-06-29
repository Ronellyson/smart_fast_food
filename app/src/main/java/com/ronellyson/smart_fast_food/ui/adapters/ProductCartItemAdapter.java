package com.ronellyson.smart_fast_food.ui.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.ProductCartItem;

import java.util.ArrayList;
import java.util.List;

public class ProductCartItemAdapter extends RecyclerView.Adapter<ProductCartItemAdapter.ViewHolder> {

    private List<ProductCartItem> items;

    public ProductCartItemAdapter() {
        this.items = new ArrayList<>();
    }

    public ProductCartItemAdapter(List<ProductCartItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCartItem item = items.get(position);
        // Configurar os dados do item nos elementos de visualização (TextViews, ImageViews, etc.) dentro do ViewHolder
        holder.productCartItemTitle.setText(item.getProductCartItemTitle());
        holder.productCartItemPrice.setText(item.getProductCartItemPrice());
        holder.productCartItemQuantity.setText(item.getProductCartItemQuantity().toString());

        // Carregar a imagem usando Glide
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(item.getProductCartItemImage()))
                .into(holder.productCartItemImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productCartItemImage;
        TextView productCartItemTitle;
        TextView productCartItemPrice;
        TextView productCartItemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productCartItemImage = itemView.findViewById(R.id.product_cart_item_image);
            productCartItemTitle = itemView.findViewById(R.id.product_cart_item_title);
            productCartItemPrice = itemView.findViewById(R.id.product_cart_item_price);
            productCartItemQuantity = itemView.findViewById(R.id.product_cart_item_quantity);
        }
    }
}
