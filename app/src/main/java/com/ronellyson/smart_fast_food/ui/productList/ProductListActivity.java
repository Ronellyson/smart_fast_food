package com.ronellyson.smart_fast_food.ui.productList;

import static com.ronellyson.smart_fast_food.ui.productDetails.ProductDetailsActivity.EXTRA_FOOD;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class ProductListActivity extends AppCompatActivity implements ProductListContract.view, ProductListAdapter.ClickItem {

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
        final Observer<Category> categoryObserver = new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                presenter.getProductsByCategory(category);
            }
        };

        model.onCategorySelect.observe(this, categoryObserver);

        RecyclerView categoryRecyclerView = findViewById(R.id.category_recycler_view);
        List<Category> buttonList = createCategoryList();
        CategoryButtonAdapter categoryButtonAdapter = new CategoryButtonAdapter(buttonList, model);
        categoryRecyclerView.setAdapter(categoryButtonAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Digite sua busca aqui");
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    searchEditText.setTextColor(Color.BLACK);
                }
            }
        });
        searchEditText.setLayoutDirection(View.LAYOUT_DIRECTION_LOCALE);
        removeSearchViewUnderline(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.filterProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        initializeData();
    }

    private List<Category> createCategoryList() {
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

        return new ArrayList<>(Arrays.asList(
                category1, category2, category3, category4, category5, category6,
                category7, category8, category9, category10, category11
        ));
    }


    private void initializeData() {
        presenter.getProductsByCategory(new Category(0,"best-foods"));
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.filterProducts(newText);
                return true;
            }
        });
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

    public void configAdapter() {
        RecyclerView productRecyclerView = findViewById(R.id.product_recycler_view);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productListAdapter = new ProductListAdapter(this);
        productRecyclerView.setLayoutManager(linearLayoutManager);
        productRecyclerView.setAdapter(productListAdapter);
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
