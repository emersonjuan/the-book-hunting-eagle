package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        /* In this page all the information about the selected book will be shown.
        Once the user clicks one of the shipping options the app should be able to add the price
        of the book to the price of the chosen shipping method and display it, maybe on an
        empty textview that will have to be placed after the shipping options in the layout.
        Once the user clicks the place order button, it will show a pop-up message saying the order
        was placed and lead him to the ManageOrders page so he can see his order details.
        This information must also be saved in the database.
         */
        Button btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartPage.this, ManageOrders.class));
            }
        });
    }
}