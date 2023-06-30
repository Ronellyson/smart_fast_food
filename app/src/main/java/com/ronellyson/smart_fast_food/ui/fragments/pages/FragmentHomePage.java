package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentProductCardList;

public class FragmentHomePage extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String CART_BUTTON_CLICKED_KEY = "cartButtonClicked";

    private SharedPreferences sharedPreferences;
    private ImageButton cartButton;

    public static FragmentHomePage newInstance() {
        return new FragmentHomePage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_page, container, false);

        // Inicializa o SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0);

        // Obtém o estado do botão salvo no SharedPreferences
        boolean isCartButtonClicked = sharedPreferences.getBoolean(CART_BUTTON_CLICKED_KEY, false);

        // Obtém o botão do layout
        cartButton = rootView.findViewById(R.id.home_page_cart_button);

        // Define o estado do botão com base no valor recuperado
        cartButton.setPressed(isCartButtonClicked);

        // Define o listener de clique para o botão
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém o estado do botão salvo no SharedPreferences, usando false como valor padrão
                boolean isCartButtonClicked = sharedPreferences.getBoolean(CART_BUTTON_CLICKED_KEY, false);


                // Inverte o estado do botão
                boolean newCartButtonClicked = !isCartButtonClicked;

                // Salva o novo estado do botão no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(CART_BUTTON_CLICKED_KEY, newCartButtonClicked);
                editor.apply();

                // Atualiza o estado do botão
                cartButton.setPressed(newCartButtonClicked);
                Log.d("CartButton", String.valueOf(cartButton.isPressed()));

                // Obtém a referência à MainActivity
                MainActivity mainActivity = (MainActivity) requireActivity();

                // Notifica a MainActivity sobre a atualização do estado do botão
                mainActivity.updateCartButtonState(newCartButtonClicked);
            }
        });

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Cria uma instância do fragmento que você deseja exibir
        FragmentProductCardList fragmentProductCardList = FragmentProductCardList.newInstance();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.product_card_list_container, fragmentProductCardList);

        // Confirma a transação
        fragmentTransaction.commit();

        return rootView;
    }
}
