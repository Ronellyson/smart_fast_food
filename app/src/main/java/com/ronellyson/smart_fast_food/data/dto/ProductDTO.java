package com.ronellyson.smart_fast_food.data.dto;

import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.data.network.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
    public static List<Product> convertProductResponseForProduct(List<ProductResponse> productResponse){
        List<Product> foods = new ArrayList<>();

        for(ProductResponse response: productResponse){
            Product food = new Product(response.getName(), response.getImage(), response.getPrice(),
                    response.getDescription(), response.getRate());

            foods.add(food);
        }

        return foods;
    }
}
