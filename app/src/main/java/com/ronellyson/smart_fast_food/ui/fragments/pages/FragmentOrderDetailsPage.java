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
import com.ronellyson.smart_fast_food.data.model.Address;
import com.ronellyson.smart_fast_food.data.model.CreditDebitCard;
import com.ronellyson.smart_fast_food.ui.contracts.OnAddressSelectedListener;
import com.ronellyson.smart_fast_food.ui.contracts.OnPaymentMethodSelectedListener;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentAddressCardList;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentOrderSummary;
import com.ronellyson.smart_fast_food.ui.fragments.components.FragmentPaymentMethodCardList;

public class FragmentOrderDetailsPage extends Fragment implements OnPaymentMethodSelectedListener, OnAddressSelectedListener {

    SharedPreferences sharedPreferences;
    private CreditDebitCard selectedPayment;
    private Address selectedAddress;

    public static FragmentOrderDetailsPage newInstance(SharedPreferences sharedPreferences) {
        FragmentOrderDetailsPage fragment = new FragmentOrderDetailsPage();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
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
                FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) requireParentFragment();

                // Navigate back to the previous view
                fragmentDynamicPage.onBackToCartPagePressed();
            }
        });

        // Obtém o FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Cria uma instância do fragmento que você deseja exibir
        FragmentAddressCardList fragmentProductCardList = FragmentAddressCardList.newInstance(sharedPreferences, true, false);

        // Inicia a transação do fragmento
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.order_details_page_address_card_list_container, fragmentProductCardList);

        // Cria uma instância do fragmento que você deseja exibir
        FragmentPaymentMethodCardList fragmentPaymentMethodCardList = FragmentPaymentMethodCardList.newInstance(sharedPreferences, true, false);

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.order_details_page_payment_method_card_container, fragmentPaymentMethodCardList);

        // Cria uma instância do fragmento que você deseja exibir
        FragmentOrderSummary fragmentOrderSummary = FragmentOrderSummary.newInstance(sharedPreferences);

        // Adiciona o fragmento ao container do fragmento principal
        fragmentTransaction.add(R.id.order_resume_container, fragmentOrderSummary);

        // Confirma a transação
        fragmentTransaction.commit();

        return rootView;
    }

    @Override
    public void onPaymentMethodSelected(CreditDebitCard selectedPayment) {
        Log.d("onPaymentMethodSelected", selectedPayment.getCardHolderName());
        this.selectedPayment = selectedPayment;
        saveSelectedPaymentAndAddress(selectedPayment, selectedAddress);
    }

    @Override
    public void onAddressSelected(Address selectedAddress) {
        Log.d("onAddressSelected", selectedAddress.getHolder());
        this.selectedAddress = selectedAddress;
        saveSelectedPaymentAndAddress(selectedPayment, selectedAddress);
    }

    private void saveSelectedPaymentAndAddress(CreditDebitCard selectedPayment, Address selectedAddress) {

    }

}

