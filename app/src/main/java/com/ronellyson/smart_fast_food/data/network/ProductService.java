package com.ronellyson.smart_fast_food.data.network;


import com.ronellyson.smart_fast_food.data.network.response.ProductResponse;

import java.io.Console;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("best-foods")
    Call<List<ProductResponse>> getBestProducts();
}
