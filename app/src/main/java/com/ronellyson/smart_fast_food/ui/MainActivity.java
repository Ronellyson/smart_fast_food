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

        showHomePageFragment();
    }

    public void showHomePageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentProductCartPage productCartPageFragment = (FragmentProductCartPage) fragmentManager.findFragmentByTag("ProductCartPage");
        if (productCartPageFragment != null) {
            fragmentTransaction.remove(productCartPageFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("HomePage");
        if (fragment == null) {
            fragmentTransaction.add(R.id.container_root, FragmentHomePage.newInstance(), "HomePage");
        }

        fragmentTransaction.commit();
    }

    public void onCartButtonPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Verificar se a página do carrinho de compras já está sendo exibida
        Fragment fragmentProductCart = fragmentManager.findFragmentByTag("ProductCartPage");
        if (fragmentProductCart != null && fragmentProductCart.isVisible()) {
            // A página do carrinho de compras já está sendo exibida, não é necessário fazer nada
            return;
        }

        // Remover fragmento da página de detalhes do pedido, se estiver visível
        Fragment fragmentOrderDetails = fragmentManager.findFragmentByTag("OrderDetailsPage");
        if (fragmentOrderDetails != null && fragmentOrderDetails.isVisible()) {
            fragmentManager.beginTransaction().remove(fragmentOrderDetails).commit();
        }

        // Exibir a página do carrinho de compras
        showProductCartPageFragment();
    }


    public void showProductCartPageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentHomePage homePageFragment = (FragmentHomePage) fragmentManager.findFragmentByTag("HomePage");
        if (homePageFragment != null) {
            fragmentTransaction.remove(homePageFragment);
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
        showHomePageFragment();
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
