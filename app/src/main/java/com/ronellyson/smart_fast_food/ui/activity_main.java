package com.ronellyson.smart_fast_food.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.fragments.FragmentProductCartItem;
import com.ronellyson.smart_fast_food.ui.fragments.FragmentProductCartItemList;

public class activity_main extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_root, FragmentProductCartItemList.newInstance(),"HomePage")
                    .commit();
        }
    }
}
