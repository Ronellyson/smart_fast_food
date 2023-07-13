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
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentAddressCardList;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentProductCardList;

public class FragmentOrderDetailsPage extends Fragment {

    public static FragmentOrderDetailsPage newInstance() {
        return new FragmentOrderDetailsPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_details_page, container, false);

        ImageButton backButton = rootView.findViewById(R.id.product_cart_page_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the button state to false
                MainActivity mainActivity = (MainActivity) requireActivity();
                // Navigate back to the previous view
                mainActivity.onBackToCartPagePressed();
            }
        });

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Cria uma instância do fragmento que você deseja exibir
        FragmentAddressCardList fragmentProductCardList = FragmentAddressCardList.newInstance();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.address_card_list_container, fragmentProductCardList);

        // Confirma a transação
        fragmentTransaction.commit();

        return rootView;
    }
}

