package com.ronellyson.smart_fast_food.ui.productList;

import com.ronellyson.smart_fast_food.data.model.Product;

import java.util.List;

public interface ProductListContract {

    public interface view{

        void showProducts(List<Product> products);

        void showMessageError();

    }

    public interface presenter {
        void getProducts();
        void destroyView();
    }
}
