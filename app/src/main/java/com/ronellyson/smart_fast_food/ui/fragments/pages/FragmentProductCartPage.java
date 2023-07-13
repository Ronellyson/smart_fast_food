package com.ronellyson.smart_fast_food.ui.fragments.pages;

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

        // Obtain the FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();

        // Create an instance of the fragment you want to display
        FragmentProductCartItemList fragmentProductCartItemList = FragmentProductCartItemList.newInstance();

        // Start the fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the fragment to the main fragment container
        fragmentTransaction.add(R.id.product_cart_item_list_container, fragmentProductCartItemList);

        // Confirm the transaction
        fragmentTransaction.commit();

        ImageButton backButton = rootView.findViewById(R.id.product_cart_page_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the button state to false
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.onBackToHomePagePressed();
            }
        });

        Button continueButton = rootView.findViewById(R.id.product_cart_page_continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the button state to true
                MainActivity mainActivity = (MainActivity) requireActivity();
                FragmentProductCartItemList fragment = (FragmentProductCartItemList) getChildFragmentManager().findFragmentById(R.id.product_cart_item_list_container);
                fragment.getAdapter().setContinueButtonClicked(true);
                mainActivity.showOrderDetailsPageFragment();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentProductCartItemList fragment = (FragmentProductCartItemList) getChildFragmentManager().findFragmentById(R.id.product_cart_item_list_container);
        fragment.getAdapter().setContinueButtonClicked(false);
    }
}
