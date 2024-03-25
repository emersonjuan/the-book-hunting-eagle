package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.LoggedUser;

import java.util.Timer;
import java.util.TimerTask;

public class UserHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        Button btnBuy = findViewById(R.id.btnBuy);
        Button btnSellShare = findViewById(R.id.btnSellShare);
        Button btnManageShelf = findViewById(R.id.btnManageShelf);
        Button btnManageAccount = findViewById(R.id.btnManageAccount);
        Button btnOrders = findViewById(R.id.btnOrders);
        Button btnLogOut = findViewById(R.id.btnLogOut);
        TextView txtNameUserAccount = findViewById(R.id.txtNameUserAccount);
        ImageView imageViewAvatar = findViewById(R.id.imgUser);

        //Header update
        User user = LoggedUser.getUser();
        String[] fullName = user.getName().split(" ");
        txtNameUserAccount.setText(fullName[0]);
        imageViewAvatar.setImageResource(user.getAvatar());

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
                LoggedUser.setUser(null);
                startActivity(new Intent(UserHomePage.this, LogoutPage.class));

                Toast.makeText(UserHomePage.this, "User logged out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}