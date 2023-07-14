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

    private FragmentHomePage fragmentHomePage;

    public static FragmentDynamicPage newInstance() {
        return new FragmentDynamicPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dynamic_page_fragment, container, false);

        // Obtém o SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        // Cria uma instância do FragmentNavigationDrawer
        fragmentNavigationDrawer = FragmentNavigationDrawer.newInstance(sharedPreferences);

        // Cria uma instância do FragmentHomePage
        fragmentHomePage = FragmentHomePage.newInstance();

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento do FragmentNavigationDrawer ao drawer_container
        fragmentTransaction.add(R.id.drawer_container, fragmentNavigationDrawer);

        // Adiciona o fragmento do FragmentNavigationDrawer ao dynamic_content_container
        fragmentTransaction.add(R.id.dynamic_content_container, fragmentHomePage);

        // Confirma a transação
        fragmentTransaction.commit();

        return rootView;
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
}
