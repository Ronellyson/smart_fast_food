package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentNavigationDrawer;

public class FragmentDynamicPage extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private SharedPreferences sharedPreferences;
    private FragmentNavigationDrawer fragmentNavigationDrawer;
    private static final String CONTINUE_BUTTON_CLICKED_KEY = "continueButtonClicked";


    public static FragmentDynamicPage newInstance() {
        return new FragmentDynamicPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dynamic_page_fragment, container, false);

        // Obtém o SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CONTINUE_BUTTON_CLICKED_KEY, false);
        editor.apply();

        // Cria uma instância do FragmentNavigationDrawer
        fragmentNavigationDrawer = FragmentNavigationDrawer.newInstance(sharedPreferences);

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento do FragmentNavigationDrawer ao drawer_container
        fragmentTransaction.add(R.id.drawer_container, fragmentNavigationDrawer);

        // Confirma a transação
        fragmentTransaction.commit();

//        showFragmentHomePage();
//        showFragmentAddressManagementPage();
//        showFragmentPaymentManagement();
        showFragmentOrderHistoryPage();
        return rootView;
    }

    public void showFragmentHomePage() {
        // Cria uma instância do FragmentHomePage
        FragmentHomePage fragmentHomePage = FragmentHomePage.newInstance(sharedPreferences);

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento FragmentAddressManagementPage ao drawer_container
        fragmentTransaction.replace(R.id.dynamic_content_container, fragmentHomePage);

        // Remove o fragmento atual do dynamic_content_container
        Fragment fragment = fragmentManager.findFragmentById(R.id.dynamic_content_container);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        // Confirma a transação
        fragmentTransaction.commit();
    }

    public void showFragmentOrderHistoryPage() {
        // Cria uma instância do FragmentAddressManagementPage
        FragmentOrderHistoryPage fragmentOrderHistoryPage = FragmentOrderHistoryPage.newInstance(sharedPreferences);

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento FragmentAddressManagementPage ao drawer_container
        fragmentTransaction.replace(R.id.dynamic_content_container, fragmentOrderHistoryPage);

        // Remove o fragmento atual do dynamic_content_container
        Fragment fragment = fragmentManager.findFragmentById(R.id.dynamic_content_container);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        // Confirma a transação
        fragmentTransaction.commit();
    }
    public void showFragmentAddressManagementPage() {
        // Cria uma instância do FragmentAddressManagementPage
        FragmentAddressManagementPage fragmentAddressManagementPage = FragmentAddressManagementPage.newInstance(sharedPreferences);

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento FragmentAddressManagementPage ao drawer_container
        fragmentTransaction.replace(R.id.dynamic_content_container, fragmentAddressManagementPage);

        // Remove o fragmento atual do dynamic_content_container
        Fragment fragment = fragmentManager.findFragmentById(R.id.dynamic_content_container);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        // Confirma a transação
        fragmentTransaction.commit();
    }

    public void showAddressRegistrationPageFragment(SharedPreferences sharedPreferences){
        FragmentAddressRegistrationPage addressRegistrationPage = FragmentAddressRegistrationPage.newInstance(sharedPreferences);

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento FragmentProductCartPage ao drawer_container
        fragmentTransaction.replace(R.id.dynamic_content_container, addressRegistrationPage, "ProductCartPage");

        Fragment fragment = fragmentManager.findFragmentById(R.id.dynamic_content_container);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        fragmentTransaction.commit();
    }

    public void showPaymentMethodRegistrationPage(SharedPreferences sharedPreferences){
        FragmentPaymentMethodRegistrationPage paymentMethodRegistrationPage = FragmentPaymentMethodRegistrationPage.newInstance(sharedPreferences);

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento FragmentProductCartPage ao drawer_container
        fragmentTransaction.replace(R.id.dynamic_content_container, paymentMethodRegistrationPage, "ProductCartPage");

        Fragment fragment = fragmentManager.findFragmentById(R.id.dynamic_content_container);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        fragmentTransaction.commit();
    }

    public void showProductCartPageFragment() {
        // Cria uma instância do FragmentProductCartPage
        FragmentProductCartPage productCartPageFragment = FragmentProductCartPage.newInstance();

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento FragmentProductCartPage ao drawer_container
        fragmentTransaction.replace(R.id.dynamic_content_container, productCartPageFragment, "ProductCartPage");

        Fragment fragment = fragmentManager.findFragmentById(R.id.dynamic_content_container);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        fragmentTransaction.commit();
    }

    public void showOrderDetailsPageFragment() {
        boolean continueButtonClicked = sharedPreferences.getBoolean(CONTINUE_BUTTON_CLICKED_KEY, false);

        if (continueButtonClicked) {
            // Crie uma instância do fragmento FragmentOrderDetailsPage
            FragmentOrderDetailsPage orderDetailsPageFragment = FragmentOrderDetailsPage.newInstance(sharedPreferences);

            // Obtém o FragmentManager
            FragmentManager fragmentManager = getChildFragmentManager();

            // Inicia a transação do fragmento
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Adicione o fragmento ao contêiner raiz
            fragmentTransaction.replace(R.id.dynamic_content_container, orderDetailsPageFragment, "OrderDetailsPage");

            Fragment fragment = fragmentManager.findFragmentById(R.id.dynamic_content_container);
            if (fragment != null) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commit();
        }
    }

    public void showFragmentPaymentManagement() {
        // Cria uma instância do FragmentAddressManagementPage
        FragmentPaymentManagementPage fragmentPaymentManagementPage = FragmentPaymentManagementPage.newInstance(sharedPreferences);

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento FragmentAddressManagementPage ao drawer_container
        fragmentTransaction.replace(R.id.dynamic_content_container, fragmentPaymentManagementPage);

        // Remove o fragmento atual do dynamic_content_container
        Fragment fragment = fragmentManager.findFragmentById(R.id.dynamic_content_container);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }

        // Confirma a transação
        fragmentTransaction.commit();
    }

    public void onBackToHomePagePressed() {
        showFragmentHomePage();
    }
    public void onBackToCartPagePressed() {
        showProductCartPageFragment();
    }
    public void onBackToAddressManagementPagePressed() {
        showFragmentAddressManagementPage();
    }
    public void onBackToPaymentMethodManagementPagePressed() {
        showFragmentPaymentManagement();
    }


    @Override
    public void onResume() {
        super.onResume();
        // Registra o listener para monitorar as mudanças no SharedPreferences
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        // Verifica o valor inicial de isNavigationDrawerOpen
        boolean isNavigationDrawerOpen = sharedPreferences.getBoolean("isNavigationDrawerOpen", false);
        if (isNavigationDrawerOpen) {
            showFragmentNavigationDrawer();
        } else {
            hideFragmentNavigationDrawer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Remove o listener do SharedPreferences
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("isNavigationDrawerOpen")) {
            boolean isNavigationDrawerOpen = sharedPreferences.getBoolean("isNavigationDrawerOpen", false);
            if (isNavigationDrawerOpen) {
                showFragmentNavigationDrawer();
            } else {
                hideFragmentNavigationDrawer();
            }
        }
        if (key.equals("selectedNavigationOption")) {
            String selectedOption = sharedPreferences.getString(key, "");

            if (selectedOption.equals(getString(R.string.home_page_button))) {
                showFragmentHomePage();
            } else if (selectedOption.equals(getString(R.string.order_history_button))) {
                showFragmentOrderHistoryPage();
            } else if (selectedOption.equals(getString(R.string.address_management_button))) {
                showFragmentAddressManagementPage();
            } else if (selectedOption.equals(getString(R.string.payment_management_button))) {
                showFragmentPaymentManagement();
            }
        }

    }

    private void showFragmentNavigationDrawer() {
        if (fragmentNavigationDrawer != null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.show(fragmentNavigationDrawer);
            fragmentTransaction.commit();
        }
    }

    private void hideFragmentNavigationDrawer() {
        if (fragmentNavigationDrawer != null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragmentNavigationDrawer);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("selectedNavigationOption");
        editor.remove("isNavigationDrawerOpen");
    }
}
