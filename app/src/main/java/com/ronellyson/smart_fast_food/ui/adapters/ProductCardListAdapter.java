package com.ronellyson.smart_fast_food.ui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Product;

import java.math.RoundingMode;
import java.util.List;

public class ProductCardListAdapter extends RecyclerView.Adapter<ProductCardListAdapter.ViewHolder> {
    private List<Product> products;
    private OnAddButtonClickListener addButtonClickListener;
    private SharedPreferences sharedPreferences;

    public void setOnAddButtonClickListener(OnAddButtonClickListener listener) {
        this.addButtonClickListener = listener;
    }

    public interface OnAddButtonClickListener {
        void onAddButtonClick(int position);
    }

    public ProductCardListAdapter(List<Product> products, OnAddButtonClickListener listener, Context context) {
        this.products = products;
        this.addButtonClickListener = listener;
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView listImg;
        TextView listTitle;
        TextView listDescription;
        TextView listPrice;

        Button addButton;

        public ViewHolder(View itemView) {
            super(itemView);
            listImg = itemView.findViewById(R.id.product_card_image);
            listTitle = itemView.findViewById(R.id.product_card_title);
            listDescription = itemView.findViewById(R.id.product_card_description);
            listPrice = itemView.findViewById(R.id.product_card_price);
            addButton = itemView.findViewById(R.id.product_card_add_button);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        if (addButtonClickListener != null) {
                            addButtonClickListener.onAddButtonClick(adapterPosition);

                            Product product = products.get(adapterPosition);
                            boolean isProductAddedToCart = sharedPreferences.getBoolean(String.valueOf(product.getId()), false);
                            boolean newProductAddedToCart = !isProductAddedToCart;

                            // Save or remove the product ID in SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if (newProductAddedToCart) {
                                editor.putBoolean(String.valueOf(product.getId()), true);
                            } else {
                                editor.remove(String.valueOf(product.getId()));
                            }
                            editor.apply();

                            // Update the button color and text based on the product's cart status
                            updateButtonAppearance(newProductAddedToCart);

                            Log.d("ProductCardListAdapter", "Product ID: " + product.getId() + ", Added to cart: " + newProductAddedToCart);
                        }
                    }
                }
            });
            // Call updateButtonAppearance during initialization
            updateButtonAppearance(false);
        }

        private void updateButtonAppearance(boolean isProductAddedToCart) {
            if (isProductAddedToCart) {
                addButton.setBackgroundResource(R.color.selectedColorAddButton);
                addButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.selectedColorButtonText));
                addButton.setOutlineSpotShadowColor(ContextCompat.getColor(itemView.getContext(), R.color.selectedColorButtonText));
                addButton.setText(R.string.remove_button_text);
            } else {
                addButton.setBackgroundResource(R.color.normalColorAddButton);
                addButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.normalColorButtonText));
                addButton.setText(R.string.add_button_text);
            }
        }
        public void bind(Product product) {
            listTitle.setText(product.getName());
            listDescription.setText(product.getDescription());
            listPrice.setText("$ " + String.valueOf(product.getPrice().setScale(2, RoundingMode.HALF_UP)));
            Glide.with(itemView.getContext()).load(product.getUrlImage()).into(listImg);
        }
    }
}
