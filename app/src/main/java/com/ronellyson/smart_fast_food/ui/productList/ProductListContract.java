package com.ronellyson.smart_fast_food.ui.productList;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;

import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.data.model.Product;

import java.util.List;

public interface ProductListContract {

    public interface view{

        void showProducts(List<Product> products);

        void showMessageError();
    }

    public interface presenter {
        void getProductsByCategory(LiveData<Category> category);
        void destroyView();
    }
}
