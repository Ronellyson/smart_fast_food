package com.ronellyson.smart_fast_food.ui.presenters;

import com.ronellyson.smart_fast_food.data.dto.ProductDTO;
import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.data.network.ApiProduct;
import com.ronellyson.smart_fast_food.data.network.response.ProductResponse;
import com.ronellyson.smart_fast_food.ui.contracts.ProductCardListContract;
import com.ronellyson.smart_fast_food.ui.fragments.FragmentProductCardList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCardListPresenter implements ProductCardListContract.presenter {
    private FragmentProductCardList view;

    public ProductCardListPresenter(FragmentProductCardList view) {
        this.view = view;
    }

    @Override
    public void getProductsByCategory(Category category) {
        ApiProduct.getINSTANCE().getProductsByCategory(category.getName())
                .enqueue(new Callback<List<ProductResponse>>() {
                    @Override
                    public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                        if (response.isSuccessful()) {
                            List<Product> products = ProductDTO.convertProductResponseForProduct(response.body());
                            view.setProducts(products);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                    }
                });
    }

    @Override
    public void destroyView() {
        // Limpar quaisquer referências ou recursos que precisam ser liberados
        view = null;
    }
}
