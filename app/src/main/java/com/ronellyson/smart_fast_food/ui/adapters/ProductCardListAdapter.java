package com.ronellyson.smart_fast_food.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Product;

import java.math.RoundingMode;
import java.util.List;

public class ProductCardListAdapter extends RecyclerView.Adapter<ProductCardListAdapter.ViewHolder> {
    private List<Product> products;

    public ProductCardListAdapter(List<Product> products) {
        this.products = products;
    }

    public void updateProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView listImg;
        TextView listTitle;
        TextView listDescription;
        TextView listPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            listImg = itemView.findViewById(R.id.product_card_image);
            listTitle = itemView.findViewById(R.id.product_card_title);
            listDescription = itemView.findViewById(R.id.product_card_description);
            listPrice = itemView.findViewById(R.id.product_card_price);
        }

        public void bind(Product product) {
            listTitle.setText(product.getName());
            listDescription.setText(product.getDescription());
            listPrice.setText("$ " + String.valueOf(product.getPrice().setScale(2, RoundingMode.HALF_UP)));
            Glide.with(itemView.getContext()).load(product.getUrlImage()).into(listImg);
        }
    }
}
