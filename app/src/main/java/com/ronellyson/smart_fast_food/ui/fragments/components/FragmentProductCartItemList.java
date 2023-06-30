package com.ronellyson.smart_fast_food.ui.fragments.components;

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
import com.ronellyson.smart_fast_food.data.model.ProductCartItem;
import com.ronellyson.smart_fast_food.ui.adapters.ProductCartItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductCartItemList extends Fragment {

    private RecyclerView recyclerView;
    private ProductCartItemAdapter adapter;

    public static FragmentProductCartItemList newInstance() {
        return new FragmentProductCartItemList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_cart_item_list, container, false);
        recyclerView = view.findViewById(R.id.product_cart_item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ProductCartItem> productCartItems = new ArrayList<ProductCartItem>();
        productCartItems.add(new ProductCartItem("Filé a Parmegiana", "https://images.pexels.com/photos/13443376/pexels-photo-13443376.jpeg?auto=compress&cs=tinysrgb&w=1600", "120", 150));
        productCartItems.add(new ProductCartItem("Hambúrguer", "https://images.pexels.com/photos/1633578/pexels-photo-1633578.jpeg?auto=compress&cs=tinysrgb&w=1600", "80", 100));
        productCartItems.add(new ProductCartItem("Pizza", "https://images.pexels.com/photos/1566837/pexels-photo-1566837.jpeg?auto=compress&cs=tinysrgb&w=1600", "30", 200));
        productCartItems.add(new ProductCartItem("Sushi", "https://images.pexels.com/photos/357756/pexels-photo-357756.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "50", 80));
        productCartItems.add(new ProductCartItem("Salada", "https://images.pexels.com/photos/1059905/pexels-photo-1059905.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "20", 120));
        productCartItems.add(new ProductCartItem("Massa", "https://images.pexels.com/photos/16594961/pexels-photo-16594961/free-photo-of-pauzinhos-hashi-palitinho-comida.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "40", 90));
        productCartItems.add(new ProductCartItem("Frango Assado", "https://images.pexels.com/photos/5718025/pexels-photo-5718025.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "70", 70));
        productCartItems.add(new ProductCartItem("Sorvete", "Sorvete", "15", 250));
        productCartItems.add(new ProductCartItem("Churrasco", "https://images.pexels.com/photos/1482803/pexels-photo-1482803.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "90", 60));

        adapter = new ProductCartItemAdapter(productCartItems);
        recyclerView.setAdapter(adapter);
        return view;
    }
}

