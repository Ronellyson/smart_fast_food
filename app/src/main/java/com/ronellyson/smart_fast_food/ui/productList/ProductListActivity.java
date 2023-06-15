package com.ronellyson.smart_fast_food.ui.productList;


import static com.ronellyson.smart_fast_food.ui.productDetails.ProductDetailsActivity.EXTRA_FOOD;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        configAdapter();

        presenter = new ProductListPresenter(this);
        presenter.getProducts();

        RecyclerView category_recycler_view = findViewById(R.id.category_recycler_view);
        List<Category> buttonList = new ArrayList<>();
        Category category1 = new Category(1, "melhores");
        Category category2 = new Category(2, "piores");
        Category category3 = new Category(3, "horriveis");

        buttonList.add(category1);
        buttonList.add(category2);
        buttonList.add(category3);

        CategoryButtonAdapter categoryButtonAdapter = new CategoryButtonAdapter(buttonList);
        category_recycler_view.setAdapter(categoryButtonAdapter);
        category_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Get the ViewModel.
        CategoryButtonModel model = new ViewModelProvider(this).get(CategoryButtonModel.class);

        // Create the observer which updates the UI.
        final Observer<Category> nameObserver = new Observer<Category>() {
            @Override
            public void onChanged(@Nullable final Category category) {
                // Update the UI, in this case, a TextView.
                model.onChangedCategorySelect(category);
                model.onCategorySelect();
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

    public void onCategoryClicked(Category category) {
        presenter.getProductsByCategory(category);
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
