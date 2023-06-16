package com.ronellyson.smart_fast_food.ui.categorySlider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.ui.categorySlider.CategoryButtonModel;

import java.util.List;

public class CategoryButtonAdapter extends RecyclerView.Adapter<CategoryButtonAdapter.ButtonViewHolder> {
    private List<Category> buttonList;
    private CategoryButtonModel model;

    public CategoryButtonAdapter(List<Category> buttonList, CategoryButtonModel model) {
        this.buttonList = buttonList;
        this.model = model;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_button, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        final Category category = buttonList.get(position);
        String buttonLabel = category.getName();
        holder.button.setText(buttonLabel);

        int[] colors = {R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary}; // Adicione mais cores conforme necessário

        int colorIndex = position % colors.length;
        int colorRes = colors[colorIndex];
        holder.button.setBackgroundResource(colorRes);

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
