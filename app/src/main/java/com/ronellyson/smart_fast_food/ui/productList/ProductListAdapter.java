package com.ronellyson.smart_fast_food.ui.productList;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private List<Product> products;

    public ProductListAdapter() {
        this.products = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return (products != null && products.size() >0) ? products.size() : 0;
    }

    public static class ProductListViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;
        private TextView price;
        private TextView description;

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_food);
            image = itemView.findViewById(R.id.image_food);
            price = itemView.findViewById(R.id.price_food);
            description = itemView.findViewById(R.id.description_food);

        }

        public void bind(Product product){
            name.setText(product.getName());
            price.setText("$"+(product.getPrice()));
            description.setText(product.getDescription());

            Picasso.get().load(product.getImage()).into(image);
        }
    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }
}
