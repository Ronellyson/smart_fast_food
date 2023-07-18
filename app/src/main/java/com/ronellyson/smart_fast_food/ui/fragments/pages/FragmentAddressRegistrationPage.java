package com.ronellyson.smart_fast_food.ui.fragments.pages;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ronellyson.smart_fast_food.R;

public class FragmentAddressRegistrationPage extends Fragment {
    SharedPreferences sharedPreferences;


    public static FragmentAddressRegistrationPage newInstance(SharedPreferences sharedPreferences) {
        FragmentAddressRegistrationPage fragment = new FragmentAddressRegistrationPage();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.address_registration_page, container, false);
        ImageButton backButton = rootView.findViewById(R.id.address_registration_page_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDynamicPage fragmentDynamicPage = (FragmentDynamicPage) requireParentFragment();
                fragmentDynamicPage.onBackToAddressManagementPagePressed();
            }
        });

        return rootView;
    };
}
