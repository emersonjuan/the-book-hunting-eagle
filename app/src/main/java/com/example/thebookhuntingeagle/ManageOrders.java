package com.example.thebookhuntingeagle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.LoggedUser;

public class ManageOrders extends AppCompatActivity {
    private ImageView imageViewAvatar;
    private TextView txtNameUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);
        /* In this page the user will see the details of the order he selected at the Your Orders
        page. The order details displayed in this page are: the book title, order status, order
        shipping option. This page allows the user to edit this order status or shipping option.
        Once he's done with editing the details, he will be able to save his changes (toast message
        saying changes were saved) and go back to the Your Orders page or the User home page.
         */
        txtNameUserAccount = findViewById(R.id.txtNameUserAccount6);
        imageViewAvatar = findViewById(R.id.imgUser6);
        Button btnSaveChanges = (Button) findViewById(R.id.btnSaveChanges);

        loadUserData();

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageOrders.this, UserHomePage.class));
            }
        });
    }

    private void loadUserData() {
        //Header update
        User user = LoggedUser.getUser();
        String[] fullName = user.getName().split(" ");
        txtNameUserAccount.setText(fullName[0]);
        imageViewAvatar.setImageResource(user.getAvatar());
    }
}