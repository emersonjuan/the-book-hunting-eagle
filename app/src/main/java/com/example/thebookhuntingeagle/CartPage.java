package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.thebookhuntingeagle.model.Sale;

public class CartPage extends AppCompatActivity {

    private TextView txtOrderDetailBookTitle;
    private TextView txtOrderDetailBookAuthor;
    private TextView txtOrderDetailBookEdition;
    private TextView txtOrderDetailBookCondition;
    private TextView txtOrderDetailBookPrice;
    private TextView txtOrderDetailBookDiscount;
    private TextView txtOrderDetailBookFinalPrice;
    private TextView txtOrderDetailBookUserName;
    private TextView txtOrderDetailBookUserEmail;
    private TextView txtOrderDetailBookUserPhone;
    private TextView txtOrderDetailBookUserCity;
    private TextView txtOrderDetailBookUserAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        //References for controls
        txtOrderDetailBookTitle = findViewById(R.id.txtOrderDetailBookTitle);
        txtOrderDetailBookAuthor = findViewById(R.id.txtOrderDetailBookAuthor);
        txtOrderDetailBookEdition = findViewById(R.id.txtOrderDetailBookEdition);
        txtOrderDetailBookCondition = findViewById(R.id.txtOrderDetailBookCondition);
        txtOrderDetailBookPrice = findViewById(R.id.txtOrderDetailBookPrice);
        txtOrderDetailBookDiscount = findViewById(R.id.txtOrderDetailBookDiscount);
        txtOrderDetailBookFinalPrice = findViewById(R.id.txtOrderDetailBookFinalPrice);
        txtOrderDetailBookUserName = findViewById(R.id.txtOrderDetailBookUserName);
        txtOrderDetailBookUserEmail = findViewById(R.id.txtOrderDetailBookUserEmail);
        txtOrderDetailBookUserPhone = findViewById(R.id.txtOrderDetailBookUserPhone);
        txtOrderDetailBookUserCity = findViewById(R.id.txtOrderDetailBookUserCity);
        txtOrderDetailBookUserAddress = findViewById(R.id.txtOrderDetailBookUserAddress);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        //Sale coming from previous screen
        Sale sale = (Sale) getIntent().getSerializableExtra("sale");
        //Load sale data to current screen
        loadSale(sale);

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartPage.this, ManageOrders.class));
            }
        });
    }

    private void loadSale(Sale sale) {
        txtOrderDetailBookTitle.setText(sale.getBookTitle());
        txtOrderDetailBookAuthor.setText(sale.getAuthor());
        txtOrderDetailBookEdition.setText(sale.getEdition());
        txtOrderDetailBookCondition.setText(sale.getCondition().name());
        txtOrderDetailBookPrice.setText(String.format("CAD %.2f", sale.getPrice()));
        txtOrderDetailBookDiscount.setText(String.format("%.2f %%", sale.getDiscount()));
        txtOrderDetailBookFinalPrice.setText(String.format("CAD %.2f", sale.getDiscountedPrice()));
        txtOrderDetailBookUserName.setText(sale.getSeller().getName());
        txtOrderDetailBookUserEmail.setText(sale.getSeller().getEmail());
        txtOrderDetailBookUserPhone.setText(sale.getSeller().getPhone());
        txtOrderDetailBookUserCity.setText(sale.getSeller().getCity().getName());
        txtOrderDetailBookUserAddress.setText(sale.getSeller().getAddress());
    }
}