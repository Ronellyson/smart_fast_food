package com.ronellyson.smart_fast_food.ui.productList;


import static com.ronellyson.smart_fast_food.ui.productDetails.ProductDetailsActivity.EXTRA_FOOD;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.Category;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.ronellyson.smart_fast_food.ui.categorySlider.CategoryButtonAdapter;
import com.ronellyson.smart_fast_food.ui.categorySlider.CategoryButtonModel;
import com.ronellyson.smart_fast_food.ui.productDetails.ProductDetailsActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductListContract.view, ProductListAdapter.ClickItem{

    private ProductListAdapter productListAdapter;
    private ProductListPresenter presenter;

    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat);
        setContentView(R.layout.activity_main);

        CategoryButtonModel model = new ViewModelProvider(this).get(CategoryButtonModel.class);

        configAdapter();

        presenter = new ProductListPresenter(this);
        presenter.getProductsByCategory(model.getOnCategorySelect().getValue());

        RecyclerView category_recycler_view = findViewById(R.id.category_recycler_view);
        Category category1 = new Category(1, "best-foods");
        Category category2 = new Category(2, "breads");
        Category category3 = new Category(3, "burgers");
        Category category4 = new Category(4, "chocolates");
        Category category5 = new Category(5, "desserts");
        Category category6 = new Category(6, "drinks");
        Category category7 = new Category(7, "fried-chicken");
        Category category8 = new Category(8, "ice-cream");
        Category category9 = new Category(9, "pizzas");
        Category category10 = new Category(10, "porks");
        Category category11 = new Category(11, "sandwiches");

        List<Category> buttonList = new ArrayList<>(Arrays.asList(category1, category2, category3, category4, category5, category6, category7, category8, category9, category10, category11));

        CategoryButtonAdapter categoryButtonAdapter = new CategoryButtonAdapter(buttonList, model);
        category_recycler_view.setAdapter(categoryButtonAdapter);
        category_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

// Create the observer which updates the UI.
        final Observer<Category> nameObserver = new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                Log.d("", category.getName());
                // Aqui você pode realizar as ações desejadas quando um botão for clicado.
            }
        };

// Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.onCategorySelect.observe(this, nameObserver);


        searchView = findViewById(R.id.searchView);

        // Obtém o EditText interno do SearchView
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        // Define a cor do texto como preto
        searchEditText.setTextColor(Color.BLACK);

        // Configura um listener de foco para redefinir a cor do texto ao perder o foco
        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    searchEditText.setTextColor(Color.BLACK);
                }
            }
        });

        // Define um layout personalizado para o EditText para evitar que a cor do texto seja redefinida quando ganha o foco
        searchEditText.setLayoutDirection(View.LAYOUT_DIRECTION_LOCALE);

        removeSearchViewUnderline(searchView);

    }

    private void removeSearchViewUnderline(SearchView searchView) {
        try {
            Field searchPlateField = SearchView.class.getDeclaredField("mSearchPlate");
            searchPlateField.setAccessible(true);
            Drawable searchPlateDrawable = (Drawable) searchPlateField.get(searchView);
            searchPlateDrawable.setAlpha(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void configAdapter(){
        RecyclerView product_recycler_view = findViewById(R.id.product_recycler_view);

        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(this);
        productListAdapter = new ProductListAdapter(this);

        product_recycler_view.setLayoutManager(linearLayout);
        product_recycler_view.setAdapter(productListAdapter);
    }

    @Override
    public void showProducts(List<Product> products) {
        productListAdapter.setProducts(products);
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

    @Override
    public void onItemClicked(Product product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra(EXTRA_FOOD, product.getName());
        startActivity(intent);
    }
}
