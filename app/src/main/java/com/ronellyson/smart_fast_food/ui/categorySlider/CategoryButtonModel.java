package com.ronellyson.smart_fast_food.ui.categorySlider;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ronellyson.smart_fast_food.data.model.Category;

public class CategoryButtonModel extends ViewModel {
    private MutableLiveData<Category> _onCategorySelect = new MutableLiveData<>();
    public MutableLiveData<Category> onCategorySelect = _onCategorySelect;

    public void onCategorySelect() {
        _onCategorySelect.setValue(onCategorySelect.getValue());
    }

    public void onChangedCategorySelect(Category selectedCategory) {
        onCategorySelect.setValue(selectedCategory);
    }

    public LiveData<Category> getOnCategorySelect() {
        return onCategorySelect;
    }
}
