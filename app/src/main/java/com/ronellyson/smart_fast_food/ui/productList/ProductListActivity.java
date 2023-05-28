package com.ronellyson.smart_fast_food.ui.productList;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Product;

import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductListContract.view{

    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private ProductListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configAdapter();

        presenter = new ProductListPresenter(this);
        presenter.getProducts();
    }

    public void configAdapter(){
        recyclerView = findViewById(R.id.my_recycler_view);

        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(this);
        adapter = new ProductListAdapter();


        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProducts(List<Product> products) {
        adapter.setProducts(products);
    }

    @Override
    public void showMessageError() {
        Toast.makeText(this, "Erro ao carregar o cardápio", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}
