package com.ronellyson.smart_fast_food.ui.categorySlider;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ronellyson.smart_fast_food.data.model.Category;

public class CategoryButtonModel extends ViewModel {
    private Category selectedCategory = new Category(0,"");
    private MutableLiveData<Category> _onCategorySelect = new MutableLiveData<>();
    public LiveData<Category> onCategorySelect = _onCategorySelect;

    public void onCategorySelect() {
        if (_onCategorySelect != null) {
            _onCategorySelect.setValue(selectedCategory);
        }
    }

    public void onChangedCategorySelect(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
        Log.d("sda",selectedCategory.getName());
    }
}
