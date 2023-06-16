package com.ronellyson.smart_fast_food.ui.productList;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.ronellyson.smart_fast_food.data.dto.ProductDTO;
import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.data.network.ApiProduct;
import com.ronellyson.smart_fast_food.data.network.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListPresenter implements ProductListContract.presenter {

    private ProductListContract.view view;
    private List<Product> allProducts; // Lista de todos os produtos

    public ProductListPresenter(ProductListContract.view view) {
        this.view = view;
        this.allProducts = new ArrayList<>(); // Inicializa a lista de produtos
    }

    public void getProductsByCategory(Category category) {
        if (category != null) {
            ApiProduct.getINSTANCE().getProductsByCategory(category.getName())
                    .enqueue(new Callback<List<ProductResponse>>() {
                        @Override
                        public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                            if (response.isSuccessful()) {
                                List<Product> products = ProductDTO.convertProductResponseForProduct(response.body());
                                allProducts = products; // Atualiza a lista de todos os produtos
                                view.showProducts(products);
                            } else {
                                view.showMessageError();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                            view.showMessageError();
                        }
                    });
        }
    }

    public void filterProducts(String query) {
        if (query.isEmpty()) {
            view.showProducts(allProducts); // Mostra todos os produtos sem filtro
        } else {
            List<Product> filteredProducts = new ArrayList<>();
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }
            view.showProducts(filteredProducts); // Mostra apenas os produtos filtrados
        }
    }

    @Override
    public void destroyView() {
        this.view = null;
    }
}

