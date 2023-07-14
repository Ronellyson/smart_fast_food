package com.ronellyson.smart_fast_food.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.fragments.pages.FragmentDynamicPage;
import com.ronellyson.smart_fast_food.ui.fragments.pages.FragmentHomePage;
import com.ronellyson.smart_fast_food.ui.fragments.pages.FragmentOrderDetailsPage;
import com.ronellyson.smart_fast_food.ui.fragments.pages.FragmentProductCartPage;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String CONTINUE_BUTTON_CLICKED_KEY = "continueButtonClicked";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CONTINUE_BUTTON_CLICKED_KEY, false);
        editor.apply();

        showDynamicPageFragment();
    }

    public void showDynamicPageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag("FragmentDynamicPage");
        if (fragment == null) {
            fragment = FragmentDynamicPage.newInstance();
            fragmentTransaction.add(R.id.container_root, fragment, "FragmentDynamicPage");
        }

        fragmentTransaction.commit();
    }


    public void showProductCartPageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) fragmentManager.findFragmentByTag("FragmentDynamicPage");
        if (fragmentDynamicPage != null) {
            fragmentTransaction.remove(fragmentDynamicPage);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("ProductCartPage");
        if (fragment == null) {
            FragmentProductCartPage productCartPageFragment = FragmentProductCartPage.newInstance();
            fragmentTransaction.add(R.id.container_root, productCartPageFragment, "ProductCartPage");
        }

        fragmentTransaction.commit();
    }

    public void showOrderDetailsPageFragment() {
        boolean continueButtonClicked = sharedPreferences.getBoolean(CONTINUE_BUTTON_CLICKED_KEY, false);

        if (continueButtonClicked) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FragmentProductCartPage productCartPageFragment = (FragmentProductCartPage) fragmentManager.findFragmentByTag("ProductCartPage");
            if (productCartPageFragment != null) {
                fragmentTransaction.remove(productCartPageFragment);
            }

            // Crie uma instância do fragmento FragmentOrderDetailsPage
            FragmentOrderDetailsPage orderDetailsPageFragment = FragmentOrderDetailsPage.newInstance();

            // Adicione o fragmento ao contêiner raiz
            fragmentTransaction.add(R.id.container_root, orderDetailsPageFragment, "OrderDetailsPage");

            fragmentTransaction.commit();
        }
    }

    public void onBackToHomePagePressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Remover fragmento da página do carrinho de compras, se estiver visível
        Fragment fragmentProductCart = fragmentManager.findFragmentByTag("ProductCartPage");
        if (fragmentProductCart != null && fragmentProductCart.isVisible()) {
            fragmentManager.beginTransaction().remove(fragmentProductCart).commit();
        }

        // Exibir a página inicial
        showDynamicPageFragment();
    }

    public void onBackToCartPagePressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Remover fragmentos da view anterior
        Fragment fragment = fragmentManager.findFragmentByTag("OrderDetailsPage");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }

        // Exibir a página do carrinho de compras
        showProductCartPageFragment();
    }
}
