package com.ronellyson.smart_fast_food.ui.contracts;

import android.widget.ImageView;

import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.data.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductCardListContract {

    public interface view{

        void showProducts(List<Product> products);

        void showMessageError();
    }

    public interface presenter {
        void getProductsFiltered(String categoryName, String searchQuery);
        void destroyView();
    }
}