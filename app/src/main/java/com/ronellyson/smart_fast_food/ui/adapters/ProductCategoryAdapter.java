package com.ronellyson.smart_fast_food.ui.adapters;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentProductCategoryButtonList;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {
    private List<Category> categories;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private String selectedCategoryName = "";

    public ProductCategoryAdapter(List<Category> categories, SharedPreferences sharedPreferences) {
        this.categories = categories;
        this.sharedPreferences = sharedPreferences;
        this.gson = new Gson();
        // Recuperar o nome da categoria selecionada do SharedPreferences, se existir
        selectedCategoryName = getSelectedCategoryName();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_category_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.updateButtonAppearance(category.getName().equals(selectedCategoryName));
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    // ViewHolder para os itens do RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button selectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selectButton = itemView.findViewById(R.id.categoryButton);

            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getBindingAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        Category category = categories.get(adapterPosition);
                        String categoryName = category.getName();

                        // Verificar se a categoria já está selecionada
                        if (categoryName.equals(selectedCategoryName)) {
                            // Categoria já selecionada, deselecionar
                            selectedCategoryName = "";
                        } else {
                            // Nova categoria selecionada
                            selectedCategoryName = categoryName;
                        }

                        // Salvar o nome da categoria selecionada no SharedPreferences
                        saveSelectedCategoryName(selectedCategoryName);

                        Log.d("ProductCategoryAdapter", sharedPreferences.getAll().toString());

                        // Notificar as alterações aos itens
                        notifyDataSetChanged();
                    }
                }
            });
        }

        private void updateButtonAppearance(boolean isSelected) {
            if (isSelected) {
                selectButton.setBackgroundResource(R.color.selectedColorAddButton);
                selectButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.selectedColorButtonText));
                selectButton.setOutlineSpotShadowColor(ContextCompat.getColor(itemView.getContext(), R.color.selectedColorButtonText));
            } else {
                selectButton.setBackgroundResource(R.color.normalColorAddButton);
                selectButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.normalColorButtonText));
            }
        }

        public void bind(Category category) {
            selectButton.setText(category.getName());
            updateButtonAppearance(category.getName().equals(selectedCategoryName));
        }

        private void saveSelectedCategoryName(String categoryName) {
            sharedPreferences.edit()
                    .putString(FragmentProductCategoryButtonList.getProductCategoryButtonSelectedKey(), categoryName)
                    .apply();
        }
    }

    private String getSelectedCategoryName() {
        return sharedPreferences.getString(FragmentProductCategoryButtonList.getProductCategoryButtonSelectedKey(), "");
    }
}
