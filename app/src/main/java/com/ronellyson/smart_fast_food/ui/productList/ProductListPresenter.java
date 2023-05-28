package com.ronellyson.smart_fast_food.ui.productList;

import com.ronellyson.smart_fast_food.data.dto.ProductDTO;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.data.network.ApiProduct;
import com.ronellyson.smart_fast_food.data.network.response.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListPresenter implements ProductListContract.presenter{

    private ProductListContract.view view;

    public ProductListPresenter(ProductListContract.view view) {
        this.view = view;
    }

    @Override
    public void getProducts() {
        ApiProduct.getINSTANCE().getBestFoods()
                .enqueue(new Callback<List<ProductResponse>>() {
                    @Override
                    public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                        if(response.isSuccessful()){
                            List<Product> products = ProductDTO.convertFoodResponseForProduct(response.body());
                            view.showProducts(products);
                        }else{
                            view.showMessageError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                        view.showMessageError();
                    }
                });
    }

    @Override
    public void destroyView() {
        this.view = null;
    }
}
