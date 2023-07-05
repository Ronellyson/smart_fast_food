package com.ronellyson.smart_fast_food.ui.contracts;

import java.util.List;

public interface ProductCartItemListContract {
    public interface view{

    }

    public interface presenter {
        void getProductsByListProductId(List<String> idList);
        void destroyView();
    }
}

