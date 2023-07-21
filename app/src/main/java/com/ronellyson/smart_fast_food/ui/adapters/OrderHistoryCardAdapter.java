package com.ronellyson.smart_fast_food.ui.adapters;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.DeliveryOrder;
import com.ronellyson.smart_fast_food.data.model.ProductCartItem;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryCardAdapter extends RecyclerView.Adapter<OrderHistoryCardAdapter.OrderHistoryViewHolder> {

    private List<DeliveryOrder> orderHistoryList;
    private SharedPreferences sharedPreferences;
    private int expandedPosition = -1; // Armazena a posição do card expandido

    public OrderHistoryCardAdapter(SharedPreferences sharedPreferences) {
        this.orderHistoryList = new ArrayList<>();
        this.sharedPreferences = sharedPreferences;
        retrieveOrderHistoryList();
    }

    public void retrieveOrderHistoryList() {
        // Recupera a lista de pedidos do SharedPreferences usando a chave "orderHistory"
        String orderHistoryJson = sharedPreferences.getString("orderHistory", "");
        List<DeliveryOrder> orderHistoryList = new ArrayList<>();

        // Converte o JSON para a lista de objetos DeliveryOrder usando Gson
        if (!orderHistoryJson.isEmpty()) {
            orderHistoryList = new Gson().fromJson(orderHistoryJson, new TypeToken<List<DeliveryOrder>>() {}.getType());
        }

        // Define a lista recuperada como a lista do adapter e notifica as mudanças
        setOrderHistoryList(orderHistoryList);
    }

    public void setOrderHistoryList(List<DeliveryOrder> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history_card, parent, false);
        return new OrderHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        DeliveryOrder deliveryOrder = orderHistoryList.get(position);
        holder.bind(deliveryOrder);

        // Check if the card is expanded and set visibility accordingly
        if (expandedPosition == position) {
            holder.productCartItemOrderListContainerLabel.setVisibility(View.VISIBLE);
            holder.productCartItemOrderListContainer.setVisibility(View.VISIBLE);
            holder.totalValueLabelTextView.setVisibility(View.VISIBLE);
            holder.totalValueTextView.setVisibility(View.VISIBLE);
            holder.deliveryAddressLabelTextView.setVisibility(View.VISIBLE);
            holder.deliveryAddressTextView.setVisibility(View.VISIBLE);
            holder.paymentMethodLabelTextView.setVisibility(View.VISIBLE);
            holder.paymentMethodTextView.setVisibility(View.VISIBLE);
        } else {
            holder.productCartItemOrderListContainerLabel.setVisibility(View.GONE);
            holder.productCartItemOrderListContainer.setVisibility(View.GONE);
            holder.deliveryAddressLabelTextView.setVisibility(View.GONE);
            holder.deliveryAddressTextView.setVisibility(View.GONE);
            holder.paymentMethodLabelTextView.setVisibility(View.GONE);
            holder.paymentMethodTextView.setVisibility(View.GONE);
        }

        // Set the click listener to expand/collapse the card details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the expanded position
                int clickedPosition = holder.getBindingAdapterPosition();
                expandedPosition = expandedPosition == clickedPosition ? -1 : clickedPosition;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView orderIdLabelTextView;
        private TextView orderIdTextView;
        private TextView orderDateTimeLabelTextView;
        private TextView orderDateTimeTextView;
        private TextView totalValueLabelTextView;
        private TextView totalValueTextView;
        private TextView deliveryAddressLabelTextView;
        private TextView deliveryAddressTextView;
        private TextView paymentMethodLabelTextView;
        private TextView paymentMethodTextView;
        private TextView productCartItemOrderListContainerLabel;
        private LinearLayout productCartItemOrderListContainer;
        private boolean isExpanded = expandedPosition != 0;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdLabelTextView = itemView.findViewById(R.id.order_id);
            orderIdTextView = itemView.findViewById(R.id.order_id_value);
            orderDateTimeLabelTextView = itemView.findViewById(R.id.order_date_time);
            orderDateTimeTextView = itemView.findViewById(R.id.order_date_time_value);
            totalValueLabelTextView = itemView.findViewById(R.id.order_total_label);
            totalValueTextView = itemView.findViewById(R.id.order_total_value);
            deliveryAddressLabelTextView = itemView.findViewById(R.id.delivery_address_label);
            deliveryAddressTextView = itemView.findViewById(R.id.delivery_address_value);
            paymentMethodLabelTextView = itemView.findViewById(R.id.payment_method_label);
            paymentMethodTextView = itemView.findViewById(R.id.payment_method_value);
            productCartItemOrderListContainerLabel = itemView.findViewById(R.id.product_cart_item_order_list_container_label);
            productCartItemOrderListContainer = itemView.findViewById(R.id.product_cart_item_order_list_container);

            // Set the click listener to expand/collapse the hidden information
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isExpanded = !isExpanded;
                    notifyDataSetChanged();
                }
            });
        }

        public void bind(DeliveryOrder order) {
            orderIdLabelTextView.setText("Order ID:");
            orderIdTextView.setText(String.valueOf(order.getOrderId()));
            orderIdTextView.setTextColor(itemView.getResources().getColor(R.color.black));

            orderDateTimeLabelTextView.setText("Order DateTime:");
            orderDateTimeTextView.setText(String.valueOf(order.getOrderDateTime()));
            orderDateTimeTextView.setTextColor(itemView.getResources().getColor(R.color.black));

            totalValueLabelTextView.setText("Valor Total:");
            totalValueTextView.setText("R$: " + order.getTotalValue());
            totalValueTextView.setTextColor(itemView.getResources().getColor(R.color.black));

            deliveryAddressLabelTextView.setText("Endereço:");
            deliveryAddressTextView.setText(order.getAddress().toString());
            deliveryAddressTextView.setTextColor(itemView.getResources().getColor(R.color.black));

            paymentMethodLabelTextView.setText("Método de pagamento:");
            paymentMethodTextView.setText(order.getCreditDebitCard().toString());
            paymentMethodTextView.setTextColor(itemView.getResources().getColor(R.color.black));

            if (isExpanded) {
                Log.d("bind", order.getProductCartItems().get(0).getProduct().toString());
                // Show the hidden information
                productCartItemOrderListContainerLabel.setVisibility(View.VISIBLE);
                productCartItemOrderListContainer.setVisibility(View.VISIBLE);
                productCartItemOrderListContainer.removeAllViews();

                for (ProductCartItem item : order.getProductCartItems()) {
                    Log.d("ProductCartItem", item.toString());
                    // Inflate the layout for displaying product details
                    View productItemView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.item_product_cart_item, productCartItemOrderListContainer, false);

                    // Find the views within the inflated layout
                    TextView productQuantityTextView = productItemView.findViewById(R.id.product_quantity);
                    TextView productNameTextView = productItemView.findViewById(R.id.product_name);
                    TextView productPriceTextView = productItemView.findViewById(R.id.product_price);

                    // Set product name and price
                    productQuantityTextView.setText("Quantidade: " + item.getProductCartItemQuantity());
                    productNameTextView.setText("Nome: " + item.getProduct().getName());
                    productPriceTextView.setText("Preço: R$: " + item.getProduct().getPrice());

                    // Add the productItemView to the productCartItemOrderListContainer
                    productCartItemOrderListContainer.addView(productItemView);
                }
            } else {
                // Hide the hidden information
                productCartItemOrderListContainerLabel.setVisibility(View.GONE);
                productCartItemOrderListContainer.setVisibility(View.GONE);
                productCartItemOrderListContainer.removeAllViews();
            }
        }
    }
}

