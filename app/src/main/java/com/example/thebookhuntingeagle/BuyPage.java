package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.thebookhuntingeagle.database.SaleDataSource;
import com.example.thebookhuntingeagle.model.Sale;

import java.util.List;

public class BuyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);
        /* The best sellers must be stored in the database? Or is it better just to show
        a list of 3 items? (same for New and trending)
        In search results the app should bring from the database all the books with names matching
        the words searched. This result format should be maybe a listview that would allow the user
        to select the book he wants to buy.
        Once the book is selected the user clicks the button add to cart and goes to the cart page.
         */
        Button btnAddCart = findViewById(R.id.btnAddCart);
        SearchView searchView = findViewById(R.id.search);
        SaleDataSource sds = new SaleDataSource(this);

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(BuyPage.this, CartPage.class));
                sds.open();
                String searchTxt = searchView.getQuery().toString();
                List<Sale> booksFound = sds.findByTitle(searchTxt);
                sds.close();
            }
        });
    }
}