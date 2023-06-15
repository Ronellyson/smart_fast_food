package com.ronellyson.smart_fast_food.ui.productDetails;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ronellyson.smart_fast_food.R;
import com.ronellyson.smart_fast_food.data.model.ItemProduct;
import com.ronellyson.smart_fast_food.data.model.Product;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD = "EXTRA_FOOD";
    private EditText quantity;
    private Button removeQuantity;
    private Button addQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        setValores();

    }

    public void setValores(){
        ImageView imageProduct;
        TextView nameProduct;
        TextView priceProduct;
        TextView rateProduct;
        TextView descriptionProduct;

        imageProduct = findViewById(R.id.image_product);
        nameProduct = findViewById(R.id.name_product);
        priceProduct = findViewById(R.id.price_product);
        rateProduct = findViewById(R.id.rate_product);
        descriptionProduct = findViewById(R.id.description_product);
        quantity = findViewById(R.id.quantity_product);
        removeQuantity = findViewById(R.id.button_remove);
        addQuantity = findViewById(R.id.button_add);

        Product product = (Product) getIntent().getSerializableExtra(EXTRA_FOOD);
        nameProduct.setText(product.getName());
        priceProduct.setText("$"+(product.getPrice()));
        rateProduct.setText(Integer.toString(product.getRate()));
        descriptionProduct.setText(product.getDescription());
        Picasso.get().load(product.getUrlImage()).into(imageProduct);

        String quantityValue = quantity.getText().toString();
        int quantity = Integer.parseInt(quantityValue);
    }

    public void setAddQuantity(View view){
        String quantityValue = quantity.getText().toString();
        int quantityInt = Integer.parseInt(quantityValue);
        quantityInt += 1;
        quantity.setText(Integer.toString(quantityInt));
    }

    public void setRemoveQuantity(View view){
        String quantityValue = quantity.getText().toString();
        int quantityInt = Integer.parseInt(quantityValue);
        if(quantityInt > 1){
            quantityInt -= 1;
            quantity.setText(Integer.toString(quantityInt));
        }
    }

    public void addItemPedido(View view){
        Product food = (Product) getIntent().getSerializableExtra(EXTRA_FOOD);

        String quantityValue = quantity.getText().toString();
        int quantityInt = Integer.parseInt(quantityValue);

        ItemProduct itemProduct = new ItemProduct(food, quantityInt);
        if(itemProduct != null){
            Toast.makeText(this, itemProduct.getQuantity()+" "+itemProduct.getProduct().getName()+" adicionado com sucesso", Toast.LENGTH_LONG).show();
        }
    }
}