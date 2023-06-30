package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.MainActivity;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentProductCartItemList;

public class FragmentProductCartPage extends Fragment {
    public static FragmentProductCartPage newInstance() {
        return new FragmentProductCartPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_cart_page, container, false);

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Cria uma instância do fragmento que você deseja exibir
        FragmentProductCartItemList fragmentProductCartItemList = FragmentProductCartItemList.newInstance();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.product_cart_item_list_container, fragmentProductCartItemList);

        // Confirma a transação
        fragmentTransaction.commit();

        ImageButton backButton = rootView.findViewById(R.id.product_cart_page_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Atualiza o estado do botão para false
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.updateCartButtonState(false);
            }
        });


        return rootView;
    }
}
