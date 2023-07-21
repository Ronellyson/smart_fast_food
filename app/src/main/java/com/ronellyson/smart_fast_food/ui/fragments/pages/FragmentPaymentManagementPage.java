package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentPaymentMethodCardList;

public class FragmentPaymentManagementPage extends Fragment {
    SharedPreferences sharedPreferences;

    public static FragmentPaymentManagementPage newInstance(SharedPreferences sharedPreferences) {
        FragmentPaymentManagementPage fragment = new FragmentPaymentManagementPage();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.payment_method_management_page, container, false);

        ImageButton btnOpenDrawer = rootView.findViewById(R.id.btn_open_drawer);
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define o estado do Navigation Drawer como aberto no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNavigationDrawerOpen", true);
                editor.apply();
            }
        });

        Button btnPaymentMethodRegister = rootView.findViewById(R.id.btnPaymentMethodRegister);
        btnPaymentMethodRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém a referência à MainActivity
                FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) requireParentFragment();

                fragmentDynamicPage.showPaymentMethodRegistrationPage(sharedPreferences);
            }
        });

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Cria uma instância do fragmento que você deseja exibir
        FragmentPaymentMethodCardList fragmentPaymentMethodCardList = FragmentPaymentMethodCardList.newInstance(sharedPreferences, false, true);

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.payment_method_card_list_container,fragmentPaymentMethodCardList);

        // Confirma a transação
        fragmentTransaction.commit();

        return rootView;
    }
}
