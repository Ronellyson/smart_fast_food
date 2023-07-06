package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.MainActivity;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentProductCardList;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentProductCategoryButtonList;

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
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Obtém a referência à SearchView
        SearchView searchBar = rootView.findViewById(R.id.home_page_search_bar);

        // Define o listener de texto alterado para a barra de busca
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Salva o conteúdo da barra de busca no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("searchQuery", query);
                editor.apply();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Salva o conteúdo da barra de busca no SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("searchQuery", newText);
                editor.apply();
                return false;
            }
        });


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
                Log.d("isCartButtonClicked", String.valueOf(cartButton.isPressed()));

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

        // Cria uma instância do fragmento FragmentProductCategoryButtonList
        FragmentProductCategoryButtonList fragmentProductCategoryButtonList = FragmentProductCategoryButtonList.newInstance();

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.product_card_list_container, fragmentProductCardList);
        fragmentTransaction.add(R.id.product_category_button_list_container, fragmentProductCategoryButtonList);
        // Confirma a transação
        fragmentTransaction.commit();

        return rootView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("searchQuery");
        editor.apply();
    }
}
