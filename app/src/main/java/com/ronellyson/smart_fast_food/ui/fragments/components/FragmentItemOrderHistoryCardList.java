package com.ronellyson.smart_fast_food.ui.fragments.components;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.ui.adapters.OrderHistoryCardAdapter;

public class FragmentItemOrderHistoryCardList extends Fragment {

    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private OrderHistoryCardAdapter adapter;

    public static FragmentItemOrderHistoryCardList newInstance(SharedPreferences sharedPreferences) {
        FragmentItemOrderHistoryCardList fragment = new FragmentItemOrderHistoryCardList();
        fragment.sharedPreferences = sharedPreferences;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_order_history_card_list, container, false);

        recyclerView = view.findViewById(R.id.item_order_history_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderHistoryCardAdapter(sharedPreferences);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
