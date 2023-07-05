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
import com.google.gson.Gson;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.data.model.ProductCartItem;

import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

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

    public ProductCardListAdapter(List<Product> products, OnAddButtonClickListener listener, SharedPreferences sharedPreferences) {
        this.products = products;
        this.addButtonClickListener = listener;
        this.sharedPreferences = sharedPreferences;
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
                            boolean isProductAddedToCart = isProductAlreadyAdded(product.getId());

                            // Save or remove the product ID in SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if (!isProductAddedToCart) {
                                // Convert Product to ProductCartItem
                                ProductCartItem productCartItem = new ProductCartItem(UUID.randomUUID().toString(), product, 1, true);
                                editor.putString(String.valueOf(product.getId()), productCartItemToJson(productCartItem));
                            } else {
                                editor.remove(String.valueOf(product.getId()));
                            }
                            editor.apply();

                            // Update the button color and text based on the product's cart status
                            updateButtonAppearance(isProductAddedToCart);

                            Log.d("ProductCardListAdapter", "Product ID: " + product.getId() + ", Added to cart: " + !isProductAddedToCart);
                        }
                    }
                }
            });
        }

        private boolean isProductAlreadyAdded(String productId) {
            return sharedPreferences.contains(productId);
        }

        private void updateButtonAppearance(boolean isProductAddedToCart) {
            String productId = products.get(getAdapterPosition()).getId();
            boolean isProductInCartItem = isProductInCartItem(productId);

            if (isProductInCartItem) {
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

        private boolean isProductInCartItem(String productId) {
            String productCartItemJson = sharedPreferences.getString(productId, null);
            return productCartItemJson != null;
        }

        public void bind(Product product) {
            listTitle.setText(product.getName());
            listDescription.setText(product.getDescription());
            listPrice.setText("R$ " + String.valueOf(product.getPrice().setScale(2, RoundingMode.HALF_UP)));
            Glide.with(itemView.getContext()).load(product.getUrlImage()).into(listImg);

            updateButtonAppearance(isProductAlreadyAdded(product.getId()));
        }
    }

    // Method to convert ProductCartItem to JSON string
    private String productCartItemToJson(ProductCartItem productCartItem) {
        Gson gson = new Gson();
        return gson.toJson(productCartItem);
    }
}
