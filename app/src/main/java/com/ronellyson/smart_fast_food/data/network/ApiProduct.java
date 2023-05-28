package com.ronellyson.smart_fast_food.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiProduct {
    private static ProductService INSTANCE;

    public static ProductService getINSTANCE(){
        if(INSTANCE == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://free-food-menus-api.onrender.com/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            INSTANCE = retrofit.create(ProductService.class);
        }

        return INSTANCE;
    }
}
