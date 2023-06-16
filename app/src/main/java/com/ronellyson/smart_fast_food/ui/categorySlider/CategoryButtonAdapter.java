package com.ronellyson.smart_fast_food.ui.categorySlider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Category;

import java.util.List;

public class CategoryButtonAdapter extends RecyclerView.Adapter<CategoryButtonAdapter.ButtonViewHolder> {
    private List<Category> buttonList;
    private CategoryButtonModel model;
    private int selectedItemPosition = -1;

    public CategoryButtonAdapter(List<Category> buttonList, CategoryButtonModel model) {
        this.buttonList = buttonList;
        this.model = model;

        // Adicione o observer para monitorar a seleção do botão no modelo
        model.getOnCategorySelect().observeForever(new Observer<Category>() {
            @Override
            public void onChanged(Category selectedCategory) {
                int previousSelectedItem = selectedItemPosition;
                selectedItemPosition = buttonList.indexOf(selectedCategory);
                notifyItemChanged(previousSelectedItem);
                notifyItemChanged(selectedItemPosition);
            }
        });
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_button, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Category category = buttonList.get(position);
        String buttonLabel = category.getName();
        holder.button.setText(buttonLabel);

        // Define o background personalizado com bordas arredondadas
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.rounded_button_background);
        holder.button.setBackground(drawable);

        // Define a cor do botão com base no estado de seleção
        Context context = holder.itemView.getContext();
        if (position == selectedItemPosition) {
            holder.button.setSelected(true);
            holder.button.setBackgroundColor(context.getResources().getColor(R.color.selectedButton));
        } else {
            holder.button.setSelected(false);
            holder.button.setBackgroundColor(context.getResources().getColor(R.color.unselectedButton));
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.onChangedCategorySelect(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }
}
