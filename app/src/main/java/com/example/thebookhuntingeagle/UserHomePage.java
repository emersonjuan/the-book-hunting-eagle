package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        Button btnBuy = (Button) findViewById(R.id.btnBuy);
        Button btnSellShare = (Button) findViewById(R.id.btnSellShare);
        Button btnManageShelf = (Button) findViewById(R.id.btnManageShelf);
        Button btnManageAccount = (Button) findViewById(R.id.btnManageAccount);
        Button btnOrders = (Button) findViewById(R.id.btnOrders);
        Button btnLogOut = (Button) findViewById(R.id.btnLogOut);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomePage.this, BuyPage.class));
            }
        });

        btnSellShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomePage.this, SellSharePage.class));
            }
        });

        btnManageShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomePage.this, BookshelfPage.class));
            }
        });

        btnManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomePage.this, ManageAccount.class));
            }
        });

        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomePage.this, ManageOrders.class));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomePage.this, LogoutPage.class));
            }
        });
    }
}