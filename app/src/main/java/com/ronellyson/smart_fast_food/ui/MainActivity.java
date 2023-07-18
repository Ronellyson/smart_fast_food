package com.ronellyson.smart_fast_food.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.fragments.pages.FragmentDynamicPage;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showDynamicPageFragment();
    }

    public void showDynamicPageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag("FragmentDynamicPage");
        if (fragment == null) {
            fragment = FragmentDynamicPage.newInstance();
            fragmentTransaction.add(R.id.container_root, fragment, "FragmentDynamicPage");
        }

        fragmentTransaction.commit();
    }
}
